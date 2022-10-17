package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

type Node struct {
	price       int
	destination string
}

type Graph struct {
	list map[string][]Node
	lock sync.RWMutex
}

func (graph *Graph) initialize() {
	graph.list = make(map[string][]Node)
}

func (graph *Graph) addCity(name string) bool {
	if graph.list[name] != nil {
		return false
	}

	graph.list[name] = make([]Node, 0)

	return true
}

func (graph *Graph) getNode(from, to string) (*Node, int) {

	if graph.list[from] == nil {
		return nil, -1
	}

	var resultNode *Node = nil
	var index int = -1

	for i := 0; i < len(graph.list[from]); i++ {
		current := graph.list[from][i]

		if current.destination == to {
			resultNode = &current
			index = i
			break
		}
	}
	return resultNode, index
}

func (graph *Graph) addRoute(from, to string, price int) bool {
	_, index := graph.getNode(from, to)

	if index != -1 || graph.list[from] == nil || graph.list[to] == nil || from == to {
		return false
	}

	graph.list[from] = append(graph.list[from], Node{price, to})
	graph.list[to] = append(graph.list[to], Node{price, from})

	return true
}

func deleteByIndex[T any](slice []T, i int) []T {
	return append(slice[:i], slice[i+1:]...)
}

func (graph *Graph) deleteCity(city string) bool {
	if graph.list[city] == nil {
		return false
	}

	for currentCity, _ := range graph.list {
		if currentCity == city {
			continue
		}

		graph.deleteRoute(currentCity, city)
	}

	delete(graph.list, city)
	return true
}

func (graph *Graph) deleteRoute(from, to string) bool {
	_, indexFromList := graph.getNode(from, to)

	if indexFromList == -1 {
		return false
	}

	_, indexToList := graph.getNode(to, from)

	graph.list[from] = deleteByIndex(graph.list[from], indexFromList)
	graph.list[to] = deleteByIndex(graph.list[to], indexToList)

	return true
}

func (graph *Graph) editRoute(from, to string, price int) bool {
	routeTo, _ := graph.getNode(from, to)

	if routeTo == nil {
		return false
	}

	routeFrom, _ := graph.getNode(to, from)

	routeTo.price = price
	routeFrom.price = price

	return true
}

func (graph *Graph) print() {
	for city, list := range graph.list {
		fmt.Printf("From: %s\n", city)

		for i := 0; i < len(list); i++ {
			fmt.Printf("%s : %d\n", list[i].destination, list[i].price)
		}
		fmt.Println("----------------------------------")
	}
}

func editPrice(graph *Graph, cities []string) {
	from := cities[rand.Int()%len(cities)]
	to := cities[rand.Int()%len(cities)]

	if from == to {
		fmt.Println("Edit price: no loop edges")
		return
	}

	route, _ := graph.getNode(from, to)
	if route == nil {
		if graph.list[from] == nil || graph.list[to] == nil {
			fmt.Printf("Edit price: there isn`t %s or %s in graph\n", from, to)
		} else {
			fmt.Printf("Edit price: there isn`t route from %s to %s\n", from, to)
		}
	} else {
		oldPrice := route.price
		newPrice := rand.Intn(2000)
		graph.editRoute(from, to, newPrice)
		fmt.Printf("Edit price: changed price from %s to %s(before = %d, after = %d)\n", from, to, oldPrice, newPrice)
	}
}

func performPriceEditor(graph *Graph, cities []string) {
	for {
		graph.lock.Lock()
		time.Sleep(5 * time.Second)
		editPrice(graph, cities)
		graph.lock.Unlock()
	}
}

func editRoute(graph *Graph, cities []string) {
	from := cities[rand.Int()%len(cities)]
	to := cities[rand.Int()%len(cities)]
	toRemove := rand.Intn(2)%2 == 0

	if toRemove {
		if graph.deleteRoute(from, to) {
			fmt.Printf("Edit route: delete route from %s to %s \n", from, to)
		} else {
			fmt.Printf("Edit route: there isn`t route from %s to %s \n", from, to)
		}
	} else {
		price := rand.Intn(2000)

		if graph.addRoute(from, to, price) {
			fmt.Printf("Edit route: there isn`t %s or %s in graph\n", from, to, price)
		} else {
			route, _ := graph.getNode(from, to)

			if route != nil {
				fmt.Printf("Edit route: the route from %s to %s already exists\n", from, to)
			} else if graph.list[from] == nil || graph.list[to] == nil {
				fmt.Printf("Edit route: there isn`t %s or %s in graph\n", from, to)
			}
		}
	}
}

func performRouteEditor(graph *Graph, cities []string) {
	for {
		graph.lock.Lock()
		time.Sleep(5 * time.Second)
		editRoute(graph, cities)
		graph.lock.Unlock()
	}
}

func editCity(graph *Graph, cities []string) {
	city := cities[rand.Int()%len(cities)]
	toRemove := rand.Intn(2)%2 == 0

	if toRemove {
		if graph.deleteCity(city) {
			fmt.Printf("Edit city: successfully removed %s city\n", city)
		} else {
			fmt.Printf("Edit city: %s doesn`t exist\n", city)
		}
	} else {
		if graph.addCity(city) {
			fmt.Printf("Edit city: successfully added %s city\n", city)
		} else {
			fmt.Printf("Edit city: %s already exists\n", city)
		}
	}
}

func performCityEditor(graph *Graph, cities []string) {
	for {
		graph.lock.Lock()
		time.Sleep(5 * time.Second)
		editCity(graph, cities)
		graph.lock.Unlock()
	}
}

func findRoute(graph *Graph, cities []string) {
	from := cities[rand.Int()%len(cities)]
	to := cities[rand.Int()%len(cities)]
	route, _ := graph.getNode(from, to)

	if route != nil {
		fmt.Printf("Find route: found route from %s to %s, price: %d\n", from, to, route.price)
	} else {
		fmt.Printf("Find route: there isn`t route from %s to %s\n", from, to)
	}
}

func performRouteFinder(graph *Graph, cities []string) {
	for {
		graph.lock.RLock()
		time.Sleep(5 * time.Second)
		findRoute(graph, cities)
		graph.lock.RUnlock()
	}
}

func main() {
	graph := Graph{}
	graph.initialize()
	graph.addCity("Rivne")
	graph.addCity("Berezne")
	graph.addCity("Kyiv")

	graph.addRoute("Rivne", "Berezne", 100)
	graph.addRoute("Rivne", "Kyiv", 200)
	graph.addRoute("Kyiv", "Berezne", 600)

	graph.print()
}

package Manager;

import Models.Author;
import Models.Book;

import java.math.BigDecimal;
import java.util.*;

public class Manager {
    private final List<Author> authors;
    private final List<Book> books;

    public Manager(){
        authors = new ArrayList<>();
        books = new ArrayList<>();
    }

    public Manager(List<Author> authors, List<Book> books) {
        this.authors = authors;
        this.books = books;
    }

    public void addAuthor(String name) {
        UUID id = UUID.randomUUID();
        Author author = new Author(id, name);
        authors.add(author);
        System.out.printf("Author added: %n%s%n", author);
    }

    public void addBookToAuthor(UUID authorId, String name, BigDecimal price){
        Optional<Author> author = findAuthorById(authorId);

        if (author.isEmpty()){
            System.out.println("It`s not possible to add the book to author with id: " + authorId);
        }
        UUID id = UUID.randomUUID();
        Book book = new Book(id,authorId,name,price);
        books.add(book);
        System.out.printf("Book added: %n%s%n", book);
    }

    public void deleteAuthor(UUID authorId){
        Optional<Author> author = findAuthorById(authorId);

        if (author.isEmpty()){
            System.out.println("Can`t delete author with id: " + authorId);
            return;
        }
        authors.remove(author.get());
        long booksToDelete = getBooksCount(authorId);
        deleteAuthorBooks(authorId);
        System.out.printf("Author`s {%d} books has been deleted: %s%n", booksToDelete, authorId);
    }

    public void deleteBook(UUID bookId){
        boolean isDeleted = books.removeIf(book -> Objects.equals(book.getId(), bookId));

        if(isDeleted)
            System.out.println("Book deleted: " + bookId);
        else
            System.out.println("Can`t delete book with id: " + bookId);
    }

    public void updateAuthor(UUID authorId, String name){
        Optional<Author> author = findAuthorById(authorId);

        if (author.isEmpty()){
            System.out.println("Can`t update author with id: " + authorId);
        }
        Author authorToUpdate = author.get();
        authorToUpdate.setName(name);
        System.out.println("Successfully updated author with id: " + authorId);
    }

    public void updateBook(UUID bookId,UUID authorId,String name,BigDecimal price){
        Optional<Book> book = findBookById(bookId);

        if (book.isEmpty()){
            System.out.println("Can`t update book with id: " + bookId);
            return;
        }
        Book bookToUpdate = book.get();
        bookToUpdate.setAuthorId(authorId);
        bookToUpdate.setName(name);
        bookToUpdate.setPrice(price);
        System.out.println("Successfully updated book with id: " + bookId);
    }

    public void getAuthorById(UUID id){
        Optional<Author> author = findAuthorById(id);

        if(author.isEmpty()){
            System.out.println("Can`t find author with id: " + id);
            return;
        }
        System.out.println("Found author with id: " + id);
    }

    public void getBookById(UUID id){
        Optional<Book> book = findBookById(id);

        if(book.isEmpty()){
            System.out.println("Can`t find book with id: " + id);
            return;
        }
        System.out.println("Found book with id: " + id);
    }

    public List<Author> getAuthors(){
        return new ArrayList<>(authors);
    }

    public List<Book> getBooks(UUID authorId){
        return books.stream()
                .filter(book -> Objects.equals(book.getId(),authorId))
                .toList();
    }

    public void printAllAuthors(){
        System.out.println("Authors: ");
        for(Author author : authors){
            System.out.println(author);
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }

    public void printAllAuthorsBook(UUID authorId){
        Optional<Author> author = findAuthorById(authorId);

        if (author.isEmpty())
            System.out.println("Can`t get author`s books: " + authorId);

        List<Book> authorsBooks = getBooks(authorId);
        System.out.printf("Author`s books: %s%n", authorId);

        for (Book book : books){
            System.out.println(book);
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }

    public long getBooksCount(UUID authorId) {
        return authors.stream()
                .filter(author -> Objects.equals(author.getId(),authorId))
                .count();
    }

    private void deleteAuthorBooks(UUID authorId) {
        books.removeIf(book -> Objects.equals(book.getAuthorId(),authorId));
    }
    private Optional<Author> findAuthorById(UUID id) {
        return authors.stream()
                .filter(author -> Objects.equals(author.getId(),id))
                .findFirst();
    }

    private Optional<Book> findBookById(UUID id){
        return books.stream()
                .filter(book -> Objects.equals(book.getId(),id))
                .findFirst();
    }
}

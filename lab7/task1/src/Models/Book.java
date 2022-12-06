package Models;

import java.math.BigDecimal;
import java.util.UUID;

public class Book {
    private UUID id;
    private UUID authorId;
    private String name;
    private BigDecimal price;

    public Book(){}
    public Book(UUID id, UUID authorId, String name, BigDecimal price) {
        this.id = id;
        this.authorId = authorId;
        this.name = name;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Books{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}

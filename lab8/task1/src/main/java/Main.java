import client.Client;
import models.Author;
import models.Book;
import util.Constants;

import java.io.IOException;
import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) throws IOException {
        Client client = new Client(Constants.HOST, Constants.PORT);
        try {
            client.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Remark`s books
        System.out.println(client.findBooksByAuthorName("Erich Maria Remark"));

        System.out.println("Connected");

        client.deleteBook(9L);

        //Seneca
        client.deleteAuthor(1L);

        //Add to Steven King
        client.insertBook(new Book(null, 4L, "Lord of the Ring", BigDecimal.valueOf(1550)));

        //Update How to die
        client.updateBook(new Book(4L, 1L, "How to cry", BigDecimal.ONE));

        //Add Zhadan
        client.insertAuthor(new Author(null, "Sergiy Zhadan"));

        //Delete Night in Lisbon
        client.deleteBook(10L);

        //Move King of Lir to Remark
        client.moveToAnotherAuthor(5L, 3L);

        //Remark`s books
        System.out.println(client.findBooksByAuthorName("Erich Maria Remark"));

        client.disconnect();
    }
}
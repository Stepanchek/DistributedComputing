package util;

import models.Author;
import models.Book;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

public final class IoUtils {

    private IoUtils() {}

    public static void writeString(DataOutputStream out, String str) throws IOException {
        out.writeInt(str.length());
        out.writeBytes(str);
    }

    public static String readString(DataInputStream in) throws IOException {
        int length = in.readInt();
        byte[] data = new byte[length];
        in.readFully(data);
        return new String(data);
    }

    public static Book readBook(DataInputStream in, boolean withId) throws IOException {
        Book book = new Book();

        if (withId) {
            book.setId(in.readLong());
        }

        book.setAuthorId(in.readLong());
        book.setName(readString(in));
        book.setPrice(new BigDecimal(readString(in)));

        return book;
    }

    public static Author readAuthor(DataInputStream in, boolean withId) throws IOException {
        Author author = new Author();

        if (withId) {
            author.setId(in.readLong());
        }

        author.setName(readString(in));
        return author;
    }

    public static void writeBook(DataOutputStream out, Book book, boolean withId) throws IOException {
        if (withId) {
            out.writeLong(book.getId());
        }

        out.writeLong(book.getAuthorId());
        writeString(out, book.getName());
        writeString(out, book.getPrice().toString());
    }

    public static void writeAuthor(DataOutputStream out, Author author, boolean withId) throws IOException {
        if (withId) {
            out.writeLong(author.getId());
        }

        writeString(out, author.getName());
    }
}
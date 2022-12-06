package utils;

import java.io.File;

public class Constants {
    private Constants(){}

    public static final String MANAGER = "Manager";
    public static final String AUTHOR = "Author";
    public static final String BOOK = "Book";
    public static final String ID = "id";
    public static final String AUTHOR_ID = "authorId";
    public static final String NAME = "name";
    public static final String PRICE = "price";

    public static final String XML_PATH =
            String.join(File.separator, ".", "src","resources","input.xml");

    public static final String XSD_PATH =
            String.join(File.separator, ".", "src","resources","schema.xsd");
}

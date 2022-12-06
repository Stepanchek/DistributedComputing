package xml;

import Manager.Manager;
import Models.Author;
import Models.Book;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import utils.Constants;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DOMCustomParser {
    public static Manager parse(String pathToXml)
            throws ParserConfigurationException, IOException, SAXException {
        SchemaFactory schemaFactory =
                SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

        Object Constants;
        Schema schema = schemaFactory.newSchema(new File(utils.Constants.XSD_PATH));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setSchema(schema);
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(new ParsingErrorHandler());
        Document document = builder.parse(new File(pathToXml));
        document.getDocumentElement().normalize();

        List<Author> authors = new ArrayList<>();
        List<Book> books = new ArrayList<>();
        NodeList authorNodes = document.getElementsByTagName(utils.Constants.AUTHOR);
        NodeList bookNodes = document.getElementsByTagName(utils.Constants.BOOK);

        for (int i = 0; i < authorNodes.getLength(); i++) {
            Element node = (Element) authorNodes.item(i);

            Author author = new Author();
            author.setId(UUID.fromString(node.getAttribute(utils.Constants.ID)));
            author.setName(node.getAttribute(utils.Constants.NAME));

            authors.add(author);
        }

        for (int i = 0; i < bookNodes.getLength(); i++) {
            Element node = (Element) bookNodes.item(i);

            Book book = new Book();
            book.setId(UUID.fromString(node.getAttribute(utils.Constants.ID)));
            book.setAuthorId(UUID.fromString(node.getAttribute(utils.Constants.AUTHOR_ID)));
            book.setName(node.getAttribute(utils.Constants.NAME));
            book.setPrice(new BigDecimal(node.getAttribute(utils.Constants.PRICE)));

            books.add(book);
        }

        return new Manager(authors, books);
    }

    public static void serialize(Manager manager, String pathToXml)
            throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        Element root = document.createElement(Constants.MANAGER);
        document.appendChild(root);

        List<Author> authors = manager.getAuthors();
        for (Author author : authors) {
            Element authorNode = document.createElement(Constants.AUTHOR);
            authorNode.setAttribute(Constants.ID, author.getId().toString());
            authorNode.setAttribute(Constants.NAME, author.getName());
            root.appendChild(authorNode);

            UUID teamId = author.getId();
            List<Book> books = manager.getBooks(teamId);
            for (Book book : books) {
                Element bookNode = document.createElement(Constants.BOOK);
                bookNode.setAttribute(Constants.ID, book.getId().toString());
                bookNode.setAttribute(Constants.AUTHOR_ID, book.getAuthorId().toString());
                bookNode.setAttribute(Constants.NAME, book.getName());
                bookNode.setAttribute(Constants.PRICE, book.getPrice().toString());

                authorNode.appendChild(bookNode);
            }
        }

        Source domSource = new DOMSource(document);
        Result result = new StreamResult(new File(pathToXml));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(domSource, result);
    }
}

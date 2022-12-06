import Manager.Manager;
import org.xml.sax.SAXException;
import utils.Constants;
import xml.DOMCustomParser;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Manager manager = DOMCustomParser.parse(Constants.XML_PATH);

        //delete Shakespeare book
        manager.deleteBook(UUID.fromString("fb9fbe92-32d0-47cb-8a0f-35d28ab1f247"));

        //delete Seneca
        manager.deleteAuthor(UUID.fromString("93704eec-53ab-4f50-88ae-03466104ce57"));

        //add book to Shakespeare
        manager.addBookToAuthor(UUID.fromString("8cae924b-e7c4-4c0a-9772-f506ae999bb8"), "King Lir", BigDecimal.valueOf(20320));

        //Update Bushan
        manager.updateBook(UUID.fromString("680344f8-f152-40a8-bd28-00442251b8d3"),
                UUID.fromString("93704eec-53ab-4f50-88ae-03466104ce57"),
                "Letters from a Stoicism",
                BigDecimal.ONE);

        //Add author
        manager.addAuthor("Ernest Hemingway");

        //Update Ernest Hemingway
        manager.updateAuthor(UUID.fromString("b720affc-3030-4974-a13c-11887683aca8"),
                "Ernest Hemingway");

        //Delete Thinner
        manager.deleteBook(UUID.fromString("f0984cb1-9631-4a67-8475-de7777d88aba"));

        //Save
        DOMCustomParser.serialize(manager, Constants.XML_PATH);
    }
}
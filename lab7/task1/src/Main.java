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
        manager.deleteBook(UUID.fromString("806f7acb-cc33-42ae-8d35-44c6a05ece21"));

        //delete Shakespeare
        manager.deleteAuthor(UUID.fromString("8cae924b-e7c4-4c0a-9772-f506ae999bb8"));

        //add book to Remark
        manager.addBookToAuthor(UUID.fromString("98abbc6a-3a6c-49b5-b65d-703e4ae212be"), "4", BigDecimal.valueOf(230));

        //Update Bushan
        manager.updateBook(UUID.fromString("16c5eb63-3b4c-43af-801b-787e7586cda0"),
                UUID.fromString("93704eec-53ab-4f50-88ae-03466104ce57"),
                "33",
                BigDecimal.ONE);

        //Add author
        manager.addAuthor("Seneca");

        //Update Ernest Hemingway
        manager.updateAuthor(UUID.fromString("ec2e9337-4d33-4bbd-9ce6-796966991812"),
                "Ernest Hemin");


        //Save
        DOMCustomParser.serialize(manager, Constants.XML_PATH);
    }
}
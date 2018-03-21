package xml_xslt_jdbs;

import com.sun.org.apache.xerces.internal.parsers.SAXParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 18.12.2017
 */
public class XML {

    /**Создание 1.xml.
     * @param entries список полей для создания XML
     */
    public void createXML(List<String> entries) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            Document doc = factory.newDocumentBuilder().newDocument();
            Element rootElement = doc.createElement("entries");
            doc.appendChild(rootElement);

            for (String entry : entries) {
                Element entryElement = doc.createElement("entry");
                rootElement.appendChild(entryElement);
                Element field = doc.createElement("field");
                entryElement.appendChild(field);
                field.appendChild(doc.createTextNode(entry));
            }
            TransformerFactory.newInstance().newTransformer()
                    .transform(new DOMSource(doc), new StreamResult(
                            new File("1.xml")));
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Конвертер из 1.xml в 2.xml.
     */
    void convertXML() {
        try {
            TransformerFactory.newInstance()
                    .newTransformer(new StreamSource(
                            getClass().getResourceAsStream("/convert.xsl")))
                    .transform(new StreamSource("1.xml"), new StreamResult(
                            new File("2.xml")));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    /** Parser.
     * @return сумма полей*/
    long parse() {
        XMLReader reader = new SAXParser();
        Parser parser = new Parser();
        reader.setContentHandler(parser);

        try {
            reader.parse("2.xml");
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
        return parser.sum;
    }

    /**
     * Парсер.
     */
    private class Parser extends DefaultHandler {
        /**
         * Сумма полей.
         */
        private long sum = 0;
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            if (localName.equals("entry")) {
                this.sum += Integer.valueOf(attributes.getValue("field"));
            }
        }
    }
}

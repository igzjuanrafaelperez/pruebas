package utils;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created by ruben on 25/02/15.
 */
public class XMLUtils {

    private static final Logger LOGGER = LogManager.getLogger( XMLUtils.class.getName() );

    Document document;

    public XMLUtils () {
        generateDocument();
    }

    private void generateDocument() {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            LOGGER.error( e );
        }


        if (docBuilder != null) {
            this.document = docBuilder.newDocument();
        }
    }

    public Element generateNode(String nodeName, String nodeValue, Element nodeParent){

        Element node = this.document.createElement(nodeName);
        if(nodeValue!=null) {
            node.appendChild(this.document.createTextNode(nodeValue));
        }
        nodeParent.appendChild(node);

        return node;
    }

    public Element generateNode(String nodeName, String nodeValue){

        Element node = this.document.createElement(nodeName);
        if(nodeValue!=null) {
            node.appendChild(this.document.createTextNode(nodeValue));
        }
        this.document.appendChild(node);

        return node;
    }

    public String printDocument(){
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();

            transformer.transform(new DOMSource(this.document), new StreamResult(writer));

            return writer.getBuffer().toString().replaceAll("\\n|\\r", "");
        } catch (TransformerException e) {
            LOGGER.error( e );
            return null;
        }
    }
}

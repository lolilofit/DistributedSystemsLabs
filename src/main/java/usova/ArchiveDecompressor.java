package usova;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.IOException;

public class ArchiveDecompressor {
    private static final String FILE_PATH = "src/main/resources/RU-NVS.osm.bz2";

    private static final String NODE = "node";

    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();

    private static final Logger LOGGER = LogManager.getLogger(Main.class.getName());

    private XMLEventReader reader = null;

    public ArchiveDecompressor() {
        try {
            BZip2CompressorInputStream in = new BZip2CompressorInputStream(new FileInputStream(FILE_PATH));
            reader = FACTORY.createXMLEventReader(in);
        } catch (IOException | XMLStreamException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public XmlResponse read() {
        XmlResponse xmlResponse = new XmlResponse();

        try {
            String prevElementName = "";

            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();

                    if(startElement.getName().getLocalPart().equals(NODE)) {
                        String user = startElement.getAttributeByName(new QName("user")).getValue();
                        xmlResponse.incrementEdits(user);
                        prevElementName = NODE;
                    } else if(startElement.getName().getLocalPart().equals("tag") && prevElementName.equals(NODE)) {
                        String k = startElement.getAttributeByName(new QName("k")).getValue();
                        xmlResponse.incrementKeys(k);
                    }
                    else {
                        prevElementName = "";
                    }
                }
            }
            xmlResponse.sortAnswer();
            close();
        } catch (XMLStreamException e) {
            LOGGER.error(e.getMessage());
        }
        return xmlResponse;
    }

    private void close() {
        if (reader != null) {
            try {
                reader.close();
            } catch (XMLStreamException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }
}

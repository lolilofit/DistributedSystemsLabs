package usova;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import usova.generated.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.util.EventReaderDelegate;
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
            reader = new XsiTypeReader(reader);
        } catch (IOException | XMLStreamException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public XmlResponse read() {
        XmlResponse xmlResponse = new XmlResponse();

        try {
            JAXBContext jc = JAXBContext.newInstance(Node.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            Node node = (Node) unmarshaller.unmarshal(reader);

            /*
            Node node = null;

            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();

                    if(startElement.getName().getLocalPart().equals(NODE)) {
                        node = new Node();
                        readNodeAttributes(node, startElement, xmlResponse);
                    } else if(startElement.getName().getLocalPart().equals("tag") && node != null) {
                        readNodeTag(node, startElement, xmlResponse);
                    }
                    else {
                        node = null;
                    }
                }
            }
            xmlResponse.sortAnswer();
             */
            close();
        } catch (JAXBException e) {
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


    private static class XsiTypeReader extends EventReaderDelegate {
        public XsiTypeReader(XMLEventReader reader) {
            super(reader);
        }
    }
}

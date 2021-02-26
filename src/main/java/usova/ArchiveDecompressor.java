package usova;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import usova.generated.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import java.io.FileInputStream;
import java.io.IOException;

public class ArchiveDecompressor {
    private static final String FILE_PATH = "src/main/resources/RU-NVS.osm.bz2";

    private static final String NODE = "node";

    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();

    private static final Logger LOGGER = LogManager.getLogger(Main.class.getName());

    private XMLStreamReader reader = null;

    private Unmarshaller nodeUnmarshaller;

    public ArchiveDecompressor() {
        try {
            BZip2CompressorInputStream in = new BZip2CompressorInputStream(new FileInputStream(FILE_PATH));
            reader = FACTORY.createXMLStreamReader(in);
            reader = new XsiTypeReader(reader, "http://openstreetmap.org/osm/0.6");

            JAXBContext jc = JAXBContext.newInstance(Node.class);
            nodeUnmarshaller = jc.createUnmarshaller();
        } catch (IOException | XMLStreamException | JAXBException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public Node getNextNode() {
        try {
            while (reader.hasNext()) {
                int event = reader.next();

                if (event == XMLStreamReader.START_ELEMENT && reader.getLocalName().equals(NODE)) {
                    return nodeUnmarshaller.unmarshal(reader, Node.class).getValue();
                }
            }
        } catch (XMLStreamException | JAXBException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    public void close() {
        if (reader != null) {
            try {
                reader.close();
            } catch (XMLStreamException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }


    private static class XsiTypeReader extends StreamReaderDelegate {
        private final String namespaceURI;

        public XsiTypeReader(XMLStreamReader reader, String namespaceURI) {
            super(reader);
            this.namespaceURI = namespaceURI;
        }

        @Override
        public String getAttributeNamespace(int paramInt) {
            return super.getAttributeNamespace(paramInt);
        }

        @Override
        public String getNamespaceURI() {
            return namespaceURI;
        }
    }
}

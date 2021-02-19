package usova;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import usova.generated.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.util.EventReaderDelegate;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
            reader = new XsiTypeReader(reader, "http://openstreetmap.org/osm/0.6");
        } catch (IOException | XMLStreamException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public XmlResponse read() {
        XmlResponse xmlResponse = new XmlResponse();

        try {
            JAXBContext jc = JAXBContext.newInstance(Node.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();

            while (reader.hasNext()) {
                XMLEvent event = reader.peek();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();

                    if(startElement.getName().getLocalPart().equals(NODE)) {
                        JAXBElement<Node> node = unmarshaller.unmarshal(reader, Node.class);
                    }
                }
                reader.nextEvent();
            }
            xmlResponse.sortAnswer();

            close();
        } catch (XMLStreamException | JAXBException e) {
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
        private final XMLEventFactory factory = XMLEventFactory.newInstance();
        private final String namespaceURI;

        private int startElementCount = 0;

        public XsiTypeReader(XMLEventReader reader, String namespaceURI) {
            super(reader);
            this.namespaceURI = namespaceURI;
        }

        private StartElement withNamespace(StartElement startElement) {
            List<Namespace> namespaces = new ArrayList<>();
            namespaces.add(factory.createNamespace(namespaceURI));

            Iterator<Namespace> originalNamespaces = startElement.getNamespaces();
            while (originalNamespaces.hasNext()) {
                namespaces.add(originalNamespaces.next());
            }

            return factory.createStartElement(
                    new QName(namespaceURI, startElement.getName().getLocalPart()),
                    startElement.getAttributes(),
                    namespaces.iterator()
            );
        }

        @Override
        public XMLEvent nextEvent() throws XMLStreamException {
            XMLEvent event = super.nextEvent();
            if (event.isStartElement()) {
                if (++startElementCount == 1) {
                    return withNamespace(event.asStartElement());
                }
            }
            return event;
        }

        @Override
        public XMLEvent peek() throws XMLStreamException {
            XMLEvent event = super.peek();
            if (startElementCount == 0 && event.isStartElement()) {
                return withNamespace(event.asStartElement());
            } else {
                return event;
            }
        }
    }
}

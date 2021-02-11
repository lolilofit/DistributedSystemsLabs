package usova;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class Main {
    static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException, XMLStreamException {
        logger.debug("Hello, world");
        ArchiveDecompressor archiveDecompressor = new ArchiveDecompressor();
        XmlResponse response = archiveDecompressor.read();
        response.print();
    }
}

package usova;

import usova.generated.Node;
import usova.generated.Tag;

import javax.xml.namespace.QName;
import javax.xml.stream.events.StartElement;
import java.math.BigInteger;

public class XmlUtils {
    public static void readNodeAttributes(StartElement startElement, XmlResponse xmlResponse) {
        String user = startElement.getAttributeByName(new QName("user")).getValue();
        xmlResponse.incrementEdits(user);
    }

    public static void readNodeTag(StartElement startElement, XmlResponse xmlResponse) {
        String k = startElement.getAttributeByName(new QName("k")).getValue();
        xmlResponse.incrementKeys(k);
    }
}

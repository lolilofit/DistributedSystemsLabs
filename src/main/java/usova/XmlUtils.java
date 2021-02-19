package usova;

import usova.generated.Node;
import usova.generated.Tag;

import javax.xml.namespace.QName;
import javax.xml.stream.events.StartElement;
import java.math.BigInteger;

public class XmlUtils {
    public static void readNodeAttributes(Node node, StartElement startElement, XmlResponse xmlResponse) {
        String user = startElement.getAttributeByName(new QName("user")).getValue();
        xmlResponse.incrementEdits(user);
        node.setUser(user);

        String id = startElement.getAttributeByName(new QName("id")).getValue();
        node.setUser(id);

        String lat = startElement.getAttributeByName(new QName("lat")).getValue();
        node.setLat(Double.parseDouble(lat));

        String lon = startElement.getAttributeByName(new QName("lon")).getValue();
        node.setLon(Double.parseDouble(lon));

        String uid = startElement.getAttributeByName(new QName("uid")).getValue();
        node.setUid(BigInteger.valueOf(Long.parseLong(uid)));

        String visible = startElement.getAttributeByName(new QName("visible")).getValue();
        node.setVisible(Boolean.parseBoolean(visible));

        String version = startElement.getAttributeByName(new QName("version")).getValue();
        node.setVersion(BigInteger.valueOf(Long.parseLong(version)));

        String changeset = startElement.getAttributeByName(new QName("changeset")).getValue();
        node.setChangeset(BigInteger.valueOf(Long.parseLong(changeset)));

        String timestamp = startElement.getAttributeByName(new QName("timestamp")).getValue();
        node.setChangeset(BigInteger.valueOf(Long.parseLong(timestamp)));
    }

    public static void readNodeTag(Node node, StartElement startElement, XmlResponse xmlResponse) {
        String k = startElement.getAttributeByName(new QName("k")).getValue();
        xmlResponse.incrementKeys(k);

        String v = startElement.getAttributeByName(new QName("v")).getValue();

        Tag tag = new Tag();
        tag.setK(k);
        tag.setV(v);

        node.getTag().add(tag);
    }
}

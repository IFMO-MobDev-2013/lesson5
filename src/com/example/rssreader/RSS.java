package com.example.rssreader;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class RSS {
    public static String[] name;
    public static String[] text;

    public static void parse(Document doc){
        NodeList root = doc.getElementsByTagName("entry");
        String entry1;
        String summary;
        if (root.getLength() != 0) {
            entry1 = "entry";
            summary = "summary";
        } else {
            entry1 = "item";
            summary = "description";
            root = doc.getElementsByTagName(entry1);
        }

        name = new String[root.getLength()];
        text = new String[root.getLength()];
        for (int i = 0; i < root.getLength(); i++) {
            NodeList entry = root.item(i).getChildNodes();
            name[i] = getNodeValue("title", entry);
            text[i] = getNodeValue(summary, entry);

        }
    }

    protected static String getNodeValue(String tagName, NodeList nodes ) {
        for ( int x = 0; x < nodes.getLength(); x++ ) {
            Node node = nodes.item(x);
            if (node.getNodeName().equalsIgnoreCase(tagName)) {
                NodeList childNodes = node.getChildNodes();
                for (int y = 0; y < childNodes.getLength(); y++ ) {
                    Node data = childNodes.item(y);
                    if ( data.getNodeType() == Node.TEXT_NODE )
                        return data.getNodeValue();
                }
            }
        }
        return "";
    }
}

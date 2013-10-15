package ru.mermakov.projects.MRSSReader;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.MalformedURLException;
import java.net.URL;

public class DOMParser {
    private RSSFeed feed = new RSSFeed();

    public RSSFeed parseXml(String xml) {

        URL url = null;
        try {
            url = new URL(xml);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }

        try {
            // Create required instances
            DocumentBuilderFactory dbf;
            dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            // Parse the xml
            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();

            // Get all <item> tags.
            NodeList nl = doc.getElementsByTagName("item");
            NodeList n2 = doc.getElementsByTagName("entry");
            int length = nl.getLength();
            int length2 = n2.getLength();



            for (int i = 0; i < length; i++) {
                Node currentNode = nl.item(i);
                RSSItem item = new RSSItem();

                NodeList nchild = currentNode.getChildNodes();
                int clength = nchild.getLength();

                // Get the required elements from each Item
                for (int j = 1; j < clength; j = j + 2) {

                    Node thisNode = nchild.item(j);
                    String theString = null;
                    String nodeName = thisNode.getNodeName();

                    theString = nchild.item(j).getFirstChild().getNodeValue();

                    if (theString != null) {
                        if ("title".equals(nodeName)) {
                            // Node name is equals to 'title' so set the Node
                            // value to the Title in the RSSItem.
                            item.setTitle(theString);
                        }

                        else if ("description".equals(nodeName)) {
                            item.setDescription(theString);
                            String html = theString;
                            org.jsoup.nodes.Document docHtml = Jsoup
                                    .parse(html);
                            Elements imgEle = docHtml.select("img");
                            item.setImage(imgEle.attr("src"));
                        }

                        else if ("pubDate".equals(nodeName)) {

                            // We replace the plus and zero's in the date with
                            // empty string
                            String formatedDate = theString.replace(" +0000",
                                    "");
                            item.setDate(formatedDate);
                        }

                        else if("link".equals(nodeName)) {
                            item.setLink(theString);
                        }

                    }
                }
                feed.addItem(item);
            }
        } catch (Exception e) {
        }
        return feed;
    }
}

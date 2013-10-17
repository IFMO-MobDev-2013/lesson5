package com.java.android.dronov;

import com.java.android.dronov.RSS.Entry;
import com.java.android.dronov.RSS.FeedBack;
import com.java.android.dronov.RSS.RSSException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: dronov
 * Date: 16.10.13
 * Time: 21:13
 * To change this template use File | Settings | File Templates.
 */
public class RSSReader {
    public static final int ATOM_FORMAT = 1;
    public static final int RSS_FORMAT = 2;
    private String title = "";
    private String description = "";
    private String link = "";
    private String publishedDate;
    private ArrayList<Entry> array = null;
    private Exception exception;
    public FeedBack parse(String urlRSS) throws RSSException {
        try {
            URL url = new URL(urlRSS);
            URLConnection connection = url.openConnection();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();

            InputStream input = connection.getInputStream();
            Document document = builder.parse(input);
            Element root = document.getDocumentElement();

            NodeList mainItem = root.getElementsByTagName("item");
            NodeList mainEntry = root.getElementsByTagName("entry");
            if (mainItem.getLength() > 0) {
                parse(mainItem, RSS_FORMAT);
            } else if (mainEntry.getLength() > 0) {
                parse(mainEntry, ATOM_FORMAT);
            } else throw new RSSException("It's not a RSS. Please check your URL");
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            exception = new Exception("Bad URL");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            exception = new Exception("InputStream problem");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            exception = new Exception("Parser Exception");
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (RSSException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            exception = new Exception("It's not a RSS. Please check your URL");
        }
        return new FeedBack(array, exception);
    }

    private void parse(NodeList main, int format) {
        array = new ArrayList<Entry>();
        String descriptionFormat = "";
        String dateFormat = "";
        if (format == ATOM_FORMAT) {
            descriptionFormat = "summary";
            dateFormat = "published";
        } else if (format == RSS_FORMAT) {
            descriptionFormat = "description";
            dateFormat = "pubDate";
        }

        for (int i = 0; i < main.getLength(); i++) {
            Element currentElement = (Element) main.item(i);

            Element titleNode = (Element) currentElement.getElementsByTagName("title").item(0);
            Element descriptionNode = (Element) currentElement.getElementsByTagName(descriptionFormat).item(0);
            Element publishedDateNode = (Element) currentElement.getElementsByTagName(dateFormat).item(0);
            Element linkNode = (Element) currentElement.getElementsByTagName("link").item(0);

            try {
                title = titleNode.getFirstChild().getNodeValue();
            } catch (NullPointerException e) {
                title = "";
            }

            try {
                description = descriptionNode.getFirstChild().getNodeValue();
            } catch (NullPointerException e) {
                description = "";
            }
            try {
                publishedDate = publishedDateNode.getFirstChild().getNodeValue();
            } catch (NullPointerException e) {
                publishedDate = "";
            }
            try {
                link = linkNode.getFirstChild().getNodeValue();
            } catch (NullPointerException e) {
                link = "";
            }

            array.add(new Entry(title, description, link, publishedDate));
        }
    }

}

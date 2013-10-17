package com.example.untitled;

import android.os.AsyncTask;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Charm
 * Date: 16.10.13
 * Time: 17:59
 * To change this template use File | Settings | File Templates.
 */



public class getXml extends AsyncTask<Void,Void,ArrayList<RssItem>> {
    String link;
    getXml(String link) {
        this.link = link;
    }

    public ArrayList<RssItem> onPostExecute(ArrayList<RssItem>... result) {
        super.onPostExecute(result[0]);
        return result[0];
    }

    @Override
    protected ArrayList<RssItem> doInBackground(Void... params) {
        ArrayList<RssItem> rssItems = new ArrayList<RssItem>();
            try {
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream input = conn.getInputStream();
                    DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dB = dBF.newDocumentBuilder();
                    Document document = dB.parse(input);
                    Element element = document.getDocumentElement();
                    NodeList nodeList = element.getElementsByTagName("item");
                    if (nodeList.getLength() > 0) {
                        for (int i = 0; i < nodeList.getLength(); i++) {
                            Element entry = (Element) nodeList.item(i);
                            Element title = (Element) entry.getElementsByTagName("title").item(0);
                            Element description = (Element) entry.getElementsByTagName("description").item(0);
                            Element pubDate = (Element) entry.getElementsByTagName("pubDate").item(0);
                            Element link = (Element) entry.getElementsByTagName("link").item(0);
                            String strTitle = title.getFirstChild().getNodeValue();
                            String strDescription = description.getFirstChild().getNodeValue();
                            Date PubDate = new Date(pubDate.getFirstChild().getNodeValue());
                            String strLink = link.getFirstChild().getNodeValue();
                            RssItem rssItem = new RssItem(strTitle, strDescription,PubDate, strLink);
                            rssItems.add(rssItem);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return rssItems;
        }
}

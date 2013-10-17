package com.example.untitled1;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import android.os.AsyncTask;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Руслан
 * Date: 15.10.13
 * Time: 13:16
 * To change this template use File | Settings | File Templates.
 */

public class RSSFeed extends AsyncTask<Void, Void, ArrayList<Map<String, Object>> > {
    String link;

    public RSSFeed(String link){
        this.link = link;
    }



    @Override
    protected  ArrayList<Map<String, Object>> doInBackground(Void... params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(link);
        HttpResponse response;
        InputStream inputStream = null;
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            inputStream = entity.getContent();
            DocumentBuilderFactory dBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dBuilderFactory.newDocumentBuilder();
            Document document = dBuilder.parse(inputStream);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("item");
            if (nodeList.getLength() > 0){
                for(int i = 0; i < nodeList.getLength(); i++){
                    Element elem = (Element)nodeList.item(i);

                    Element title = (Element)elem.getElementsByTagName("title").item(0);
                    Element description = (Element) elem.getElementsByTagName("description").item(0);
                    Element date = (Element) elem.getElementsByTagName("pubDate").item(0);
                    Element link = (Element) elem.getElementsByTagName("link").item(0);

                    HashMap<String, Object> parsedNode = new HashMap<String, Object>();
                    parsedNode.put("title", title.getFirstChild().getNodeValue());
                    parsedNode.put("description", description.getFirstChild().getNodeValue());
                    parsedNode.put("pubDate", date.getFirstChild().getNodeValue());
                    parsedNode.put("link", link.getFirstChild().getNodeValue());

                    data.add(parsedNode);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e){
            e.printStackTrace();
        } catch (SAXException e){
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
      return data;
    }


    public ArrayList<Map<String, Object>> onPostExecute(ArrayList<Map<String, Object>>... result){
        super.onPostExecute(result[0]);
        return result[0];
    }


}

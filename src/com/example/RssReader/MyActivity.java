package com.example.RssReader;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyActivity extends Activity {

    ListView listView;
    public String[] links;
    public String[] authors;
    public String[] times;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        String rssUrl = "http://stackoverflow.com/feeds/tag/android";
        new downloadList().execute(rssUrl);
    }

    private void listInsert(String[] news) {
        listView = (ListView) findViewById(R.id.listView);
        ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>(
                news.length);
        Map<String, String> m;
        for (int i = 0; i < news.length; i++) {
            m = new HashMap<String, String>();
            m.put("author", authors[i]);
            m.put("title", news[i]);
            m.put("date", times[i]);
            data.add(m);
        }
        String[] from = {"author", "title", "date"};
        int[] to = {R.id.author, R.id.title, R.id.date};
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item, from, to);
        View header = getLayoutInflater().inflate(R.layout.listview_header_row, null);
        listView.addHeaderView(header);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> l, View view,
                                    int position, long id) {
                Intent intent = new Intent(MyActivity.this, webweb.class);
                intent.putExtra("link", links[position]);
                startActivity(intent);
            }
        });
    }

    class downloadList extends AsyncTask<String, Void, NodeList> {
        protected NodeList doInBackground(String... url) {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url[0]);
            try {
                HttpResponse response = client.execute(request);
                HttpEntity entity = response.getEntity();
                InputStream input = entity.getContent();

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setValidating(false);
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(input);
                doc.getDocumentElement().normalize();

                NodeList nodes = doc.getElementsByTagName("entry");
                return nodes;

            } catch(ClientProtocolException e) {
                e.printStackTrace();
            } catch(IOException e) {
                e.printStackTrace();
            } catch(SAXException e) {
                e.printStackTrace();
            } catch(ParserConfigurationException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected String convertTime(String badTime) {
            String year, month, day, hours, minutes;
            year = badTime.substring(2,4);
            month = badTime.substring(5,7);
            day = badTime.substring(8,10);
            hours = badTime.substring(11,13);
            minutes = badTime.substring(14,16);
            return hours + ":" + minutes + " " + day + "." + month + "." + year;
        }

        protected void onPostExecute(NodeList nodes) {
            String[] news = new String[nodes.getLength()];
            links = new String[nodes.getLength()];
            authors = new String[nodes.getLength()];
            times = new String[nodes.getLength()];
            Element element;
            Node temp;
            String author, title, link, published;
            for (int i = 0; i < nodes.getLength(); i++) {
                if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    element = (Element)nodes.item(i);
                    temp = element.getElementsByTagName("author").item(0);
                    author = ((Element)temp).getElementsByTagName("name").item(0).getTextContent();
                    title = ((Element)nodes.item(i)).getElementsByTagName("title").item(0).getTextContent();
                    published = ((Element)nodes.item(i)).getElementsByTagName("published").item(0).getTextContent();
                    temp = element.getElementsByTagName("link").item(0);
                    link = ((Element)temp).getAttribute("href");
                    news[i] = title;
                    authors[i] = author;
                    links[i] = link;
                    times[i] = convertTime(published);
                }
            }
            listInsert(news);
        }



    }

}

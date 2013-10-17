package com.example.RSSReader;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;

public class RSSActivity extends Activity {

    private ArrayList<Node> nodes;
    private ListView listView;
    private String link;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rss);

        listView = (ListView) findViewById(R.id.listView);

        RSSTape rssTape = new RSSTape();
        rssTape.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View view, int index, long arg3) {

                Intent intent = new Intent(RSSActivity.this, ArticleActivity.class);
                intent.putExtra("link", nodes.get(index).getLink());
                startActivity(intent);
            }
        });
    }

    private class RSSTape extends AsyncTask < Void, Void, ArrayList<Node> > {

        @Override
        protected ArrayList<Node> doInBackground(Void... params) {
            return getTape();
        }

        @Override
        protected void onPostExecute(ArrayList<Node> result) {
            nodes = result;

            ArrayList<HashMap<String, String>> items = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;


            for (int i = 0; i < nodes.size(); i++) {
                map = new HashMap<String, String>();
                map.put("title", nodes.get(i).getTitle());
                map.put("date", nodes.get(i).getDate().toString());
                map.put("description", nodes.get(i).getDescription());
                items.add(map);
            }

            SimpleAdapter adapter = new SimpleAdapter(RSSActivity.this, items, R.layout.listview,
                    new String[]{"title", "date", "description"},
                    new int[] {R.id.title, R.id.date, R.id.description});

            listView.setAdapter(adapter);
        }

        private ArrayList<Node> getTape() {
            String request;

            ArrayList<Node> result = new ArrayList<Node>();
            request = "http://news.rambler.ru/rss/head/";
            //request = "http://4pda.ru/feed";

            InputStream inputStream = null;

            URL url;
            HttpURLConnection connect;

            try {
                url = new URL(request);
                connect = (HttpURLConnection) url.openConnection();

                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

                inputStream = connect.getInputStream();

                Document document = documentBuilder.parse(inputStream);

                Element element = document.getDocumentElement();

                NodeList nodeList = element.getElementsByTagName("item");

                for (int i = 0; i < nodeList.getLength(); i++)  {

                    Element main = (Element) nodeList.item(i);

                    Element child = (Element) main.getElementsByTagName("title").item(0);
                    String title = child.getFirstChild().getNodeValue();

                    child = (Element) main.getElementsByTagName("description").item(0);
                    String description = child.getFirstChild().getNodeValue();


                    child = (Element) main.getElementsByTagName("pubDate").item(0);
                    Date date = new Date(child.getFirstChild().getNodeValue());

                    child = (Element) main.getElementsByTagName("link").item(0);
                    String link = child.getFirstChild().getNodeValue();

                    result.add(new Node(title, description, date, link));
                }
            }
            catch (Exception e) {

            }
            finally {
                try {
                    inputStream.close();
                } catch (IOException e) {}
            }

            return result;
        }
    }
}

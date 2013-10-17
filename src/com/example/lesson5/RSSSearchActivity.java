package com.example.lesson5;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.net.URLConnection;

public class RSSSearchActivity extends Activity {

    private final String rssURL = "http://news.google.com/?output=rss";
    private ListView listView;
    private MyAdapter myAdapter;
    private LinearLayout linearLayout;
    private TextView textView;
    private TextView[] title;
    private String[] description;
    private Document rss;

    public class RSSDownloader extends AsyncTask<String, Void, Document> {

        @Override
        protected Document doInBackground(String... params) {
            try {
                textView.setText("Downloading...");
                URL url = new URL(rssURL);
                URLConnection urlConnection = url.openConnection();
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                rss = db.parse(urlConnection.getInputStream());
                urlConnection.getInputStream().close();
                return rss;
            } catch (Exception e) {
                textView.setText(e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(Document result) {
            textView.setText("Downloaded.");
            rss = result;
            showResults();
        }
    }

    public class MyAdapter extends ArrayAdapter<TextView> {

        public MyAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public TextView getItem(int position) {
            return title[position];
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return title[position];
        }
    }

    public void showResults(){
        textView.setText(rssURL);
        if (rss.getElementsByTagName("rss").getLength() > 0) {
            Node root = (rss.getElementsByTagName("rss")).item(0);
            Node channel = ((Element) root).getElementsByTagName("channel").item(0);
            NodeList items = ((Element) channel).getElementsByTagName("item");
            title = new TextView[items.getLength()];
            description = new String[items.getLength()];
            for (int i = 0; i < items.getLength(); i++) {
                Node item = items.item(i);
                String t = ((Element)((Element) item).getElementsByTagName("title").item(0)).getChildNodes().item(0).getNodeValue();
                String d = ((Element)((Element) item).getElementsByTagName("pubDate").item(0)).getChildNodes().item(0).getNodeValue();
                description[i] = ((Element)((Element) item).getElementsByTagName("description").item(0)).getChildNodes().item(0).getNodeValue();
                title[i] = new TextView(this);
                title[i].setText(t + "\n" + d);
                myAdapter.add(title[i]);
            }
        } else if (rss.getElementsByTagName("feed").getLength() > 0) {
            Node root = (rss.getElementsByTagName("feed")).item(0);
            NodeList items = ((Element) root).getElementsByTagName("entry");
            title = new TextView[items.getLength()];
            description = new String[items.getLength()];
            for (int i = 0; i < items.getLength(); i++) {
                Node item = items.item(i);
                String t = ((Element)((Element) item).getElementsByTagName("title").item(0)).getChildNodes().item(0).getNodeValue();
                String d = ((Element)((Element) item).getElementsByTagName("updated").item(0)).getChildNodes().item(0).getNodeValue();
                description[i] = ((Element)((Element) item).getElementsByTagName("summary").item(0)).getChildNodes().item(0).getNodeValue();
                title[i] = new TextView(this);
                title[i].setText(t + "\n" + d);
                myAdapter.add(title[i]);
            }
        } else if (rss.getElementsByTagName("rdf").getLength() > 0) {
            Node root = (rss.getElementsByTagName("rdf")).item(0);
            NodeList items = ((Element) root).getElementsByTagName("item");
            title = new TextView[items.getLength()];
            description = new String[items.getLength()];
            for (int i = 0; i < items.getLength(); i++) {
                Node item = items.item(i);
                String t = ((Element)((Element) item).getElementsByTagName("title").item(0)).getChildNodes().item(0).getNodeValue();
                description[i] = ((Element)((Element) item).getElementsByTagName("description").item(0)).getChildNodes().item(0).getNodeValue();
                title[i] = new TextView(this);
                title[i].setText(t);
                myAdapter.add(title[i]);
            }
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toResultActivity(position);
            }
        });
        linearLayout.addView(listView);
    }

    public void toResultActivity(int position) {
        Intent intent = new Intent(this, RSSResultActivity.class);
        intent.putExtra("html", description[position]);
        startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textView = (TextView) findViewById(R.id.mainTextView);
        listView = new ListView(this);
        myAdapter = new MyAdapter(this, R.layout.list_item);
        listView.setAdapter(myAdapter);
        linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
        new RSSDownloader().execute(rssURL);
    }
}

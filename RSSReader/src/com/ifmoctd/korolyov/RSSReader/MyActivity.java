package com.ifmoctd.korolyov.RSSReader;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
import java.util.ArrayList;
import java.util.Date;

public class MyActivity extends Activity {
    ArrayList<RSSItem> list;
    ArrayAdapter<RSSItem> arrayAdapter;
    ListView listView;
    URL url;
    final static String ID_EXTRA1 = "Title";
    final static String ID_EXTRA2 = "Content";


    MyTask myTask;

    void readRSSAtom(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {

            Element entry = (Element) nodeList.item(i);

            Element _titleElement = (Element) entry.getElementsByTagName(
                    "title").item(0);
            Element _descriptionElement = (Element) entry
                    .getElementsByTagName("summary").item(0);
            Element _pubDateElement = (Element) entry
                    .getElementsByTagName("published").item(0);
            Element _linkElement = (Element) entry.getElementsByTagName(
                    "link").item(0);

            String _title;
            try {
                _title = _titleElement.getFirstChild().getNodeValue();
            } catch (NullPointerException e) {
                _title = "";
            }
            String _description = "";
            try {
                for (Node node = _descriptionElement.getFirstChild(); node != null; node = node.getNextSibling()) {
                    _description = _description + node.getNodeValue();
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            Date _pubDate;
            try {
                _pubDate = new Date(_pubDateElement.getFirstChild().getNodeValue());
            } catch (Exception e) {
                _pubDate = new Date();
            }

            String _link;
            try {
                _link = _linkElement.getFirstChild().getNodeValue();
            } catch (NullPointerException e) {
                _link = "";
            }

            RSSItem rssItem = new RSSItem(_title, _description, _pubDate, _link);
            list.add(rssItem);
        }
    }

    void readRSSOther(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {

            Element entry = (Element) nodeList.item(i);

            Element _titleElement = (Element) entry.getElementsByTagName(
                    "title").item(0);
            Element _descriptionElement = (Element) entry
                    .getElementsByTagName("description").item(0);
            Element _pubDateElement = (Element) entry
                    .getElementsByTagName("pubDate").item(0);
            Element _linkElement = (Element) entry.getElementsByTagName(
                    "link").item(0);

            String _title;
            try {
                _title = _titleElement.getFirstChild().getNodeValue();
            } catch (NullPointerException e) {
                _title = "";
            }
            String _description = "";
            try {
                for (Node node = _descriptionElement.getFirstChild(); node != null; node = node.getNextSibling()) {
                    _description = _description + node.getNodeValue();
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            Date _pubDate = new Date(_pubDateElement.getFirstChild().getNodeValue());

            String _link;
            try {
                _link = _linkElement.getFirstChild().getNodeValue();
            } catch (NullPointerException e) {
                _link = "";
            }

            RSSItem rssItem = new RSSItem(_title, _description, _pubDate, _link);
            list.add(rssItem);
        }
    }


    public class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection httpCon = null;
            try {
                httpCon = (HttpURLConnection) url.openConnection();
                httpCon.setConnectTimeout(20000);
                try {
                    if (httpCon.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream is = httpCon.getInputStream();
                        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                                .newInstance();
                        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

                        Document document = documentBuilder.parse(is);
                        Element element = document.getDocumentElement();

                        NodeList nodeList = element.getElementsByTagName("item");

                        if (nodeList.getLength() > 0) {
                            readRSSOther(nodeList);
                        } else {
                            nodeList = element.getElementsByTagName("entry");
                            if (nodeList.getLength() > 0) {
                                readRSSAtom(nodeList);
                            } else {
                                Toast.makeText(getApplicationContext(), "Ошибка чтения RSS канала", 2000);
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Ошибка HTTPConnection", 2000);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }
            } catch (
                    IOException e
                    )

            {
                e.printStackTrace();
            } finally

            {
                if (httpCon != null) {
                    httpCon.disconnect();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listView.setAdapter(arrayAdapter);
        }

    }

    void refreshRSSList() {
        list.clear();
        myTask = new MyTask();
        myTask.execute();

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<RSSItem>();
        arrayAdapter = new ArrayAdapter<RSSItem>(this, R.layout.list_item, list);
        Button button = (Button) findViewById(R.id.button);
        final EditText editText = (EditText) findViewById(R.id.editText);

        try {
            url = new URL("http://stackoverflow.com/feeds/tag/android");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long arg) {
                RSSItem rssItem = list.get(index);
                Intent intent = new Intent();
                intent.putExtra("Title", rssItem.toString());
                if (rssItem.getDescription().length() != 0) {
                    intent.putExtra("Content", rssItem.getDescription() + "<br>" + "<a href=\"" + rssItem.getLink() + "\">Link</a>");
                } else
                    intent.putExtra("Content", "<a href=\"" + rssItem.getLink() + "\">Link</a>");
                intent.setClass(getApplicationContext(), RSSItemActivity.class);
                startActivity(intent);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    url = new URL(editText.getText().toString());
                    refreshRSSList();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error", 2000);
                }
            }
        });

        refreshRSSList();


    }
}

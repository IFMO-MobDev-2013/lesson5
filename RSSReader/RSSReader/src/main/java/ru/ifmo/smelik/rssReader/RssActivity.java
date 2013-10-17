package ru.ifmo.smelik.rssReader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import ru.ifmo.smelik.rss.R;

/**
 * Simple RSS Reader
 * Created by Nick Smelik on 16.10.13.
 */
public class RssActivity extends Activity {

    private static final String TITLE = "title";
    private static final String SUMMARY = "summary";
    private static final String PUBLISHED = "published";
    private static final String LINK = "link";
    private static final String DESCRIPTION = "description";
    private static final String PUBDATE = "pubDate";
    private static final String ITEM = "item";
    private static final String ENTRY = "entry";

    public static RssItem selectedRssItem = null;
    String feedUrl = "";
    ListView rssListView = null;
    ArrayList<RssItem> rssItems = new ArrayList<RssItem>();
    ArrayAdapter<RssItem> aa = null;
    public ProgressDialog dialog;
    Context ctx;

    Connection connect;

    private void getAtomRss(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element entry = (Element) nodeList.item(i);

            Element _titleE = (Element) entry.getElementsByTagName(TITLE).item(0);
            Element _descriptionE = (Element) entry.getElementsByTagName(SUMMARY).item(0);
            Element _pubDateE = (Element) entry.getElementsByTagName(PUBLISHED).item(0);
            Element _linkE = (Element) entry.getElementsByTagName(LINK).item(0);

            String _title;
            try {
                _title = _titleE.getFirstChild().getNodeValue();
            } catch (NullPointerException e) {
                _title = "";
            }
            String _description;
            try {

                _description = _descriptionE.getFirstChild().getNodeValue();
            } catch (NullPointerException e) {
                _description = "";
            }

            Date _pubDate;
            try {
                _pubDate = new Date(_pubDateE.getFirstChild().getNodeValue());
            } catch (Exception e) {
                _pubDate = new Date();
            }

            String _link;
            try {
                _link = _linkE.getFirstChild().getNodeValue();
            } catch (NullPointerException e) {
                _link = "";
            }

            RssItem rssItem = new RssItem(_title, _description, _pubDate, _link);
            rssItems.add(rssItem);
        }
    }

    private void getRss(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {

            Element entry = (Element) nodeList.item(i);
            Element _titleE = (Element) entry.getElementsByTagName(TITLE).item(0);
            Element _descriptionE = (Element) entry.getElementsByTagName(DESCRIPTION).item(0);
            Element _pubDateE = (Element) entry.getElementsByTagName(PUBDATE).item(0);
            Element _linkE = (Element) entry.getElementsByTagName(LINK).item(0);

            String _title;
            try {
                _title = _titleE.getFirstChild().getNodeValue();
            } catch (NullPointerException e) {
                _title = "";
            }
            String _description;
            try {

                _description = _descriptionE.getFirstChild().getNodeValue();
            } catch (NullPointerException e) {
                _description = "";
            }
            Date _pubDate = new Date(_pubDateE.getFirstChild().getNodeValue());

            String _link;
            try {
                _link = _linkE.getFirstChild().getNodeValue();
            } catch (NullPointerException e) {
                _link = "";
            }

            RssItem rssItem = new RssItem(_title, _description, _pubDate, _link);
            rssItems.add(rssItem);
        }
    }

    public class Connection extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ctx);
            dialog.setMessage(getString(R.string.load));
            dialog.setIndeterminate(true);
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection conn = null;

            try {
                URL url = new URL(feedUrl);
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(20000);

                try {

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream is = conn.getInputStream();

                        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                        DocumentBuilder db = dbf.newDocumentBuilder();

                        Document document = db.parse(is);
                        Element element = document.getDocumentElement();

                        NodeList nodeList = element.getElementsByTagName(ITEM);

                        if (nodeList.getLength() > 0) {
                            getRss(nodeList);
                        } else {
                            nodeList = element.getElementsByTagName(ENTRY);
                            if (nodeList.getLength() > 0) {
                                getAtomRss(nodeList);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null)
                    conn.disconnect();
            }
        return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            rssListView.setAdapter(aa);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_rss_layout);
        ctx = this;

        rssListView = (ListView) findViewById(R.id.rssListView);
        rssItems = new ArrayList<RssItem>();
        feedUrl = getIntent().getExtras().getCharSequence("URL").toString();
        aa = new ArrayAdapter<RssItem>(this, R.layout.list_item, rssItems);

        rssListView.setAdapter(aa);
        refreshRssList();

        final Intent intent = new Intent(this, RssItemDisplayer.class);

        rssListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long arg) {

                selectedRssItem = rssItems.get(index);
                startActivity(intent);
            }
        });
    }

    private void refreshRssList() {
        rssItems.clear();
        connect = new Connection();
        connect.execute();
    }
}

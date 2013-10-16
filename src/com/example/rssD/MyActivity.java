package com.example.rssD;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.*;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MyActivity extends Activity {

    void sendMsg(String c)
    {
        Toast toast = Toast.makeText(getApplicationContext(), c, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static final String ns = null;

    class MyParser  {

        private void readItem(XmlPullParser parser) throws XmlPullParserException, IOException {
            parser.require(XmlPullParser.START_TAG, ns, "entry");
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                if (name.equals("title")) {
                    parser.require(XmlPullParser.START_TAG, ns, "title");
                    titles.add(readText(parser));
                    parser.require(XmlPullParser.END_TAG, ns, "title");
                } else if (name.equals("summary")) {
                    parser.require(XmlPullParser.START_TAG, ns, "summary");
                    summaries.add(readText(parser));
                    parser.require(XmlPullParser.END_TAG, ns, "summary");
                } else if (name.equals("link")) {
                    String link = "";
                    parser.require(XmlPullParser.START_TAG, ns, "link");
                    String tag = parser.getName();
                    String relType = parser.getAttributeValue(null, "rel");
                    if (tag.equals("link")) {
                        if (relType.equals("alternate")){
                            link = parser.getAttributeValue(null, "href");
                            parser.nextTag();
                        }
                    }
                    parser.require(XmlPullParser.END_TAG, ns, "link");
                    links.add(link);
                } else {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        throw new IllegalStateException();
                    }
                    int depth = 1;
                    while (depth != 0) {
                        switch (parser.next()) {
                            case XmlPullParser.END_TAG:
                                depth--;
                                break;
                            case XmlPullParser.START_TAG:
                                depth++;
                                break;
                        }
                    }
                }
            }
        }

        private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
            String result = "";
            if (parser.next() == XmlPullParser.TEXT) {
                result = parser.getText();
                parser.nextTag();
            }
            return result;
        }

        private void readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
            summaries = new ArrayList();
            links = new ArrayList();
            titles = new ArrayList();

            parser.require(XmlPullParser.START_TAG, ns, "feed");
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                if (name.equals("entry")) {
                    readItem(parser);
                } else {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        throw new IllegalStateException();
                    }
                    int depth = 1;
                    while (depth != 0) {
                        switch (parser.next()) {
                            case XmlPullParser.END_TAG:
                                depth--;
                                break;
                            case XmlPullParser.START_TAG:
                                depth++;
                                break;
                        }
                    }
                }
            }
        }

        public void parse(InputStream in) throws XmlPullParserException, IOException {
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                parser.nextTag();
                readFeed(parser);
            } finally {
                in.close();
            }
        }

    }

    private String loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
        InputStream stream = null;
        MyParser parser = new MyParser();
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            stream = conn.getInputStream();
            parser.parse(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return "OK";
    }

    WebView myWebView;
    ViewSwitcher viewSwitcher;
    boolean stateMenu = true;
    ArrayAdapter adapter;
    TextView textView;
    TextView textView1;
    ListView listView;
    ArrayList<String> titles;
    ArrayList<String> links;
    ArrayList<String> summaries;
    Button button1;

    private class MyTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                String res = loadXmlFromNetwork(urls[0]);
                if(!res.equals("OK"))
                    sendMsg(res);
                return res;
            } catch (IOException e) {
                sendMsg("connection_error");
                return ("ERROR");
            } catch (XmlPullParserException e) {
                sendMsg("xml_error");
                return ("ERROR");
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("ERROR"))
                return;
            adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, titles);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(mMessageClickedHandler);
        }
    }

    public AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            textView.setText(titles.get(position));
            textView1.setText(links.get(position));
            myWebView.loadData(summaries.get(position), "text/html", null);
            if(stateMenu)
            {
                viewSwitcher.showNext();
                stateMenu = false;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final Button button = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button1);
        final EditText editText = (EditText) findViewById(R.id.editText);
        myWebView = (WebView) findViewById(R.id.webView);
        viewSwitcher = (ViewSwitcher) findViewById(R.id.viewSwitcher);
        listView = (ListView) findViewById(R.id.listView);
        textView = (TextView) findViewById(R.id.textView);
        textView1 = (TextView) findViewById(R.id.textView1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyTask().execute(editText.getText().toString());
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!stateMenu) {
                    viewSwitcher.showNext();
                    stateMenu = true;
                }
            }
        });
    }
}
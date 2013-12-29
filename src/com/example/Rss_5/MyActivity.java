package com.example.Rss_5;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

public class MyActivity extends Activity {
    ArrayList<Record> Records;
    ArrayAdapter<Record> arrayAdapter;
    ListView rsslistView;
    URL url;
    MyTask mt;
    String encoding;

    class MySAXParser extends DefaultHandler {
        String element = null;
        Record record = new Record();
        boolean flag = false;

        @Override
        public void startDocument() {
        }

        @Override
        public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
            element = localName;
            if (localName.equals("item") || localName.equals("entry")) {
                record = new Record();
                Records.add(new Record());
                flag = true;
            }
        }

        @Override
        public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
            if (localName.equals("item") || localName.equals("entry")) {
                flag = false;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String s = new String(ch, start, length);

            String v = null;
            try {
                v = new String(s.getBytes("ISO-8859-1"), encoding);
            } catch (UnsupportedEncodingException e) {
            }
            if (Records.size() > 0) {
                if (element.equals("title")) {
                    Records.get(Records.size() - 1).title += v;
                } else if (element.equals("link")) {
                    Records.get(Records.size() - 1).link += v;
                } else if (element.equals("description") || element.equals("summary")) {
                    Records.get(Records.size() - 1).description += v;
                }
            }
        }
    }

    public class MyTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Void doInBackground(Void... voids) {

            boolean f = false;
            InputStream inputStream = null;
            try {
                URLConnection conn = null;

                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser parser = factory.newSAXParser();
                XMLReader xmlReader = parser.getXMLReader();
                xmlReader.setContentHandler(new MySAXParser());


                encoding = "utf-8";
                conn = url.openConnection();
                conn.setConnectTimeout(15000);
                conn.connect();
                f = true;
                inputStream = conn.getInputStream();
                if (inputStream != null) {
                    Scanner scanner = new Scanner(inputStream, "ISO-8859-1");
                    boolean flag = false;
                    encoding = "";
                    while (!flag) {
                        String a = scanner.nextLine();
                        if (!a.contains("encoding=")) continue;
                        for (int i = a.indexOf("encoding=") + "encoding=".length() + 1; ; i++) {

                            if (a.charAt(i) == '"') {
                                flag = true;
                                break;
                            }
                            encoding += a.charAt(i);
                        }
                    }
                    scanner.close();
                }
                inputStream.close();
                conn = url.openConnection();
                conn.connect();
                inputStream = conn.getInputStream();
                if (inputStream != null) {
                    InputSource inputSource = new InputSource(inputStream);
                    inputSource.setEncoding("ISO-8859-1");
                    xmlReader.parse(inputSource);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (f == true) {
                    if (inputStream != null)
                        try {
                            inputStream.close();
                        } catch (IOException e) {

                        }

                }

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            rsslistView.setAdapter(arrayAdapter);
            super.onPostExecute(aVoid);

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        rsslistView = (ListView) findViewById(R.id.listView);
        Records = new ArrayList<Record>();
        arrayAdapter = new ArrayAdapter<Record>(this, R.layout.list_item, Records);
        final Button button = (Button) findViewById(R.id.button);
        final EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText("http://stackoverflow.com/feeds/tag/android");
        try {
            url = new URL(editText.getText().toString());
            refresh();
        } catch (MalformedURLException e) {
            Toast.makeText(getApplicationContext(), "Error", 5000);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Records.clear();
                rsslistView.setAdapter(arrayAdapter);
                try {
                    url = new URL(editText.getText().toString());
                    refresh();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error", 5000);
                }
            }
        });

        rsslistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Record rssItem = Records.get(i);
                Intent intent = new Intent();
                intent.putExtra("description", rssItem.getDescription());
                intent.setClass(getApplicationContext(), Feed.class);
                startActivity(intent);

            }
        });


    }

    void refresh() {
        Records.clear();
        rsslistView.setAdapter(arrayAdapter);
        mt = new MyTask();
        mt.execute();
    }
}

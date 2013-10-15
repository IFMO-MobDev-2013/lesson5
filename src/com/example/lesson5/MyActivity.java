package com.example.lesson5;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private ListView listView;
    private Button downloadButton;
    private EditText inputUri;
    private Context context;
    public static Document doc;
    private ArrayList<RSSEntry> itemList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = this;
        listView = (ListView)findViewById(R.id.listView);
        downloadButton = (Button)findViewById(R.id.button);
        inputUri = (EditText)findViewById(R.id.textField);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String s = inputUri.getText().toString();
                String s = "http://stackoverflow.com/feeds/tag/android";
                if (validUrl(s)) {
                    new DownloadXmlTask().execute(s);
                }
                else {
                    Toast.makeText(context, "InvalidUri", 3000);
                }
            }
        });
    }
    private class DownloadXmlTask extends AsyncTask<String, Void, String> {
        private String connectionError;
        private String xmlError;
        @Override
        protected String doInBackground(String... urls) {
            connectionError = getResources().getString(R.string.connection_error);
            xmlError = getResources().getString(R.string.xml_error);
            try {
                return loadXml(urls[0]);
            } catch (IOException e) {
                return connectionError;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            setContentView(R.layout.main);
            // Displays the HTML string in the UI via a WebView

            if (xmlError.equals(result) || connectionError.equals(result)) {
                Toast.makeText(context, result, 2000);
            }

            else {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                try {

                    DocumentBuilder db = dbf.newDocumentBuilder();

                    InputSource is = new InputSource();
                    is.setCharacterStream(new StringReader(result));
                    doc = db.parse(is);

                    populateListView();


                } catch (ParserConfigurationException e) {
                    Toast.makeText(context, "Error: " + e.getMessage(), 3000);
                } catch (SAXException e) {
                    Toast.makeText(context, "Error: " + e.getMessage(), 3000);
                    //Log.e("Error: ", e.getMessage());
                } catch (IOException e) {
                    Toast.makeText(context, "Error: " + e.getMessage(), 3000);
                    //Log.e("Error: ", e.getMessage());
                }
                // return DOM

            }
            //WebView myWebView = (WebView) findViewById(R.id.webview);
            //myWebView.loadData(result, "text/html", null);
        }
    }

    private void populateListView() {
        itemList = new ArrayList<>();
        Element e = doc.getDocumentElement();
        NodeList entries;
        boolean isAtom = false;
        if (e.getTagName().equals("feed")) {
            entries = e.getElementsByTagName("entry");
            isAtom = true;
        }
        else {
            entries = e.getElementsByTagName("item");
        }
        for (int i = 0; i < entries.getLength(); i++) {
            Element el = (Element)entries.item(i);
            String title;
            try {
                title = el.getElementsByTagName("title").item(0).getChildNodes().item(0).getNodeValue();
            }
            catch (NullPointerException npe) {
                title = "";
            }
            String link = "";
            if (el.getElementsByTagName("link").getLength() > 0) {
                try {
                    link = el.getElementsByTagName("link").item(0).getChildNodes().item(0).getNodeValue();
                }
                catch (NullPointerException npe) {
                    link = "";

                }
            }
            String description = "";
            try {
                if (isAtom) {
                    NodeList nl1 = el.getElementsByTagName("summary");
                    NodeList nl2 = el.getElementsByTagName("content");
                    if (nl1.getLength() > 0) {
                        description = nl1.item(0).getChildNodes().item(0).getNodeValue();
                    }
                    else if (nl2.getLength() > 0) {
                        description = nl2.item(0).getChildNodes().item(0).getNodeValue();
                    }
                }
                else {
                    NodeList nl = el.getElementsByTagName("description");
                    if (nl.getLength() > 0) {
                        description = nl.item(0).getChildNodes().item(0).getNodeValue();
                    }
                }
            } catch (NullPointerException npe) {
                description = "";
            }
            itemList.add(new RSSEntry(title, description, link));
        }
        ArrayAdapter<RSSEntry> arrayAdapter = new ArrayAdapter<>(context, R.layout.itemlayout, itemList);
        listView.setAdapter(arrayAdapter);
        arrayAdapter.addAll(itemList);
    }

    private String loadXml (String url) throws IOException {

        HttpClient httpclient = new DefaultHttpClient();

        // Prepare a request object
        HttpGet httpget = new HttpGet(url);

        // Execute the request
        HttpResponse response;
        response = httpclient.execute(httpget);
        // Examine the response status
        Log.i("Return status",response.getStatusLine().toString());

        // Get hold of the response entity
        HttpEntity entity = response.getEntity();
        // If the response does not enclose an entity, there is no need
        // to worry about connection release

        if (entity != null) {

            // A Simple JSON Response Read
            InputStream instream = entity.getContent();
            String result= convertStreamToString(instream);
            // now you have the string representation of the HTML request
            instream.close();
            return result;
        }
        else {
            throw new IOException("response entity is null");
        }


    }


    private String convertStreamToString(InputStream is) throws IOException {
    /*
     * To convert the InputStream to String we use the BufferedReader.readLine()
     * method. We iterate until the BufferedReader return null which means
     * there's no more data to read. Each line will appended to a StringBuilder
     * and returned as String.
     */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        is.close();
        return sb.toString();
    }
    private boolean validUrl(String url) {
        return URLUtil.isValidUrl(url);
    }
}

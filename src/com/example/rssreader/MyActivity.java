package com.example.rssreader;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    ListView listview;
    EditText editText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listview = (ListView) findViewById(R.id.listview);
        editText = (EditText)findViewById(R.id.EditText);
        //String url = "http://stackoverflow.com/feeds/tag/android";

        if (RSS.name != null) {
            createListView();
        }

    }

    public void parseRss(View v) {
        editText.setHint("");
        String url = editText.getText().toString();
        Document rss = null;

        try {
            rss = new DownloadRSS().execute(url).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if (rss != null) {
        RSS.parse(rss);
        createListView();
        } else {
            editText.setText("");
            editText.setHint("incorrect url");
        }
    }

    public void createListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, RSS.name);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                Intent i = new Intent(MyActivity.this, SecondActivity.class);
                i.putExtra("text", RSS.text[position]);
                startActivity(i);
                finish();
            }
        });

    }

    private  class DownloadRSS extends AsyncTask<String, Void, Document> {

        @Override
        protected Document doInBackground(String... params) {
            String url = params[0];
            String xml = getXmlFromUrl(url);
            Document doc = null;

            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();


                //String encoding = getEncoding(xml);
                //System.out.println(encoding + "KJHDKSHDKJH");
                ByteArrayInputStream encXML = new ByteArrayInputStream(xml.getBytes("utf-8"));

                doc = builder.parse(encXML);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return doc;
        }





        public String getXmlFromUrl(String url) {
            String xml = null;

            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                xml = EntityUtils.toString(httpEntity);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return xml;
        }

        private String getEncoding(String xml) {
            int start = xml.indexOf("encoding=") + 10;
            int end = xml.indexOf(">", start) - 2;
            System.out.println(start + "STAR" + xml.charAt(9));
            return xml.substring(start,end);
        }

    }
}

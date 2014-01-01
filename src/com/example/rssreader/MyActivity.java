package com.example.rssreader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

public class MyActivity extends Activity {


    ListView listview;
    EditText editText;
    ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listview = (ListView) findViewById(R.id.listview);
        editText = (EditText) findViewById(R.id.EditText);

        if (RSS.name != null) {
            createListView();
        }

        editText.setHint("http://stackoverflow.com/feeds/tag/android");
    }

    public void parseRss(View v) {
        String hint = editText.getHint().toString();
        editText.setHint("");
        String url;
        String text = editText.getText().toString();
        if (hint.length() == 0 || text.length() != 0) {
            url = text;
        } else {
            url = hint;
        }
        new DownloadRSS().execute(url);
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

    private class DownloadRSS extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MyActivity.this, "", "Downloading...");
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String url = params[0];
            String xml = getXmlFromUrl(url);
            Document doc = null;
            Boolean error;

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

            if (doc != null) {
                RSS.parse(doc);
                error = false;
            } else {
                error = true;
            }
            return error;
        }

        @Override
        protected void onPostExecute(Boolean error) {
            progressDialog.dismiss();
            if (error) {
                editText.setText("");
                editText.setHint("incorrect url");
            } else {
                createListView();
            }
        }


        public String getXmlFromUrl(String url) {
            String xml = null;

            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                xml = EntityUtils.toString(httpEntity);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return xml;
        }

    }
}

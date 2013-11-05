package com.example.rssReader;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Vector;

public class MyActivity extends Activity {

    String[] stringForSpinner = {"stackoverflow.com", "habrahabr.ru", "lenta.ru", "bash.im"};
    Vector<String> stringsForTitles;
    Vector<String> stringsForSummary;
    ArrayAdapter<String> adapterForTitle;
    ListView lv;
    class rssTask extends AsyncTask<String, Void, Void> {

        String string;
        String answer;

        @Override
        protected Void doInBackground(String... params) {
            string = params[0];
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(string);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                answer = EntityUtils.toString(httpEntity);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            XMLParser parser = new XMLParser();
            stringsForTitles.clear();
            stringsForSummary.clear();
            try {
                parser.putAnswer(answer);
                Vector<String> t = parser.getTitles();

                stringsForTitles.addAll(t);
                t = parser.getSummary();

                stringsForSummary.addAll(t);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            lv = (ListView)findViewById(R.id.lv);
            lv.setAdapter(adapterForTitle);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MyActivity.this, ActivityContent.class);
                    intent.putExtra(ActivityContent.url, stringsForSummary.elementAt(position));
                    startActivity(intent);

                }
            });
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ArrayAdapter<String> adapterForSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stringForSpinner);
        adapterForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 //       tv = (TextView)findViewById(R.id.tv);
        stringsForTitles = new Vector<>();
        stringsForSummary = new Vector<>();

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapterForSpinner);
        spinner.setPrompt("RSS");
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String string = "";
                switch (position) {
                    case 0:
                        string = "http://stackoverflow.com/feeds/tag/android";
                        break;
                    case 1:
                        string = "http://habrahabr.ru/rss/hubs/";
                        break;
                    case 2:
                        string = "http://lenta.ru/rss";
                        break;
                    case 3:
                        string = "http://bash.im/rss/";
                        break;
                }
                new rssTask().execute(string);

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        adapterForTitle = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringsForTitles);
    }
}
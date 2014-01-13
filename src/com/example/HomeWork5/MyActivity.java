package com.example.HomeWork5;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    boolean ATOM = false;
    ArrayList<String> titles = new ArrayList<String>();
    ArrayList<String> links = new ArrayList<String>();
    ArrayList<String> descriptions = new ArrayList<String>();
    ArrayList<String> content = new ArrayList<String>();
    SAXParserFactory saxParserFactory;
    SAXParser saxParser;
    RSSHandler rssHandler;
    Information information;
    String ITEM = "item";
    String TITLE = "title";
    String LINK = "link";
    String DESCRIPTION = "description";
    String CONTENT = "description";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);
        String channelName = getIntent().getExtras().getString("channel");

        RSS rss = new RSS(getApplicationContext());
        rss.execute(channelName);
        try{
            if ((information = rss.get()) == null){

                Toast toast = Toast.makeText(getApplicationContext(), "Ошибка", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0 ,0);
                toast.show();
            }else{

                ListView listView = (ListView) findViewById(R.id.listView);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, information.titles);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(MyActivity.this, DescriptionActivity.class);
                    intent.putExtra("title", information.titles.get(i));
                    String toDraw = "";
                    if(information.content.size() > i){
                        toDraw = "<br>" + information.content.get(i); //intent.putExtra("content", information.content.get(i));
                    }
                    if(information.descriptions.size() > i){
                        toDraw = "<br>" + information.descriptions.get(i); //intent.putExtra("description", information.descriptions.get(i));
                    }
                    intent.putExtra("description", toDraw);
                    intent.putExtra("link", information.links.get(i));
                    intent.putExtra("ATOM", ATOM);
                    startActivity(intent);
                    //finish();
                }
            });
            }
        } catch (InterruptedException e){
            Toast toast = Toast.makeText(getApplicationContext(), "Упс! Мы упали;( ", Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(MyActivity.this, ChannelActivity.class);
            startActivity(intent);
            finish();
        }
        catch (ExecutionException e){
            Toast toast = Toast.makeText(getApplicationContext(), "Упс! Мы упали;( ", Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(MyActivity.this, ChannelActivity.class);
            startActivity(intent);
            finish();
        }


    }
    class Information{
        ArrayList<String> titles;
        ArrayList<String> links;
        ArrayList<String> descriptions;
        ArrayList<String> content;
        Information(ArrayList<String> titles, ArrayList<String> links, ArrayList<String> descriptions, ArrayList<String> content){
            this.titles = titles;
            this.content = content;
            this.links = links;
            this.descriptions = descriptions;
        }
    }

    class RSS extends AsyncTask<String, Void, Information>{
        Context context;
        public RSS(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }




        @Override
        protected Information doInBackground(String ...params) {

            try{
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(params[0]);
                HttpResponse httpResponse;
                httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                String data = EntityUtils.toString(httpEntity);
                saxParserFactory = SAXParserFactory.newInstance();
                saxParser = saxParserFactory.newSAXParser();
                rssHandler = new RSSHandler();
                ByteArrayInputStream bais = new ByteArrayInputStream(data.getBytes());
                saxParser.parse(bais, rssHandler);

            }catch (ClientProtocolException e){
                return null;
            } catch (SAXException e) {
                return null;
            } catch (ParserConfigurationException e) {
                return null;
            } catch (IOException e){
                return null;
            }
            return new Information(titles, links, descriptions, content);
        }
    }


    public class RSSHandler extends DefaultHandler {
        String buffer = "";
        /*ArrayList<String> titles = new ArrayList<String>();
        ArrayList<String> links = new ArrayList<String>();
        ArrayList<String> descriptions = new ArrayList<String>();
        ArrayList<String> content = new ArrayList<String>();*/
        boolean item = false, title = false, link = false, description = false;


        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attrs)throws SAXException{

            buffer = "";
            if(localName.equals("feed")){
                ATOM = true;
                ITEM = "entry";
                TITLE = "title";
                LINK = "link";
                DESCRIPTION = "summary";
                CONTENT = "content";
            }
            if(localName.equals(ITEM))
                item = true;
            if(localName.equals(TITLE))
                title = true;
            if(localName.equals(LINK))
                link = true;
            if(localName.equals(DESCRIPTION)|| localName.equals(CONTENT))
                description = true;

        }

        @Override
        public void endElement(String uri, String localName, String qName)throws SAXException{
            if(localName.equals(ITEM)){
                item = false;
            }
            if(localName.equals(TITLE)){
                title = false;
                if(item == true)
                    titles.add(buffer);
            }
            if(localName.equals(LINK)){
                link = false;
                if(item == true)
                    links.add(buffer);
            }

            if(localName.equals(DESCRIPTION)){
                description = false;
                if(item)
                    descriptions.add(buffer);
            }
            if(localName.equals(CONTENT)){
                description = false;
                if(item)
                    content.add(buffer);
            }
            buffer = "";
        }
        @Override
        public void characters(char[] ch, int start, int length)throws SAXException{
            super.characters(ch, start, length);
            //String data = new String(ch, start, length);
            if(item == true){
                buffer += new String(ch, start, length);
            }
        }

        //public Information getInformation();

    }

    public void onBackPressed(){
        Intent intent = new Intent(MyActivity.this, ChannelActivity.class);
        startActivity(intent);
        finish();
    }
}

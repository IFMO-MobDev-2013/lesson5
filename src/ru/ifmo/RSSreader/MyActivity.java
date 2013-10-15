package ru.ifmo.RSSreader;

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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    ArrayList<String> articleNames;
    ArrayAdapter<String> adapter;
    ArrayList<Article> articles;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ListView listView = (ListView) findViewById(R.id.listView1);
        articleNames = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, articleNames);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                Intent intent = new Intent(MyActivity.this, WebActivity.class);
                intent.putExtra("url", articles.get(position).url);
                startActivity(intent);
            }
        });

        new RSSDownloader().execute(getResources().getString(R.string.RSSAdress));

    }

    class RSSDownloader extends AsyncTask<String, Void, ArrayList<Article>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Article> doInBackground(String... s) {
           try {
               return DOMParser.getDocument(s[0]);
           } catch (Exception ex){

           }
           return new ArrayList<Article>();
        }

        @Override
        protected void onPostExecute(ArrayList<Article> articles) {
            for (int i = 0; i < articles.size(); i++){
                articleNames.add(articles.get(i).title);
            }
            adapter.notifyDataSetChanged();
            MyActivity.this.articles = articles;
        }
    }

}

class DOMParser {
    public static ArrayList<Article> getDocument(String urlAdress) throws Exception {
        URL url = new URL(urlAdress);
        URLConnection connection = url.openConnection();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        ArrayList<Article> articles = new ArrayList<Article>();


        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc;
        InputStream in = null;

        try{
            in = connection.getInputStream();
            doc = builder.parse(in);

            Element root = doc.getDocumentElement();
            NodeList items = root.getElementsByTagName(Article.articleTag);
            for (int i = 0; i < items.getLength(); i++){
                Article ar = new Article();
                Node item = items.item(i);
                NodeList properties = item.getChildNodes();
                for (int j = 0; j < properties.getLength(); j++){
                    Node property = properties.item(j);
                    String name = property.getNodeName();

                    if (name.equalsIgnoreCase(Article.titleTag)){
                        ar.title = property.getFirstChild().getNodeValue();
                    } else if (name.equalsIgnoreCase(Article.dateTag)){
                        ar.date = property.getFirstChild().getNodeValue();
                    } else if (name.equalsIgnoreCase(Article.ulrTag)){
                        ar.url = property.getFirstChild().getNodeValue();
                    }

                }
                articles.add(ar);

            }
        } finally {
            try{
                if (in != null){
                    in.close();
                }
            } catch (Throwable e){

            }
        }

        return articles;

    }
}

class Article {
    final static String articleTag = "entry";
    String url;
    final static String ulrTag = "id";
    String title;
    final static String titleTag = "title";
    String date;
    final static String dateTag = "published";

}

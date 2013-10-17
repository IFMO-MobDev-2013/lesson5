package ru.ifmo.rssreader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ShowTitlesActivity extends Activity {

    List<Article> articleList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("url");
        AsyncTask<String, Void, List<Article>> reader = new RSSReader();
        reader.execute(url);
        setContentView(R.layout.titleslayout);
    }

    private void errorMessage(String message) {
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);
    }

    private Context getContext() {
        return this;
    }

    private void showTitles(List<Article> articles) {
        if (articles == null) {
            errorMessage("An error occurred while reading RSS.");
            return;
        }
        articleList = articles;
        if (articles.size() == 0) {
            errorMessage("RSS is empty!");
            return;
        }
        String[] titles = new String[articles.size()];
        for (int i = 0; i < articles.size(); i++) {
            titles[i] = articles.get(i).getTitle();
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                String article = articleList.get(position).getArticle();
                Intent intent = new Intent(getContext(), ShowArticleActivity.class);
                intent.putExtra("article", article);
                startActivity(intent);
            }

        });
        listView.setAdapter(adapter);
    }

    private class RSSReader extends AsyncTask<String, Void, List<Article>> {

        @Override
        protected List<Article> doInBackground(String... url) {
            return XMLParser.parse(URLReader.read(url[0]));
        }

        @Override
        protected void onPostExecute(List<Article> articles) {
            super.onPostExecute(articles);
            showTitles(articles);
        }
    }
}

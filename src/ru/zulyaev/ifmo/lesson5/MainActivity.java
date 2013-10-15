package ru.zulyaev.ifmo.lesson5;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.google.gson.Gson;
import ru.zulyaev.ifmo.lesson5.util.Utils;

import java.util.List;

/**
 * @author Никита
 */
public class MainActivity extends Activity {
    public static final String URL_INDEX = "url";

    private static final Gson GSON = new Gson();
    private static final String FEEDS_PREF = "FEEDS";

    private final AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            showFeed(feeds.get(position));
        }
    };

    private List<String> feeds;
    private SharedPreferences preferences;
    private EditText input;
    private ArrayAdapter<String> adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getPreferences(MODE_PRIVATE);
        feeds = Utils.asArrayList(GSON.<String[]>fromJson(preferences.getString(FEEDS_PREF, "[]"), String[].class));
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, feeds);

        setContentView(R.layout.main);

        ListView listView = (ListView) findViewById(R.id.feed_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);

        input = (EditText) findViewById(R.id.input);
    }

    private void showFeed(String url) {
        Intent intent = new Intent(this, FeedActivity.class);
        intent.putExtra(URL_INDEX, url);
        startActivity(intent);
    }

    public void addFeed(View view) {
        String url = input.getText().toString();
        input.setText("");

        feeds.add(0, url);
        preferences.edit().putString(FEEDS_PREF, GSON.toJson(feeds.toArray())).commit();
        adapter.notifyDataSetChanged();
    }
}
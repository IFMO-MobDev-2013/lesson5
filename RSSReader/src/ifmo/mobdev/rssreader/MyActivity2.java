package ifmo.mobdev.rssreader;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;


public class MyActivity2 extends Activity {
    private ArrayList<HashMap<String, String>> items;
    public static final String TITLE = "title";
    public static final String LINK = "link";
    public static final String DESCR = "description";
    public static final String DATE = "pubDate";
    ImageView pict;

    private void addToListView() {
        ListAdapter adapter = new SimpleAdapter(this, items, R.layout.list_item,
                new String[] {TITLE, LINK, DESCR, DATE},
                new int [] {R.id.title, R.id.link, R.id.descr, R.id.date});
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String title = ((TextView) view.findViewById(R.id.title)).getText().toString();
                String link = ((TextView) view.findViewById(R.id.link)).getText().toString();
                //String descr = ((TextView) view.findViewById(R.id.descr)).getText().toString();
                //String date = ((TextView) view.findViewById(R.id.date)).getText().toString();

                Intent intent = new Intent(MyActivity2.this, MyActivity3.class);
                intent.putExtra("link", link);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.w2);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String url = extras.getString("url");
        pict = (ImageView) findViewById(R.id.imageView);

        new DownloadXMLTask(new RSSLoadCallback()  {
            @Override
            public void onRSSLoaded(String xml) {
                XMLParser parser = new XMLParser(xml);
                items = parser.parse();
                if (items == null) {
                    pict.setImageResource(R.drawable.wrongurl);
                    Toast toast = Toast.makeText(MyActivity2.this, "Wrong URL", 3000);
                    toast.show();
                } else {
                    pict.setVisibility(View.INVISIBLE);
                    addToListView();
                }
            }
            @Override
        public void onRSSLoadFailed(Exception e) {
                pict.setImageResource(R.drawable.wrongurl);
                Toast toast = Toast.makeText(MyActivity2.this, e.getLocalizedMessage(), 3000);
                toast.show();
            }
        }).execute(url);
    }
}

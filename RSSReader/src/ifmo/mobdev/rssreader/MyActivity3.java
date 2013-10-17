package ifmo.mobdev.rssreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;

public class MyActivity3 extends Activity {
    TextView title2, link2, descr2, date2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.w3);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String link = extras.getString("link");

        WebView wv = (WebView) findViewById(R.id.webView);
        wv.loadUrl(link);
    }
}

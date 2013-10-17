package ru.ifmo.smelik.rssReader;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.ifmo.smelik.rss.R;

public class enteranceActivity extends Activity {

    String uri = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enterance_layout);

        final EditText url = (EditText) findViewById(R.id.editText);

        final Button goButton = (Button) findViewById(R.id.button);

        final Intent transferUrl = new Intent(this, RssActivity.class);

                goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uri = url.getText().toString();
                if (uri.substring(0, 7).equals("http://")) {
                    transferUrl.putExtra("URL", uri);
                } else {
                    transferUrl.putExtra("URL", "http://" + uri);
                }
                startActivity(transferUrl);
            }
        });
    }
}

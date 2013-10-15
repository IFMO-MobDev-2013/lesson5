package ru.ifmo.mobdev.shalamov;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 15.10.13
 * Time: 19:30
 * To change this template use File | Settings | File Templates.
 */
public class ShowActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_item);
        Intent intent = getIntent();

        //FeedItem fi =

        TextView title = (TextView)findViewById(R.id.title);
        TextView date = (TextView)findViewById(R.id.date);
        TextView link = (TextView)findViewById(R.id.link);
        TextView description = (TextView)findViewById(R.id.description);
        CheckBox rank = (CheckBox)findViewById(R.id.checkBox);

        description.setMovementMethod(new ScrollingMovementMethod());

        title.setText(intent.getStringExtra("title"));
        date.setText(intent.getStringExtra("date"));
        link.setText(intent.getStringExtra("link"));
        description.setText(intent.getStringExtra("description"));

//        if(intent.getStringExtra("rank").equals("like"))

    }
}

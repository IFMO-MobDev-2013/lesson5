package ru.ifmo.mobdev.shalamov.RssAgregator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    EditText editText = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ask);

        editText = (EditText) findViewById(R.id.editText);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyActivity.class);
                intent.putExtra("url", editText.getText().toString());
                startActivity(intent);
            }
        });
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.radioButton0:
                editText.setText("http://bash.im/rss/");
//                ((RadioButton)v).setO

            case R.id.radioButton1:
                editText.setText("http://lenta.ru/rss");
                break;
            case R.id.radioButton2:
                editText.setText("http://habrahabr.ru/rss/hubs/");
                break;
            case R.id.radioButton3:
                editText.setText("http://stackoverflow.com/feeds/tag/android");
                break;
        }
    }

}

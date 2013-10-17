package ifmo.mobdev.rssreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

import java.net.MalformedURLException;
import java.net.URL;

public class MyActivity1 extends Activity{

    ImageButton show;
    ImageView title;
    EditText edtxt;
    String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.w1);

        show = (ImageButton) findViewById(R.id.show);
        edtxt = (EditText) findViewById(R.id.editText);
        title = (ImageView) findViewById(R.id.imgViewTitle);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.title_anim);
        title.startAnimation(animation);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = edtxt.getText().toString();
                try {
                    URL Url = new URL(url);

                    Intent intent = new Intent(MyActivity1.this, MyActivity2.class);
                    intent.putExtra("url", url);

                    startActivity(intent);

                } catch (MalformedURLException e) {
                    Toast toast = Toast.makeText(MyActivity1.this, "Wrong URL!", 3000);
                    toast.show();
                }
            }
        });
    }
}

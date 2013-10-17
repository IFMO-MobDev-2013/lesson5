package ru.ifmo.rain.loboda.rss;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class Details extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        BitmapDrawable drawable = new BitmapDrawable(getResources(), Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888));
        Getter getter = new Getter(drawable);
        Bundle bundle = getIntent().getExtras();

        TextView author = (TextView) findViewById(R.id.author);
        TextView date = (TextView) findViewById(R.id.date);
        TextView content = (TextView) findViewById(R.id.content);
        TextView fullContent = (TextView) findViewById(R.id.fullContent);
        fullContent.setMovementMethod(LinkMovementMethod.getInstance());
        if (bundle.getString("Author") == null) {
            author.setVisibility(View.GONE);
        } else {
            author.setText(Html.fromHtml(bundle.getString("Author")));
        }
        if (bundle.getString("Date") == null) {
            date.setVisibility(View.GONE);
        } else {
            date.setText(Html.fromHtml(bundle.getString("Date")));
        }
        if (bundle.getString("Annotation") == null) {
            content.setVisibility(View.GONE);
        } else {
            content.setText(Html.fromHtml(bundle.getString("Annotation")));
        }
        if (bundle.getString("Description") == null) {
            fullContent.setVisibility(View.GONE);
        } else {
            fullContent.setText(Html.fromHtml(bundle.getString("Description"), getter, null));
        }
    }

    private class Getter implements Html.ImageGetter {
        Drawable drawable;

        Getter(Drawable drawable) {
            this.drawable = drawable;
        }

        @Override
        public Drawable getDrawable(String s) {
            return drawable;
        }
    }
}


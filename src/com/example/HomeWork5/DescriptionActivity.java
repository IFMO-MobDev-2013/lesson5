package com.example.HomeWork5;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: Дмитрий
 * Date: 10.01.14
 * Time: 7:10
 * To change this template use File | Settings | File Templates.
 */
public class DescriptionActivity extends Activity {
    String link;
    String description;
    String title;
    String content = "\n";
    boolean ATOM;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);
        ATOM = getIntent().getExtras().getBoolean("ATOM");
        description = getIntent().getExtras().getString("description").trim().replaceAll("\n", "<br>");
        title = getIntent().getExtras().getString("title");
        link = getIntent().getExtras().getString("link");
        //if(ATOM){
        //    content = getIntent().getExtras().getString("content");
        //}
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadData("<h3><b>" + title + "</b></h3>" + "<br>" + description, "text/html; charset=utf-8", null);
    }




    /*public void onBackPressed(){
        Intent intent3 = new Intent(DescriptionActivity.this, MyActivity.class);
        startActivity(intent3);
        finish();
    }*/
}

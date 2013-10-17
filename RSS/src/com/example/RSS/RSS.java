package com.example.RSS;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class RSS extends Activity implements View.OnClickListener{
    private String url = "";
    Button b;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        b = (Button) findViewById(R.id.button);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(this, SecondPage.class);
        EditText edit = (EditText) findViewById(R.id.editText);
        url = edit.getEditableText().toString();
        intent.putExtra(SecondPage.URLL, url);
        startActivity(intent);
    }

}

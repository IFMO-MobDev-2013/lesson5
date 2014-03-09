package com.mobdev.rss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class RSS extends Activity implements View.OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		((Button) findViewById(R.id.button)).setOnClickListener(this);
		((TextView) findViewById(R.id.textView2))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						((EditText) findViewById(R.id.editText))
								.setText(R.string.url);
					}
				});
	}

	@Override
	public void onClick(View v) {
		startActivity(new Intent(this, SecondPage.class).putExtra(
				SecondPage.URLL, ((EditText) findViewById(R.id.editText))
						.getEditableText().toString()));
	}

}

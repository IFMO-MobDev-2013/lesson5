package com.mobdev.rss;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class SecondPage extends ListActivity {
	public static final String URLL = "URLL";
	public ArrayList<HashMap<String, String>> mIt = new ArrayList<HashMap<String, String>>();
	public static Document doc = null;
	public ProgressDialog dialog;

	ListAdapter adapter = null;
	ListView lv;

	static final String KEY_TITLE = "title";
	static final String KEY_LINK = "link";
	static final String KEY_DESCRIPTION = "description";
	static final String KEY_PUBDATE = "pubDate";

	public class Parser extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... s) {
			try {
				URL url = new URL(s[0]);
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				BufferedInputStream is = new BufferedInputStream(
						url.openStream());
				doc = db.parse(is);
				doc.getDocumentElement().normalize();
				NodeList nl = doc.getElementsByTagName("item");

				for (int i = 0; i < nl.getLength(); i++) {

					HashMap<String, String> map = new HashMap<String, String>();
					Element e = (Element) nl.item(i);

					map.put(KEY_TITLE, getValue(e, "title"));
					map.put(KEY_LINK, getValue(e, "link"));
					map.put(KEY_DESCRIPTION, getValue(e, "description"));
					map.put(KEY_PUBDATE, getValue(e, "pubDate"));
					mIt.add(map);
				}

			} catch (Exception e) {
				(Toast.makeText(getApplicationContext(),
						getString(R.string.wrong), Toast.LENGTH_LONG)).show();
			}
			return "";
		}

		@Override
		protected void onPostExecute(String s) {
			lv.setAdapter(adapter);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		String url = extras.getString(URLL);
		adapter = new SimpleAdapter(this, mIt, R.layout.rssitem, new String[] {
				KEY_TITLE, KEY_PUBDATE, KEY_DESCRIPTION, KEY_LINK }, new int[] {
				R.id.firstLine, R.id.date, R.id.secondLine, R.id.link });
		lv = getListView();
		setListAdapter(adapter);
		(new Parser()).execute(url);

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				String link = ((TextView) view.findViewById(R.id.link))
						.getText().toString();
				Intent in = new Intent(getApplicationContext(), ShowWeb.class);
				in.putExtra(URLL, link);
				startActivity(in);
			}
		});
	}

	public final String getElementValue(Node elem) {
		Node child;
		if (elem != null) {
			if (elem.hasChildNodes()) {
				for (child = elem.getFirstChild(); child != null; child = child
						.getNextSibling()) {
					if (child.getNodeType() == Node.TEXT_NODE) {
						return child.getNodeValue();
					}
				}
			}
		}
		return "";
	}

	public String getValue(Element item, String str) {
		NodeList n = item.getElementsByTagName(str);
		return this.getElementValue(n.item(0));
	}

}

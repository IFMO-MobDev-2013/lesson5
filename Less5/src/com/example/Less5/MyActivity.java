package com.example.Less5;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MyActivity extends Activity {

	public static final String link = "http://news.rambler.ru/rss/head/";
	public static ArrayList<Node> nodes = new ArrayList<Node>();

	class RSSAsyncTask extends AsyncTask<URL, Void, ArrayList<Node>> {

		private String addNewData(Element e, String n) {
			return e.getElementsByTagName(n).item(0)
					.getFirstChild()
					.getNodeValue();
		}


		@Override
		protected ArrayList<Node> doInBackground(URL... urls) {
			ArrayList<Node> nodeArrayList = new ArrayList<Node>();
			InputStream IN;
			NodeList nodeList;
			try {
				IN = urls[0]
						.openConnection()
						.getInputStream();
				Element element = DocumentBuilderFactory
						.newInstance()
						.newDocumentBuilder()
						.parse(IN)
						.getDocumentElement();
				nodeList = element.getElementsByTagName("item");
				for (int i = 0; i < nodeList.getLength(); i++) {
					Element main = (Element) nodeList.item(i);
					nodeArrayList.add(
							new Node(
									addNewData(main, "title"),
									addNewData(main, "description"),
									new Date(addNewData(main, "pubDate")),
									addNewData(main, "link")
							)
					);

				}
				nodes = nodeArrayList;
				return nodeArrayList;

			} catch (Exception e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(ArrayList<Node> nodes) {
			super.onPostExecute(nodes);


			ArrayList<HashMap<String, String>> items = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> map;


			for (Node node : nodes) {
				map = new HashMap<String, String>();
				map.put("title", node.getTitle());
				map.put("date", node.getDate().toString());
				map.put("description", node.getDescription());
				items.add(map);
			}

			SimpleAdapter adapter = new SimpleAdapter(MyActivity.this, items, R.layout.my_simple_item,
					new String[]{"title", "date", "description"},
					new int[]{R.id.headerTextView, R.id.timeTextView, R.id.descriptionTextView});

			((ListView) findViewById(R.id.listView)).setAdapter(adapter);
		}
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		RSSAsyncTask rssAsyncTask = new RSSAsyncTask();
		try {
			rssAsyncTask.execute(new URL(link));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		((ListView) findViewById(R.id.listView)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> av, View view, int index, long arg3) {
				Intent intent = new Intent(MyActivity.this, WebActivity.class);
				intent.putExtra("link", nodes.get(index).getLink());
				startActivity(intent);
			}
		});
	}
}

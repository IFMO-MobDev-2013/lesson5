package com.example.lesson5;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Vector;

public class Program extends Activity
{
    public MyAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Vector<Entry> e = new Vector<Entry>();
        adapter = new MyAdapter(this, e, this);
        ListView list_view = (ListView) findViewById(R.id.listView);
        list_view.setAdapter(adapter);
    }

    public void onRssLoaded(XmlDocument target)
    {
        Console.print("Rss loaded");
        Vector<Entry> e = new Vector<Entry>();
        if (target.findByName("feed").size() > 0)
        {
            Vector<XmlNode> nodes = target.findByName("entry");
            for (int i = 0; i < nodes.size(); ++i)
            {
                Entry entry = parseEntry(nodes.get(i));
                Console.print("Entry ("+entry.link+"): "+entry.title);
                adapter.entries.add(entry);
            }
        }
        else if (target.findByName("channel").size() > 0)
        {
            Console.print("Channel");
            Vector<XmlNode> nodes = target.findByName("item");
            for (int i = 0; i < nodes.size(); ++i)
            {
                Entry entry = parseItem(nodes.get(i));
                Console.print("Item ("+entry.link+"): "+entry.title);
                adapter.entries.add(entry);
            }
        }
        ((ProgressBar)findViewById(R.id.progressBar)).setVisibility(ProgressBar.INVISIBLE);
    }

    public void onClick(View v)
    {
        adapter.entries.clear();
        TextView tv = (TextView)findViewById(R.id.linkText);
        String link = String.valueOf(tv.getText());
        ((ProgressBar)findViewById(R.id.progressBar)).setVisibility(ProgressBar.VISIBLE);
        new XmlLoader(link, this).start();
    }

    public Entry parseItem(XmlNode item)
    {
        Entry temp = new Entry();
        for (int i = 0; i < item.children.size(); ++i)
        {
            XmlNode c = item.children.get(i);
            if (c.getName().equals("title"))
            {
                temp.title = c.getValue();
            }
            else if (c.getName().equals("link"))
            {
                temp.link = c.getValue();
            }
        }
        return temp;
    }

    public Entry parseEntry(XmlNode entry)
    {
        Entry temp = new Entry();
        for (int i = 0; i < entry.children.size(); ++i)
        {
            XmlNode c = entry.children.get(i);
            if (c.getName().equals("title"))
            {
                temp.title = c.getValue();
            }
            else if (c.getName().equals("link"))
            {
                temp.link = c.getAttribute("href");
            }
        }
        return temp;
    }
}

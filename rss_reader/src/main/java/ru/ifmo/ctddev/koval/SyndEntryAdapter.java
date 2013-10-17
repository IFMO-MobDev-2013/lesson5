package ru.ifmo.ctddev.koval;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.sun.syndication.feed.synd.SyndEntry;

public class SyndEntryAdapter extends ArrayAdapter<SyndEntry> {

    public SyndEntryAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item, null);
        }

        SyndEntry entry = getItem(position);

        if (entry != null) {
            ((TextView) v.findViewById(R.id.title)).setText(Html.fromHtml(entry.getTitle()));
            ((TextView) v.findViewById(R.id.description)).setText(Html.fromHtml(entry.getDescription().getValue()));
        }

        return v;
    }
}

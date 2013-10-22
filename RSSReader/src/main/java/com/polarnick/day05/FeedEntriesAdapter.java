package com.polarnick.day05;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.polarnick.rss.FeedEntry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Date: 15.10.13
 *
 * @author Nickolay Polyarniy aka PolarNick
 */
public abstract class FeedEntriesAdapter extends ArrayAdapter<FeedEntry> {
    private List<FeedEntry> entries;

    private final DateFormat dateFormatter = new SimpleDateFormat("HH:mm");

    public FeedEntriesAdapter(Context context, List<FeedEntry> entries) {
        super(context, R.layout.entry_item, entries);
        this.entries = entries;
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View entryView = inflater.inflate(R.layout.entry_item, parent, false);

        TextView entryTitleText = (TextView) entryView.findViewById(R.id.entryTitle);
        TextView entryUpdatedDateText = (TextView) entryView.findViewById(R.id.entryDateUpdated);
        TextView entryRankText = (TextView) entryView.findViewById(R.id.entryRank);

        final FeedEntry entry = entries.get(index);

        entryTitleText.setText(entry.getTitle());
        entryUpdatedDateText.setText(dateFormatter.format(entry.getUpdatedDate()));
        if (entry.getRank() < 0) {
            entryRankText.setText(String.valueOf(entry.getRank()));
            entryRankText.setTextColor(Color.RED);
        } else {
            entryRankText.setText("+" + entry.getRank());
            entryRankText.setTextColor(Color.GREEN);
        }

        entryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedEntriesAdapter.this.onClick(entry);
            }
        });

        return entryView;
    }

    protected abstract void onClick(FeedEntry entry);

}

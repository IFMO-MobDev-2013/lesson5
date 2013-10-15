package ru.zulyaev.ifmo.lesson5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import ru.zulyaev.ifmo.lesson5.feed.Entry;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author Никита
 */
class FeedItem extends ArrayListAdapter.Item {
    private final Entry entry;
    private final LayoutInflater inflater;
    private ViewHolder holder;

    FeedItem(Entry entry, LayoutInflater inflater) {
        this.entry = entry;
        this.inflater = inflater;
    }

    @Override
    protected View composeView(View view, ViewGroup parent) {
        if (view == null) {
            view = inflater.inflate(R.layout.feed_item, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.description = (WebView) view.findViewById(R.id.description);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.title.setText(entry.getTitle());
        try {
            holder.description.loadData(URLEncoder.encode(entry.getDescription(), "utf-8").replaceAll("\\+", "%20"), "text/html", null);
        } catch (UnsupportedEncodingException e) {

        }
        return view;
    }

    public void toggle() {
        View view = holder.description;
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    static class ViewHolder {
        TextView title;
        WebView description;
    }
}

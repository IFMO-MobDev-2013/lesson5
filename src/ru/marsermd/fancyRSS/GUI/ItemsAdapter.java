package ru.marsermd.fancyRSS.GUI;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import org.horrabin.horrorss.RssItemBean;
import ru.marsermd.fancyRSS.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: marsermd
 * Date: 17.10.13
 * Time: 22:15
 * To change this template use File | Settings | File Templates.
 */
public class ItemsAdapter extends BaseAdapter {

    List<ItemModel> itemModels;
    Activity activity;
    public ItemsAdapter(Activity activity, int resource) {
        super();
        itemModels = new ArrayList<ItemModel>();
        this.activity = activity;
        ItemModel.mainActivity = activity;
    }

    public void init(List<RssItemBean> itemBeans) {
        itemModels.clear();
        for (RssItemBean item:itemBeans) {
            itemModels.add(new ItemModel(item.getTitle(), item.getLink(), item.getDescription()));
        }
        notifyDataSetChanged();

    }

    private class ViewHolder {
        TextView title;
        WebView description;
        ItemModel model;

        public ViewHolder(TextView title, WebView description) {
            this.title = title;
            this.description = description;

            description.getSettings().setJavaScriptEnabled(true);
            description.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            description.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            description.setBackgroundColor(activity.getResources().getColor(R.color.item_description));
        }
    }

    @Override
    public int getCount() {
        return itemModels.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e("shit!", "getting view");
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.row_layout, null);
            TextView title = (TextView) convertView.findViewById(R.id.item_title);
            WebView description = (WebView) convertView.findViewById(R.id.item_description);
            holder = new ViewHolder(title, description);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (holder.model != null) {
            holder.model.cancel();
        }
        holder.model = itemModels.get(position);
        holder.model.setViews(holder.title, holder.description);

        return convertView;
    }
}

package ru.mermakov.projects.MRSSReader;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

public class RSSFeed implements Serializable {
    private static final long serialVersionUID = 1L;
    private int itemcount = 0;
    private List<RSSItem> itemlist;

    RSSFeed() {
        itemlist = new Vector<RSSItem>(0);
    }

    void addItem(RSSItem item) {
        itemlist.add(item);
        itemcount++;
    }

    public RSSItem getItem(int location) {
        return itemlist.get(location);
    }

    public int getItemCount() {
        return itemcount;
    }
}

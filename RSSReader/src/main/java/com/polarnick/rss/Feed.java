package com.polarnick.rss;

import java.util.*;

/**
 * Date: 13.10.13
 *
 * @author Nickolay Polyarniy aka PolarNick
 */
public class Feed {

    private final List<FeedEntry> entries = new ArrayList<FeedEntry>();
    private Date updatedDate;

    public void addEntry(FeedEntry entry) {
        entries.add(entry);
    }

    public List<FeedEntry> getEntries() {
        return entries;
    }

    public void sortEntriesByRank() {
        Collections.sort(entries, new Comparator<FeedEntry>() {
            @Override
            public int compare(FeedEntry entry1, FeedEntry entry2) {
                return entry2.getRank() - entry1.getRank();
            }
        });
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}

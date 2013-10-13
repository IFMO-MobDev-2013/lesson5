package com.polarnick.rss;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 13.10.13
 *
 * @author Nickolay Polyarniy aka PolarNick
 */
public class Feed {

    private final List<FeedEntry> entries = new ArrayList<FeedEntry>();

    public void addEntry(FeedEntry entry) {
        entries.add(entry);
    }

    public List<FeedEntry> getEntries() {
        return entries;
    }
}

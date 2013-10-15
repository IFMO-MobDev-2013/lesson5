package com.polarnick.rss;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Date: 13.10.13
 *
 * @author Nickolay Polyarniy aka PolarNick
 */
public class StackOverflowRSSReaderTest {
    @Test
    public void testReadFeed() throws Exception {
        StackOverflowRSSReader reader = new StackOverflowRSSReader();
        Feed feed = reader.readFeed();

        Assert.assertNotNull(feed);
        Assert.assertNotNull(feed.getUpdatedDate());

        List<FeedEntry> entries = feed.getEntries();
        Assert.assertNotNull(entries);
        Assert.assertTrue(entries.size() >= 1);

        for (FeedEntry entry : entries) {
            Assert.assertNotNull(entry);
            Assert.assertNotNull(entry.getLink());
            Assert.assertNotNull(entry.getPublishedDate());
            Assert.assertNotNull(entry.getRank());
            Assert.assertNotNull(entry.getTitle());
            Assert.assertNotNull(entry.getUpdatedDate());
        }
    }
}

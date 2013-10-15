package ru.zulyaev.ifmo.lesson5.feed.rss2;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @author Никита
 */
@Root(name = "image", strict = false)
public class Rss2Image {
    @Element
    private String url;

    public String getUrl() {
        return url;
    }
}

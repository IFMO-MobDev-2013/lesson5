package ru.zulyaev.ifmo.lesson5.feed.atom;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.zulyaev.ifmo.lesson5.feed.Entry;

/**
 * @author Никита
 */
@Root(name = "entry", strict = false)
public class AtomEntry implements Entry {
    @Element(required = false)
    private String title;
    @Element(required = false)
    private String summary;
    @Element(required = false)
    private String link;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return summary;
    }

    @Override
    public String getLink() {
        return link;
    }
}

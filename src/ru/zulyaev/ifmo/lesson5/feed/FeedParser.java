package ru.zulyaev.ifmo.lesson5.feed;

import android.util.Log;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.zulyaev.ifmo.lesson5.feed.atom.AtomFeed;
import ru.zulyaev.ifmo.lesson5.feed.rss1.Rss1Feed;
import ru.zulyaev.ifmo.lesson5.feed.rss2.Rss2Feed;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author Никита
 */
public class FeedParser {
    @SuppressWarnings("unchecked")
    public static final List<Class<? extends Feed>> DEFAULT_FORMATS = Arrays.asList(
            AtomFeed.class,
            Rss1Feed.class,
            Rss2Feed.class
    );

    private List<Class<? extends Feed>> formats;

    public FeedParser(List<Class<? extends Feed>> formats) {
        this.formats = formats;
    }

    public FeedParser() {
        this(DEFAULT_FORMATS);
    }

    public Feed parse(InputStream is) throws IOException {
        Serializer serializer = new Persister();
        ByteArrayOutputStream dataOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        for (int read; (read = is.read(buffer)) > 0;) {
            dataOutputStream.write(buffer, 0, read);
        }
        ByteArrayInputStream dataInputStream = new ByteArrayInputStream(dataOutputStream.toByteArray());
        for (Class<? extends Feed> format : formats) {
            dataInputStream.reset();
            try {
                return serializer.read(format, dataInputStream);
            } catch (Exception e) {
                Log.d(FeedParser.class.toString(), "Not " + format.getName(), e);
            }
        }
        throw new ParseException("None of standards applies to this stream");
    }
}

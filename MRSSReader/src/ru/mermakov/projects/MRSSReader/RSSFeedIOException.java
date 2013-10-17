package ru.mermakov.projects.MRSSReader;

/**
 * Created with IntelliJ IDEA.
 * User: Mikhail
 * Date: 17.10.13
 * Time: 19:58
 * To change this template use File | Settings | File Templates.
 */
public class RSSFeedIOException extends RSSFeedException {
    private static final long serialVersionUID = 1L;

    public RSSFeedIOException() {
        super();
    }

    public RSSFeedIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public RSSFeedIOException(String message) {
        super(message);
    }

    public RSSFeedIOException(Throwable cause) {
        super(cause);
    }
}

package ru.mermakov.projects.MRSSReader;

/**
 * Created with IntelliJ IDEA.
 * User: Mikhail
 * Date: 17.10.13
 * Time: 19:58
 * To change this template use File | Settings | File Templates.
 */
public class RSSFeedXMLParseException extends RSSFeedException {
    private static final long serialVersionUID = 1L;

    public RSSFeedXMLParseException() {
        super();
    }

    public RSSFeedXMLParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public RSSFeedXMLParseException(String message) {
        super(message);
    }

    public RSSFeedXMLParseException(Throwable cause) {
        super(cause);
    }
}

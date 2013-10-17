package ru.mermakov.projects.MRSSReader;

/**
 * Created with IntelliJ IDEA.
 * User: Mikhail
 * Date: 17.10.13
 * Time: 19:58
 * To change this template use File | Settings | File Templates.
 */
public class RSSFeedException extends Exception{
    public RSSFeedException() {
        super();
    }

    public RSSFeedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RSSFeedException(String message) {
        super(message);
    }

    public RSSFeedException(Throwable cause) {
        super(cause);
    }
}

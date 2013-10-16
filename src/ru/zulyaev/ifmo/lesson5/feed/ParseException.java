package ru.zulyaev.ifmo.lesson5.feed;

import java.io.IOException;

/**
 * @author Никита
 */
public class ParseException extends IOException {
    public ParseException() {
    }

    public ParseException(String detailMessage) {
        super(detailMessage);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }
}

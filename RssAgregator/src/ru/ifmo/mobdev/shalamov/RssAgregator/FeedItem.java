package ru.ifmo.mobdev.shalamov.RssAgregator;

/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 14.10.13
 * Time: 0:04
 * To change this template use File | Settings | File Templates.
 */
public class FeedItem {
    String link;
    String title;
    String date;
    String description;
    int rank;

    FeedItem(String _link, String _title, String _date, String _description, int _rank) {
        link = _link;
        title = _title;
        date = _date;
        description = _description;
        rank = _rank;
    }
}

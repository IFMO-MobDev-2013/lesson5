package com.example.PashaAC.RSSReader;

import java.text.SimpleDateFormat;

public class Articles {
    private String title = "";
    private String description = "";
    private String link = "";
     String pubdate = "";

    private String GoodTextWithoutHTMLTags(String someString) {
        int ind;

        while ((ind = someString.indexOf("<p>")) != -1) {
            String tmp1 = someString.substring(0, ind);
            String tmp2 = someString.substring(ind + 3, someString.length());
            someString = tmp1 + "" + tmp2;
        }
        while ((ind = someString.indexOf("</p>")) != -1) {
            String tmp1 = someString.substring(0, ind);
            String tmp2 = someString.substring(ind + 4, someString.length());
            someString = tmp1 + "" + tmp2;
        }
        while ((ind = someString.indexOf("<br>")) != -1) {
            String tmp1 = someString.substring(0, ind);
            String tmp2 = someString.substring(ind + 4, someString.length());
            someString = tmp1 + "\n" + tmp2;
        }
        while ((ind = someString.indexOf("<br/>")) != -1) {
            String tmp1 = someString.substring(0, ind);
            String tmp2 = someString.substring(ind + 5, someString.length());
            someString = tmp1 + "\n" + tmp2;
        }
        while ((ind = someString.indexOf("&amp;")) != -1) {
            String tmp1 = someString.substring(0, ind);
            String tmp2 = someString.substring(ind + 5, someString.length());
            someString = tmp1 + "&" + tmp2;
        }
        while ((ind = someString.indexOf("&quot;")) != -1) {
            String tmp1 = someString.substring(0, ind);
            String tmp2 = someString.substring(ind + 6, someString.length());
            someString = tmp1 + "\"" + tmp2;
        }
        while ((ind = someString.indexOf("&gt;")) != -1) {
            String tmp1 = someString.substring(0, ind);
            String tmp2 = someString.substring(ind + 4, someString.length());
            someString = tmp1 + ">" + tmp2;
        }
        while ((ind = someString.indexOf("&lt;")) != -1) {
            String tmp1 = someString.substring(0, ind);
            String tmp2 = someString.substring(ind + 4, someString.length());
            someString = tmp1 + "<" + tmp2;
        }
        while ((ind = someString.indexOf("&apos;")) != -1) {
            String tmp1 = someString.substring(0, ind);
            String tmp2 = someString.substring(ind + 6, someString.length());
            someString = tmp1 + "'" + tmp2;
        }
        return someString;
    }


    public Articles(String title, String description, String link, String pubdate) {
        this.title = GoodTextWithoutHTMLTags(title);
        this.description = GoodTextWithoutHTMLTags(description);
        this.link = link;
        this.pubdate = pubdate;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getPubDate() {
        return pubdate.substring(0, pubdate.indexOf(" +"));
    }
    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return title + "...\n" + pubdate.substring(0, pubdate.indexOf("+"));
    }
}

package com.example.rss;

import android.os.AsyncTask;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class RSSItem {

    private String title;
    private String description;
    private String pubDate;
    private String link;
    private boolean open;

    public RSSItem(String title, String description, String pubDate, String link) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.link = link;
        this.open = false;
    }

    public void setOpen(){
        this.open = true;
    }

    public boolean getOpen(){
        return this.open;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getLink()
    {
        return this.link;
    }

    public String getDescription()
    {
        return this.description;
    }

    public String getPubDate()
    {
        return this.pubDate;
    }

    @Override
    public String toString() {
        return getTitle() + "  ( " + getPubDate() + " )";
    }

    public static void getRssItems(String feedUrl) {
        new MyAsyncTask().execute(feedUrl);
    }

    private static class MyAsyncTask extends AsyncTask<String, Integer, ArrayList<RSSItem>>{
        private String title;
        private String description;
        private String pubDate;
        private String link;

        private String itemTag = "item";
        private String titleTag = "title";
        private String descriptionTag = "description";
        private String pubDateTag = "pubDate";
        private String linkTag = "link";
        private boolean stupidRSS = false;

        private String[] splitTag(String s,String tag){
            String ans[];
            if (stupidRSS){
                ans = s.split("<"+tag);
                if (tag.charAt(0) != '/')
                    for (int i=0;i<ans.length;i++){
                        for (int j=0;j<ans[i].length();j++){
                            if (ans[i].charAt(j) == '>'){
                                ans[i] = ans[i].substring(j+1,ans[i].length());
                                break;
                            }
                        }
                    }
            } else {
                if (tag.charAt(0) != '/')
                    tag += ">";
                ans = s.split("<"+tag);
            }
            return ans;
        }

        @Override
        protected ArrayList<RSSItem> doInBackground(String... feedUrl) {
            ArrayList<RSSItem> rssItems = new ArrayList<RSSItem>();
            RSSItem rssItem;
            try {
                URL url = new URL(feedUrl[0]);

                String var6 = "";

                URLConnection connection;
                connection = url.openConnection();
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                String encode = "utf-8";
                Scanner scanner = new Scanner(connection.getInputStream(),encode);
                var6 = scanner.nextLine();
                if (var6.split("encoding=").length>1){
                    encode = "";
                    for (int i=1;var6.split("encoding=")[1].charAt(i) != '"';i++)
                        encode += var6.split("encoding=")[1].charAt(i);
                }
                connection = url.openConnection();
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                scanner = new Scanner(connection.getInputStream(),encode);
                while (scanner.hasNext()) {
                    var6 += scanner.nextLine();
                }
                String[] items = splitTag(var6,itemTag);
                if (items.length == 1)
                {
                    itemTag = "entry";
                    titleTag = "title";
                    descriptionTag = "summary";
                    pubDateTag = "published";
                    linkTag = "id";
                    stupidRSS = true;
                    items = splitTag(var6,itemTag);
                }
                MyActivity.progressBar.setMax(items.length - 1);
                for (int i = 1;i<items.length;i++)
                    items[i] = splitTag(items[i],"/"+itemTag+">")[0];

                for (int i = 1;i<items.length;i++){
                    publishProgress(i);
                    if (!stupidRSS)
                        items[i]=deleteCDATA(items[i]);

                    title = splitTag(splitTag(items[i],titleTag)[1],"/"+titleTag+">")[0];
                    description = splitTag(splitTag(items[i],descriptionTag)[1],"/"+descriptionTag+">")[0];
                    pubDate = splitTag(splitTag(items[i],pubDateTag)[1],"/"+pubDateTag+">")[0];
                    link = splitTag(splitTag(items[i], linkTag)[1], "/" + linkTag + ">")[0];

                    if (!stupidRSS){
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd - hh:mm:ss");
                        pubDate = sdf.format(new Date(pubDate));
                    } else {
                        String s = "";
                        for (int j=0;j<pubDate.length();j++)
                            if (pubDate.charAt(j) == 'T')
                                s+=", ";
                            else if (pubDate.charAt(j) == '-')
                                s+="/";
                            else if (pubDate.charAt(j) != 'Z')
                                s+=pubDate.charAt(j);
                        pubDate = s;
                    }

                    rssItem = new RSSItem(title, description, pubDate, link);
                    rssItems.add(rssItem);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return rssItems;
        }

        private String deleteCDATA(String item){
            String s = "<![CDATA[";
            String s1 = "";
            for (int j = 0;j < item.length() - s.length();j++)
                if (!(s.equals(item.substring(j, j + s.length()))))
                    s1 += item.charAt(j);
                else
                    j += s.length()-1;
            item = s1;
            s = "]]>";
            s1 = "";
            for (int j = 0;j < item.length() - s.length();j++)
                if (!(s.equals(item.substring(j,j+s.length()))))
                    s1 += item.charAt(j);
                else
                    j += s.length()-1;
            return s1;
        }

        protected void onProgressUpdate(Integer... progress) {
            MyActivity.progressBar.setProgress(progress[0]);
        }

        protected void onPostExecute(ArrayList<RSSItem> result) {
            MyActivity.rssItems.addAll(result);
            //MyActivity.aa.notifyDataSetChanged();
            MyActivity.myArrayAdapter.notifyDataSetChanged();
        }
    }
}
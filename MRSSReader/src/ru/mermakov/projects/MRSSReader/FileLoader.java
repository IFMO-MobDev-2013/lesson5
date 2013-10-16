package ru.mermakov.projects.MRSSReader;

import android.content.Context;
import android.util.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: Mikhail
 * Date: 16.10.13
 * Time: 23:21
 * To change this template use File | Settings | File Templates.
 */
public class FileLoader {
    private File cacheDir, cacheDirFeeds, cacheDirImage;
    ExecutorService executorService;
    FileCache fileCache;

    public FileLoader(Context context) {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "MRSSReader");
            cacheDirFeeds = new File(cacheDir, "Feeds");
            cacheDirImage = new File(cacheDir, "Images");
        } else {
            cacheDir = context.getCacheDir();
            cacheDirFeeds = new File(cacheDir, "Feeds");
            cacheDirImage = new File(cacheDir, "Images");
        }
        fileCache = new FileCache(context);
        executorService = Executors.newFixedThreadPool(5);
    }

    public void downloadFromUrl(URL url) throws IOException {
        InputStream is = null;
        FileOutputStream fos = null;
        String filename=String.valueOf(url.hashCode())+".rss";
        try {
            URLConnection urlConn = url.openConnection();
            is = urlConn.getInputStream();
            fos = new FileOutputStream(filename);
            byte[] buffer = new byte[4096];
            int len;
            while ((len = is.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } finally {
                if (fos != null) {
                    fos.close();
                }
            }
        }
    }

    public void saveRss(final String linkToDownload){
        Thread download = new Thread() {
            public void run() {
                try {
                URL url=new URL(linkToDownload);
                downloadFromUrl(url);}
                catch(Exception e){
                    Log.i("Error",e.getMessage());
                }
            }
        };
        download.start();
    }
}

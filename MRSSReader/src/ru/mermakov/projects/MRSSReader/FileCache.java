package ru.mermakov.projects.MRSSReader;

import android.content.Context;

import java.io.File;

public class FileCache {
    private File cacheDir, cacheDirFeeds, cacheDirImage;

    public FileCache(Context context) {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "MRSSReader");
            cacheDirFeeds = new File(cacheDir, "Feeds");
            cacheDirImage = new File(cacheDir, "Images");
        } else {
            cacheDir = context.getCacheDir();
            cacheDirFeeds = new File(cacheDir, "Feeds");
            cacheDirImage = new File(cacheDir, "Images");
        }

        if (!cacheDir.exists())
            cacheDir.mkdirs();
        if (!cacheDirFeeds.exists())
            cacheDirFeeds.mkdirs();
        if (!cacheDirImage.exists())
            cacheDirImage.mkdirs();
    }

    public File getRssFile(String url) {
        String filename = String.valueOf(url.hashCode());
        File f = new File(cacheDirFeeds, filename);
        return f;
    }

    public File getFile(File dir, String url) {
        String filename = String.valueOf(url.hashCode());
        File f = new File(dir, filename);
        return f;
    }

    public void clear(File dir) {
        File[] files = dir.listFiles();
        if (files == null)
            return;
        for (File f : files)
            f.delete();
    }

    public File getFile(String url) {
        String filename = String.valueOf(url.hashCode());
        File f = new File(cacheDir, filename);
        return f;
    }

    public void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null)
            return;
        for (File f : files)
            f.delete();
    }
}

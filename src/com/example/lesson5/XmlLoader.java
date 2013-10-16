package com.example.lesson5;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

public class XmlLoader extends Thread {
    private String path = "";
    Program program;
    public XmlLoader(String url, Program program)
    {
        this.path = url;
        this.program = program;
    }
    @Override
    public void run() {
        super.run();
        try {
            URL url = new URL(path);
            URLConnection connection = null;
            connection = url.openConnection();

            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String response = builder.toString();
            //Console.print(response);
            XmlDocument xd = new XmlDocument();
            xd.loadXml(response);
            program.onRssLoaded(xd);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

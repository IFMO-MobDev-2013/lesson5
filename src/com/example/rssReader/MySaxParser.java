package com.example.rssReader;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.util.Vector;

public class MySaxParser extends DefaultHandler{
    private Vector<String> vectorTitles = new Vector<String>();
    private Vector<String> vectorSummary = new Vector<String>();


    boolean canBegin = false;
    static String TITLE = "title#";
    static String SUMMARY = "summary#";
    static String DESCRIPTION = "description#";
    String lastTag = "";
    String st = "";

    public Vector<String> getVectorTitles() {
        return vectorTitles;
    }

    public Vector<String> getVectorSummary() {
        return vectorSummary;
    }
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if(qName.equals("entry") || qName.equals("item")) canBegin = true;
        if(qName.equals("summary")) {
            st += " <summary type=\"html\">";
        }
        if(qName.equals("description")) {
            st += "<description>";
        }
        lastTag = qName + "#";
    }
    public void endElement(String uri, String localName, String qName) {
        if(canBegin) {
            if(lastTag.equals(TITLE)) {
                vectorTitles.add(st);
            }
            if(lastTag.equals(SUMMARY) || lastTag.equals(DESCRIPTION)) {
                if(lastTag.equals(SUMMARY)) {
                    st += "</summary>";
                }
                if(lastTag.equals(DESCRIPTION)) {
                    st += "</description>";
                }
                vectorSummary.add(st);
            }
        }
        lastTag = qName;
        st = "";
    }

    public void characters(char[] ch, int start,int length)
            throws SAXException {
        String e = new String(ch, start, length);
        if(canBegin) {
            if(lastTag.equals(TITLE)) {
                st += e;
            }
            if(lastTag.equals(SUMMARY) || lastTag.equals(DESCRIPTION)) {
                st += e;
            }
        }


    }


}

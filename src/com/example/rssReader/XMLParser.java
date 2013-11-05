package com.example.rssReader;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

public class XMLParser{
    String answer = "";
    MySaxParser msp;
    public void putAnswer(String s) throws ParserConfigurationException, SAXException, IOException {
        answer = s;
        InputStream is = new ByteArrayInputStream(s.getBytes());
        msp = new MySaxParser();
        SAXParser saxParser = (SAXParserFactory.newInstance()).newSAXParser();
        saxParser.parse(is, msp);

    }


    public Vector<String> getTitles() {
        Vector<String> ans = new Vector<>();
        ans = msp.getVectorTitles();
        return ans;
    }


    public Vector<String> getSummary() {
        Vector<String> ans = new Vector<>();
        ans = msp.getVectorSummary();
        return ans;
    }
}

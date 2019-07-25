package com.learning.topdownloadedapps;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class Parser {
    private static final String TAG = "Parser";
    private ArrayList<Entry> songs;

    public Parser() {
        this.songs = new ArrayList<>();
    }

    public ArrayList<Entry> getSongs() {
        return songs;
    }

    public boolean parse(String xml){
        boolean status = true;
        Entry current = null;
        boolean inEntry = false;
        String text = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xml));
            int eventType = xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT) {
                String tag = xpp.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        Log.d(TAG, "parse: Starting tag for " + tag);
                        if("entry".equalsIgnoreCase(tag)) {
                            inEntry = true;
                            current = new Entry();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        Log.d(TAG, "parse: Ending tag for " + tag);
                        if(inEntry) {
                            if("entry".equalsIgnoreCase(tag)){
                                songs.add(current);
                                inEntry = false;
                            } else if ("name".equalsIgnoreCase(tag)) current.setName(text);
                            else if ("artist".equalsIgnoreCase(tag)) current.setArtist(text);
                            else if ("image".equalsIgnoreCase(tag)) current.setImage(text);
                            else if ("category".equalsIgnoreCase(tag)) current.setGenre(text);
                            else if ("collection".equalsIgnoreCase(tag)) current.setAlbum(text);
                        }
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }

        return status;
    }
}

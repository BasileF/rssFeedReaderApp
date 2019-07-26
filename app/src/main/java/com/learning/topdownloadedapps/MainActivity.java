package com.learning.topdownloadedapps;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);

        Log.d(TAG, "onCreate: Start AsyncTask");
        IncomingData incData = new IncomingData();
        incData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=10/xml");
        Log.d(TAG, "onCreate: Finished");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feeds_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String feedURL;

        switch(id) {
            case R.id.top10:
                feedURL = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=10/xml";
                break;
            case R.id.top25:
                feedURL = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=25/xml";
                break;
            case R.id.top200:
                feedURL = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=200/xml";
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        downloadUrl(feedURL);
        return true;
    }

    private void downloadUrl(String feedURL){
        IncomingData incData = new IncomingData();
        incData.execute(feedURL);
    }

    private class IncomingData extends AsyncTask<String, Void, String> {
        private static final String TAG = "IncomingData";
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: Param is " + s);
            Parser p = new Parser();
            p.parse(s);

            FeedAdapter feedAdapter = new FeedAdapter(MainActivity.this, R.layout.list_record, p.getSongs());
            listView.setAdapter(feedAdapter);
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: First entry is " + strings[0]);
            String rssFeed = downloadXML(strings[0]);
            if(rssFeed == null) Log.e(TAG, "doInBackground: Error downloading XML");
            return rssFeed;
        }

        private String downloadXML(String path){
            StringBuilder xml = new StringBuilder();

            try {
                URL url = new URL(path);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d(TAG, "downloadXML: Response code is " + response);
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                char[] readXML = new char[500];
                while(true){
                    int code = reader.read(readXML);
                    if(code < 0) break;
                    if(code > 0) xml.append(readXML,0,code);
                }
                reader.close();
                return xml.toString();
            } catch(MalformedURLException e) {
                Log.e(TAG, "downloadXML: MalformedURLException " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "downloadXML: IOException " + e.getMessage());
            } catch (SecurityException e) {
                Log.e(TAG, "downloadXML: SecurityException - Lack of permissions " + e.getMessage());
            }
            return null;
        }
    }
}
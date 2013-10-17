package ru.ifmo.rain.loboda.rss;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnnotationsActivity extends Activity {
    ProgressDialog dialog;
    RSSFetcher fetcher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = ProgressDialog.show(this, "Загрузка", "Загружается RSS поток", true, false);
        setContentView(R.layout.annotations);
        Bundle bundle = getIntent().getExtras();
        fetcher = new RSSFetcher();
        fetcher.execute(bundle.getString("Url"));
    }

    private void showResult(List<RSSRecord> records) {
        ListView listView = (ListView) findViewById(R.id.listView);
        if(records != null){
            MyAdapter adapter = new MyAdapter(this, records);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(adapterView.getContext(), Details.class);
                    RSSRecord record = (RSSRecord)adapterView.getAdapter().getItem(i);
                    intent.putExtra("Author", record.getAuthor());
                    intent.putExtra("Date", record.getDate());
                    intent.putExtra("Annotation", record.getAnnotation());
                    intent.putExtra("Description", record.getDescription());
                    startActivity(intent);
                }
            });
            dialog.dismiss();
        } else {
            dialog.dismiss();
            Toast.makeText(getApplicationContext(), fetcher.getCause(), 1).show();
            this.finish();
        }
    }

    private class RSSFetcher extends AsyncTask<String, Void, List<RSSRecord>>{
        public String cause;

        public String getCause(){
            return cause;
        }

        @Override
        protected List<RSSRecord> doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                inputStream = (new URL(strings[0])).openStream();
                String charset;
                byte[] buffer = new byte[2000];
                int count = 0;
                for(int i = 0;i < 2000; ++i){
                    int ch = inputStream.read();
                    if(ch == -1){
                        break;
                    }
                    buffer[count] = (byte) ch;
                    ++count;
                }
                byte[] newBuffer = new byte[count];
                System.arraycopy(buffer, 0, newBuffer, 0, count);
                String s = new String(newBuffer);
                Pattern pattern = Pattern.compile("encoding=\"(.*?)\"");
                Matcher matcher = pattern.matcher(s);
                boolean existsEncoding = matcher.find();
                if(existsEncoding){
                    charset = matcher.group(1);
                } else {
                    charset = null;
                }
                RSSParser parser = new RSSParser(new SequenceInputStream(new ByteArrayInputStream(newBuffer), inputStream), charset);
                List<RSSRecord> records = parser.parse();
                return records;
            } catch (MalformedURLException e) {
                cause = "Невреный URL адрес";
                return null;
            } catch (SAXException e) {
                cause = "RSS канал отсутствует, пуст или содержит ошибки";
                return null;
            } catch (UnsupportedEncodingException e) {
                cause = "RSS канал отсутствует, пуст или содержит ошибки";
                return null;
            } catch (IOException e) {
                cause = "Ошибка чтения канала";
                return null;
            } catch (ParserConfigurationException e) {
                cause = "Ошибка чтения канала";
                return null;
            } catch (Exception e){
                cause = "Ошибка чтения канала";
                return null;
            } finally {
                if(inputStream != null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {}
                }
            }
        }

        @Override
        protected void onPostExecute(List<RSSRecord> records){
            showResult(records);
            return;
        }
    }
}


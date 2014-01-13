package com.example.HomeWork5;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Дмитрий
 * Date: 12.01.14
 * Time: 22:35
 * To change this template use File | Settings | File Templates.
 */
public class ChannelActivity extends Activity {
    final ArrayList<String> channels = new ArrayList<String>();
    final ArrayList<String> channelsName = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel);
        setChannels();

        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, channelsName);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if(checkInternetConnection()){
                    TextView textView = (TextView) findViewById(R.id.textView);
                    textView.setTextSize(30);
                    textView.setText("Загружаемся...");
                    Intent channel = new Intent(ChannelActivity.this, MyActivity.class);
                    channel.putExtra("channel", channels.get(i));
                    startActivity(channel);
                    finish();
 /*               }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "К сожалению, мы не смогли подключиться к интеренеру;(" +
                            " Возможно вам следует в следующий раз несколько раньше его оплачивать!", Toast.LENGTH_LONG);
                    toast.show();
                }   */
            }
        });
    }

    private void setChannels(){
        channels.add("http://stackoverflow.com/feeds/tag/android");
        channelsName.add("StackOverFlow - Android");
        channels.add("http://lenta.ru/rss");
        channelsName.add("Лента.ру");
        channels.add("http://bash.im/rss/");
        channelsName.add("Bash");
        channels.add("http://ria.ru/export/rss2/world/index.xml");
        channelsName.add("Риа-новости: в мире");
        channels.add("http://ria.ru/export/rss2/sport/index.xml");
        channelsName.add("Риа-новости: спорт");
        channels.add("http://ria.ru/export/rss2/incidents/index.xml");
        channelsName.add("Риа-новости: происшествия");
        channels.add("http://ria.ru/export/rss2/science/index.xml");
        channelsName.add("Риа-новости: наука");
        channels.add("http://Habrahabr.ru/rss/hubs/");
        channelsName.add("Хабр");

    }

    public boolean checkInternetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager == null)
            return false;
        NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
        if(networkInfo == null)
            return false;

        for(NetworkInfo currentInfo : networkInfo){
            if(currentInfo.getTypeName().equalsIgnoreCase("WIFI"))
                if(currentInfo.isConnected())
                    return  true;
            if(currentInfo.getTypeName().equalsIgnoreCase("MOBILE"))
                if(currentInfo.isConnected())
                    return true;
        }
        return false;
    }
}

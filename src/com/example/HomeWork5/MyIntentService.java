package com.example.HomeWork5;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created with IntelliJ IDEA.
 * User: Дмитрий
 * Date: 13.01.14
 * Time: 0:52
 * To change this template use File | Settings | File Templates.
 */
public class MyIntentService extends IntentService {

    public MyIntentService(){
        super("Its' the best Name!");
    }

    public void onCreate(){
        super.onCreate();
    }

    @Override
    public void onHandleIntent(Intent myIntent){
        String channelName = myIntent.getExtras().getString("channel");

    }
}

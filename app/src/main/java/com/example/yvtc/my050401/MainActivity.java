package com.example.yvtc.my050401;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    NotificationManager manager;
    final int NOTIFICATION_ID = 321;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }

    public void click1 (View v){
        Intent it = new Intent(MainActivity.this,DetailActivity.class);
        String msg = "這是通知的內容....";
        it.putExtra("msg",msg);
        PendingIntent pi = PendingIntent.getActivity(MainActivity.this,123,it,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(MainActivity.this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("這是通知")
                .setContentText("這裡是內容")
                .setContentIntent(pi)
                .setAutoCancel(true);
        Notification notification = builder.build();
        manager.notify(NOTIFICATION_ID,notification);
    }

    public void click2 (View v){
        manager.cancel(NOTIFICATION_ID);
    }

    public void clickStartService (View v){
        Intent it = new Intent(MainActivity.this , MyService.class);
        startService(it);
    }
}

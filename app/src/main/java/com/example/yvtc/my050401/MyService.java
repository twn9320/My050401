package com.example.yvtc.my050401;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyService extends Service {
    Handler handler = new Handler();
    int count;
    Context context;
    NotificationManager manager;
    final int NOTIFICATION_ID =567;
    final int NOTIFICATION_TEMP_ID =789;
    public MyService() {
    }

    Runnable showTime = new Runnable() {
        @Override
        public void run() {
            Log.d("SER1","Time:"+new java.util.Date());

            if (count <10){
                count++;
                handler.postDelayed(this,1000);
            }
            else{
                Intent it = new Intent(context, DetailActivity.class);
                String msg = "十秒到了!!";
                it.putExtra("msg", msg);
                PendingIntent pi = PendingIntent.getActivity(context, 123, it, PendingIntent.FLAG_UPDATE_CURRENT);
                Notification.Builder builder = new Notification.Builder(MyService.this);
                builder.setSmallIcon(R.mipmap.ic_launcher)
                       .setContentTitle("這是十秒通知")
                       .setContentText(msg)
                       .setContentIntent(pi)
                       .setAutoCancel(true);
                Notification notification = builder.build();
                manager.notify(NOTIFICATION_ID, notification);

            }
        }
    };
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        Log.d("SER1","onBlind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("SER1","This i s onCreate");


    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        Log.d("SER1","This is onStartCommand");
        count  = 0;
        context = getApplicationContext();
        manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        handler.post(showTime);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("temp");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                int temp = Integer.valueOf(value);
                Log.d("SER1","Temp:"+temp);
                if(temp >=40){
                    Intent it = new Intent(context, DetailActivity.class);
                    String msg = "溫度過高!! , 目前 "+temp +"度!";
                    it.putExtra("msg", msg);
                    PendingIntent pi = PendingIntent.getActivity(context, 123, it, PendingIntent.FLAG_UPDATE_CURRENT);
                    Notification.Builder builder = new Notification.Builder(MyService.this);
                    builder.setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("這是高溫警報")
                            .setContentText(msg)
                            .setContentIntent(pi)
                            .setAutoCancel(true);
                    Notification notification = builder.build();
                    manager.notify(NOTIFICATION_TEMP_ID, notification);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(showTime);

        Log.d("SER1","This is onDestroy");
    }
}

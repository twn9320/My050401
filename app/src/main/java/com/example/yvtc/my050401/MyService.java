package com.example.yvtc.my050401;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

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
        return super.onStartCommand(intent, flags, startId);
    }
}

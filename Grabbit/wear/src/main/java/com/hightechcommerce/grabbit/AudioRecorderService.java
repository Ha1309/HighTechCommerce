package com.hightechcommerce.grabbit;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AudioRecorderService extends Service {
    public AudioRecorderService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void start() {

    }

    private void stop() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        String action = intent.getAction();

        if ("start".equals(action)) {
            start();

        } else if ("stop".equals(action)) {
            stop();

        }

        return START_STICKY;
    }
}

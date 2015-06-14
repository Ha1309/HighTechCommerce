package com.hightechcommerce.grabbit;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;

public class GrabbitActivity extends Activity {

    private TextView mTextView;

    private int notificationId;
    private final String group_Id = "grabbit_group";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabbit);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);

            }
        });

        notificationId = 1;

        Intent i = new Intent(this, AudioRecorderService.class);
        i.setAction("start");
        startService(i);
    }

    public void callNotify(String title, String text, Bitmap picture) {
        Notification notification = new NotificationCompat.Builder(getApplication())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(text)
                .setVibrate(new long[]{1000})
                .setGroup(group_Id)
                .extend(
                        new NotificationCompat.WearableExtender()
                                .setHintShowBackgroundOnly(true)
                                .setBackground(picture))
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplication());

        notificationManager.notify(notificationId, notification);
        notificationId += 1;
    }

    public void gogoNote(View v) {
        Notification notification = new NotificationCompat.Builder(getApplication())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Edika: Coca Cola 2l")
                .setContentText("2 for 1: 1,23€")
                .setGroup(group_Id)
                .setVibrate(new long[]{1000})
                .extend(
                        new NotificationCompat.WearableExtender()
                                .setHintShowBackgroundOnly(true)
                                .setBackground(BitmapFactory.decodeResource(getResources(), R.drawable.hintergrundcoke320_360)))
                .build();
        Log.d("Test", "TestString");
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplication());

        notificationManager.notify(notificationId, notification);
        notificationId += 1;
    }
}

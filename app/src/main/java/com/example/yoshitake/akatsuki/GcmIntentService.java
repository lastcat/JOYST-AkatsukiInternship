package com.example.yoshitake.akatsuki;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by Yoshitake on 2014/12/14.
 */
public class GcmIntentService extends IntentService {
    private Handler mHandler = new Handler();
    private static final String TAG = "GcmIntentService";
    public GcmIntentService(){
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);
        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                Log.d(TAG, "messageType: " + messageType + ",body:" + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                Log.d(TAG,"messageType: " + messageType + ",body:" + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                Log.d(TAG, "messageType: " + messageType + ",body:" + extras.toString());
                mHandler.post(new Runnable(){
                    @Override
                    public void run() {
                        //ここで通知受けたときの挙動を描いたような気がする　とりあえずnotificationでお知らせ？
                        //getExtrasは多分鯖から投げてもらう感じだと思われる
                        Toast.makeText(getApplicationContext(), extras.getString("message"), Toast.LENGTH_LONG).show();
                        Notification n = new Notification();
                        n.icon = R.drawable.ic_launcher;
                        n.tickerText = extras.getString("message");
                        n.defaults = Notification.DEFAULT_VIBRATE;
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, i, 0);
                        n.setLatestEventInfo(getApplicationContext(),"脱落報告",extras.getString("message"), pi);
                        NotificationManager nm =
                                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                        nm.notify(1,n);
                        //Utility.Looser.add(extras.getString("message"));
                    }
                });
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }
}

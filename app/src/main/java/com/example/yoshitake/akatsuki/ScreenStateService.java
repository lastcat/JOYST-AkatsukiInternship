package com.example.yoshitake.akatsuki;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

/**
 * Created by Yoshitake on 2014/12/13.
 */
public class ScreenStateService extends Service{

    private BroadcastReceiver mScreenOnListener = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context,Intent intent) {
            String action = intent.getAction();

            //画面の電源が入ったらActivity起動
            //本当はこっちを使わないといけないのかもしれないけど、とりあえず封印。
            if (action.equals(Intent.ACTION_SCREEN_ON)) {
                Intent i = new Intent(context, LockScreenActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }
    };

    @Override
    public void onStart(Intent intent, int startId){
        super.onStart(intent,startId);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(mScreenOnListener,filter);
    }

    public void OnDestroy(){
        unregisterReceiver(mScreenOnListener);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }


}

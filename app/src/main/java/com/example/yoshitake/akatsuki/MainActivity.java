package com.example.yoshitake.akatsuki;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import android.os.Handler;
import android.widget.Toast;


public class MainActivity extends Activity {

    //メンバー検索、時間設定、確認、タイマー画面、結果　各Activity
    //挫折失敗はpush通知でそれ用のActivityを表示する

    //タイマー時間設定画面
    private int settingHour;
    private int settingmenutes;


    @OnClick(R.id.goTimerStart)
    void timerStart(){
        Toast.makeText(this,"timerSet",Toast.LENGTH_SHORT).show();
        settingHour = Integer.parseInt(h.getText().toString());
        settingmenutes = Integer.parseInt(m.getText().toString());


        Intent intent = new Intent(this,TimerStartActivity.class);
        intent.putExtra("hour",settingHour);
        intent.putExtra("minute",settingmenutes);
        startActivity(intent);
        //h,mをミリ秒変換して次Activityに渡す
        //mHandler.postDelayed(TestTask,10000);
    }

    @InjectView(R.id.rest_time)
    TextView tv;
    @InjectView(R.id.settedHour)
    EditText h;
    @InjectView(R.id.settedMinute)
    EditText m;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        Utility.Looser = new ArrayList<String>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


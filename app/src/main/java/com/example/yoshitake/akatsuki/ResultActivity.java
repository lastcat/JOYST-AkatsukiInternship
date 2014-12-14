package com.example.yoshitake.akatsuki;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class ResultActivity extends Activity {

    //最終結果発表Activity。通知で飛んできた人間のID情報を覚えておいてWIN/LOSを出して終わり（がんばれ）
    //TODO:Timer中に配列かなんかにpush来たid突っ込んどいて表示、とかで。
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Toast.makeText(getApplicationContext(),Utility.Looser.get(0),Toast.LENGTH_LONG).show();
        //普通にWINNER/LOOSERリスト良い気がする
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.result, menu);
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

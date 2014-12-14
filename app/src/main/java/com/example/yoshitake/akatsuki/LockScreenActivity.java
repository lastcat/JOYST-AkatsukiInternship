package com.example.yoshitake.akatsuki;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class LockScreenActivity extends Activity {

    //TODO:中断してしまったらpush通知
    //受信のテストも、可能なら。
    private long miliSec;
    private Context context = this;
    MyCountDownTimer cdt;
    /*private MyHandler mHandler = new MyHandler();
    private Runnable UpdateTimer = new Runnable() {
        @Override
        public void run() {
            m+=1;
            h+=1;
            hours.setText(Integer.toString(h));
            minutes.setText(Integer.toString(m));
        }
    };//////////////////////////////////
    */
    @InjectView(R.id.hour)
    TextView hours;
    @InjectView(R.id.mininute)
    TextView minutes;
    @InjectView(R.id.seconds)
    TextView seconds;

    @OnClick(R.id.btn_release)
    void releaseLock(){
        //解除ボタンのイベントリスナー。ここでpush通知飛ばす？
        //push通知投げる/受ける
        //RegInBack();
        //FailedPush(1,"APA91bHTbbU73ZMdUhDAbWpXEiYaKSyKUgcQ4ZeMrLyY-nCBPU9LIdMrhrdi7v9sUEpli_2SovakUXHWaCzEKW203FtMoJ_pvWzfroq7idAQW3uQdK9VqNpH136Mko7kllcdQw-JFwcn5meDqIONRkgbkfif-f-ebMKZ8LWSpIcLx4bWB0oixUU");
        PushInBack();
        finish();
    }

    /*@OnClick(R.id.pushButton)
    void pushTest(){
        PushInBack();
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
        ButterKnife.inject(this);

        miliSec =  getIntent().getIntExtra("miliSec",0);
        cdt = new MyCountDownTimer(miliSec,1000);
        cdt.start();
        //「n時間後に実行」は↓
        //mHandler.postDelayed()
        //定期実行は↓

        //Lock解除画面より手前に
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        ButterKnife.inject(this);
    }

    /*public void onAttachedToWindow(){
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lock_screen, menu);
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

    public void RegInBack() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {

                //return doGet("http://api.openweathermap.org/data/2.5/weather?q=Tokyo,jp");
                RegUID("http://www.akt.hatabune.com/userRegist.php");
                return "SUCCESS";
            }

            @Override
            protected void onPostExecute(String result) {
                Toast.makeText(context,result,Toast.LENGTH_LONG).show();
            }
        }.execute(null, null, null);
    }

    public void PushInBack() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {

                //return doGet("http://api.openweathermap.org/data/2.5/weather?q=Tokyo,jp");
                PushTest("http://www.akt.hatabune.com/userRegist.php");
                //FailedPush(1,"APA91bHTbbU73ZMdUhDAbWpXEiYaKSyKUgcQ4ZeMrLyY-nCBPU9LIdMrhrdi7v9sUEpli_2SovakUXHWaCzEKW203FtMoJ_pvWzfroq7idAQW3uQdK9VqNpH136Mko7kllcdQw-JFwcn5meDqIONRkgbkfif-f-ebMKZ8LWSpIcLx4bWB0oixUU");

                return "SUCCESS";
            }

            @Override
            protected void onPostExecute(String result) {
                Toast.makeText(context,result,Toast.LENGTH_LONG).show();
            }
        }.execute(null, null, null);
    }
    //id登録
    public String RegUID(String url){
        try{
            //urlにPOSTする　regidを渡さないといけない
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
            String regid = gcm.register("581875980262");
            Log.d("regid?",regid);
            HttpPost method = new HttpPost( url );
            DefaultHttpClient client = new DefaultHttpClient();
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("regid",regid));
            params.add(new BasicNameValuePair("uname","test1"));

            method.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));

            HttpResponse response = client.execute(method);
            int status = response.getStatusLine().getStatusCode();
            client.getConnectionManager().shutdown();

            if (status != HttpStatus.SC_OK)
                return Integer.toString(status);

            return EntityUtils.toString(response.getEntity(),"UTF-8");
        }
        catch(Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    public void PushTest (String uname){
        try{
            //urlにPOSTする　regidを渡さないといけない

            HttpPost method = new HttpPost( "http://www.akt.hatabune.com/pushTest.php" );
            DefaultHttpClient client = new DefaultHttpClient();
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid","APA91bHTbbU73ZMdUhDAbWpXEiYaKSyKUgcQ4ZeMrLyY-nCBPU9LIdMrhrdi7v9sUEpli_2SovakUXHWaCzEKW203FtMoJ_pvWzfroq7idAQW3uQdK9VqNpH136Mko7kllcdQw-JFwcn5meDqIONRkgbkfif-f-ebMKZ8LWSpIcLx4bWB0oixUU"));

            method.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));

            HttpResponse response = client.execute(method);
            int status = response.getStatusLine().getStatusCode();
            client.getConnectionManager().shutdown();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void MakeGroup(ArrayList<String> userlist,int gid){
        try{
            //urlにPOSTする　regidを渡さないといけない

            HttpPost method = new HttpPost( "http://www.akt.hatabune.com/groupRegist.php" );
            DefaultHttpClient client = new DefaultHttpClient();
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("gId",Integer.toString(gid)));
            params.add(new BasicNameValuePair("U1",userlist.get(0)));
            params.add(new BasicNameValuePair("U2",userlist.get(1)));
            params.add(new BasicNameValuePair("U3",userlist.get(2)));
            params.add(new BasicNameValuePair("U4",userlist.get(3)));
            params.add(new BasicNameValuePair("U5",userlist.get(4)));
            method.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));

            HttpResponse response = client.execute(method);
            int status = response.getStatusLine().getStatusCode();
            client.getConnectionManager().shutdown();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void FailedPush(int gID,String uID){
        try{
            //urlにPOSTする　regidを渡さないといけない

            HttpPost method = new HttpPost( "http://www.akt.hatabune.com/failedPush.php" );
            DefaultHttpClient client = new DefaultHttpClient();
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid",uID));
            params.add(new BasicNameValuePair("gid",Integer.toString(gID)));
            method.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));

            HttpResponse response = client.execute(method);
            int status = response.getStatusLine().getStatusCode();
            client.getConnectionManager().shutdown();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public class MyCountDownTimer extends CountDownTimer{

        public MyCountDownTimer(long miliisInFuture, long countDownInterval){
            super(miliisInFuture,countDownInterval);
        }

        @Override
        public void onFinish(){
            //Activity遷移
            Intent i = new Intent(context,ResultActivity.class);
            startActivity(i);

        }

        @Override
        public void onTick(long miliinUntilFinished){
            hours.setText(Long.toString(miliinUntilFinished/1000/60/60)+":");
            minutes.setText(Long.toString(miliinUntilFinished/1000%3600/60)+":");
            seconds.setText(Long.toString(miliinUntilFinished/1000%60));
        }

    }

}

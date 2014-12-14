package com.example.yoshitake.akatsuki;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class TimerStartActivity extends Activity {


    private int miliSecond;
    /*private Handler mHandler = new android.os.Handler();
    private Runnable TimerTask = new Runnable() {
        @Override
        public void run() {
            //Timer終了後処理。最終結果Activityへ遷移。
            //TODO:おそらくBroacastReceiverとかを使う必要がある
            //タイマー画面でやっても良いのではという説がある
            Intent fIntent = new Intent(getApplicationContext(),ResultActivity.class);
            fIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startService(fIntent);
        }
    };*/

    @OnClick(R.id.timerstart)
    void startTimer(){
        //mHandler.postDelayed(TimerTask,miliSecond);
        Intent intent = new Intent(getApplicationContext(),LockScreenActivity.class);
        //MakeGInBack();
        intent.putExtra("miliSec",miliSecond);
        startActivity(intent);
     }

    @InjectView(R.id.setHour)
    TextView setHour;
    @InjectView(R.id.setMinute)
    TextView setMinute;

    //実際にタイマーを作動させるActivity。ダミー友人を表示する必要はあるかもしれないけど、とりあえずGoサインのみ。
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_start);
        ButterKnife.inject(this);
        int h = getIntent().getIntExtra("hour",0);
        int m = getIntent().getIntExtra("minute",0);
        setHour.setText(Integer.toString(h)+"時間");
        setMinute.setText(Integer.toString(m)+"分");
        miliSecond = h*3600*1000 + m*60*1000;
        //miliSecond = m;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timer_start, menu);
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

    public void MakeGInBack() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {

                String test1 = "APA91bH8KHVMj_AtJNatJgHtN96YCigdHd0OrsrPKR1zgzjkk8qFdd1fDW9fqCN5T8x1NdSZ4qQ_bbrOluTcbJgG3wVc_kSV8IX_-zaa_kAEvjOcpVlkXfIwQEBY6Enhq9CZ-Kop9xHrH-zPq8Zrpp6-_Dr9edxig72RZCZCjjZ5vb6UK-jahcU";
                String test = "APA91bHTbbU73ZMdUhDAbWpXEiYaKSyKUgcQ4ZeMrLyY-nCBPU9LIdMrhrdi7v9sUEpli_2SovakUXHWaCzEKW203FtMoJ_pvWzfroq7idAQW3uQdK9VqNpH136Mko7kllcdQw-JFwcn5meDqIONRkgbkfif-f-ebMKZ8LWSpIcLx4bWB0oixUU";
                ArrayList<String> userList = new ArrayList<String>();
                userList.add(test);
                userList.add(test1);

                MakeGroup(userList,1);


                return "SUCCESS";
            }

            @Override
            protected void onPostExecute(String result) {
                //Toast.makeText(context,result,Toast.LENGTH_LONG).show();
            }
        }.execute(null, null, null);
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
            params.add(new BasicNameValuePair("U3","0"));
            params.add(new BasicNameValuePair("U4","0"));
            params.add(new BasicNameValuePair("U5","0"));
            method.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));

            HttpResponse response = client.execute(method);
            int status = response.getStatusLine().getStatusCode();
            client.getConnectionManager().shutdown();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

package com.bawei.demo.demo2;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int i = 1;
    private PullToRefreshListView pxl;
    private List<User.ResultsBean> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pxl = findViewById(R.id.pxl);
        initData(i);
        pxl.setMode(PullToRefreshBase.Mode.BOTH);
        pxl.setScrollingWhileRefreshingEnabled(true);
        pxl.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                initData(i++);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                initData(i++);
            }
        });
    }
    @SuppressLint("StaticFieldLeak")
    private void initData(int i) {

        final String path = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/"+ this.i;
        new AsyncTask<String,Void,String>(){

            private String aa;

            @Override
            protected String doInBackground(String... strings) {
                try {
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setConnectTimeout(5000);
                    connection.setRequestMethod("GET");
                    int code = connection.getResponseCode();
                    if(code == 200){
                        InputStream is = connection.getInputStream();
                        int len;
                        byte[] arr = new byte[1024*10];
                        aa = "";
                        while ((len=is.read(arr))!=-1){
                            String s = new String(arr, 0, len);
                            aa +=s;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return aa;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Gson gson = new Gson();
                User user = gson.fromJson(s, User.class);
                list = user.results;
                Toast.makeText(MainActivity.this, "list:" + list.size(), Toast.LENGTH_SHORT).show();
                pxl.setAdapter(new MyAdapter());
                pxl.onRefreshComplete();
            }
        }.execute();
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            TextView tv = new TextView(MainActivity.this);
            tv.setText(list.get(i).createdAt);
            return tv;
        }
    }
}

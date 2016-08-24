package com.innotech.demo.activity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.innotech.demo.BuildConfig;
import com.innotech.demo.R;
import com.innotech.demo.entity.Movie;
import com.innotech.demo.data.MovieModel;
import com.innotech.demo.data.retrofit.ProgressSubscriber;
import com.innotech.demo.utils.NativeUtil;
import com.innotech.demo.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.login)
    public void login() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rxAndroid)
    public void rxAndroid() {
//        final ApplicationInfo appInfo = getApplicationContext().getApplicationInfo();
//        final int appFlags = appInfo.flags;
//        ToastUtil.show(this, "debug " + BuildConfig.DEBUG + " ===  " + (0 != (appFlags & ApplicationInfo.FLAG_DEBUGGABLE)));
        ToastUtil.show(this, NativeUtil.checkSignature(this) + NativeUtil.getPrivateKey(this));
    }

    @OnClick(R.id.retrofit)
    public void retrofit() {
        MovieModel.getTopMoive(0, 10, new ProgressSubscriber<Movie>(this) {
            @Override
            public void onNext(Movie movie) {
                ToastUtil.show(MainActivity.this, movie.title);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

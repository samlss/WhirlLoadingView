package com.iigo.whirlloadingview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.iigo.library.WhirlLoadingView;

public class MainActivity extends AppCompatActivity {
    private WhirlLoadingView whirlLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        whirlLoadingView = findViewById(R.id.wlv_loading);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        whirlLoadingView.release();
    }

    public void onStart(View view) {
        whirlLoadingView.start();
    }

    public void onStop(View view) {
        whirlLoadingView.stop();
    }

    public void onResume(View view) {
        whirlLoadingView.resume();
    }

    public void onPause(View view) {
        whirlLoadingView.pause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //to test change the size of view.
//        ViewGroup.LayoutParams layoutParams = whirlLoadingView.getLayoutParams();
//        layoutParams.width = 400;
//        layoutParams.height = 400;
//        whirlLoadingView.setLayoutParams(layoutParams);

        //to test change color of view
//        whirlLoadingView.setColor(Color.RED);
        return super.onKeyDown(keyCode, event);
    }
}

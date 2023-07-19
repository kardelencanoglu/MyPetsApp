package com.kardelen.sahipsizkahramanlar.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import com.kardelen.sahipsizkahramanlar.MainActivity;
import com.kardelen.sahipsizkahramanlar.R;
import com.kardelen.sahipsizkahramanlar.adoption.AdoptionActivity;
import com.kardelen.sahipsizkahramanlar.login.LoginActivity;
import com.kardelen.sahipsizkahramanlar.utils.Utils;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_screen);
        Utils.setUpFullscreen(getWindow().getDecorView());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);

    }

    @Override
    public void onBackPressed() {

    }
}
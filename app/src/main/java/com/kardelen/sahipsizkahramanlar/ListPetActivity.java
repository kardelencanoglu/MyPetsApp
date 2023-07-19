package com.kardelen.sahipsizkahramanlar;

import android.os.Handler;
import android.view.View;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.kardelen.sahipsizkahramanlar.utils.Utils;

public class ListPetActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_pet);
        Utils.setUpFullscreen(getWindow().getDecorView());
    }

    @Override
    public void onBackPressed() {

    }

}
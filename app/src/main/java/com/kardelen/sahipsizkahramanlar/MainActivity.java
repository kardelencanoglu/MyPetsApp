package com.kardelen.sahipsizkahramanlar;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.kardelen.sahipsizkahramanlar.adoption.AdoptionActivity;

public class MainActivity extends AppCompatActivity {

    ImageButton ownership, adoption; //adoption = sahiplendirme
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        adoption = findViewById(R.id.adopting);
        ownership = findViewById(R.id.ownership);
        adoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   adoption.setBackgroundResource(R.drawable.light_rose_button);
                Intent intent = new Intent(getApplicationContext(), AdoptionActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ownership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    adoption.setBackgroundResource(R.drawable.light_rose_button);
            }
        });

    }

}
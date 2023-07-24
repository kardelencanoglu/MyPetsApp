package com.kardelen.sahipsizkahramanlar;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.kardelen.sahipsizkahramanlar.adoption.AdoptionActivity;
import com.kardelen.sahipsizkahramanlar.pet.ListPetActivity;
import com.kardelen.sahipsizkahramanlar.utils.Utils;

public class MainActivity extends AppCompatActivity {

    ImageButton ownership, adoption; //adoption = sahiplendirme
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setUpFullscreen(getWindow().getDecorView());
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
                Intent intent = new Intent(getApplicationContext(), ListPetActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {

    }

}
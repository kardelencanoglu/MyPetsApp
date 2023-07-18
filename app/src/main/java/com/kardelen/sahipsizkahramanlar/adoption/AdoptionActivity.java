package com.kardelen.sahipsizkahramanlar.adoption;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kardelen.sahipsizkahramanlar.MainActivity;
import com.kardelen.sahipsizkahramanlar.R;
import com.kardelen.sahipsizkahramanlar.login.LoginActivity;
import com.vishnusivadas.advanced_httpurlconnection.PutData;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdoptionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageButton backButton;
    String name, breed, age, species, gender;

    EditText nameText, breedText, ageText;
    Button saveButton;
    Spinner speciesSpinner, genderSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoption);

        nameText = findViewById(R.id.name);
        breedText = findViewById(R.id.breed);
        ageText = findViewById(R.id.age);

        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = String.valueOf((nameText.getText()));
                breed = String.valueOf(breedText.getText());
                age = String.valueOf(ageText.getText());
                species = "Stt";
                gender = "Stt";

                if (!name.equals("") && !breed.equals("") && !age.equals("") && !species.equals("") && !gender.equals("")) {

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[5];
                            field[0] = "name";
                            field[1] = "breed";
                            field[2] = "age";
                            field[3] = "speices";
                            field[4] = "gender";
                            //Creating array for data
                            String[] data = new String[5];
                            data[0] = name;
                            data[1] = breed;
                            data[2] = age;
                            data[3] = species;
                            data[4] = gender;
                            PutData putData = new PutData("http://192.168.1.46/loginregister/createpet.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    //End ProgressBar (Set visibility to GONE)
                                    Log.i("PutData", result);
                                    if(result.equals("Pet Save Success")){
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "HATA",Toast.LENGTH_SHORT).show();
                }
            }
        });



        speciesSpinner = findViewById(R.id.species);
        genderSpinner = findViewById(R.id.gender);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.animalsSpecies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapterSecond = ArrayAdapter.createFromResource(this,
                R.array.gender, android.R.layout.simple_spinner_item);
        adapterSecond.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        speciesSpinner.setAdapter(adapter);
        genderSpinner.setAdapter(adapterSecond);

        speciesSpinner.setOnItemSelectedListener(this);
        genderSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if ( spinner.getId() ==R.id.species) {
            String item = parent.getItemAtPosition(position).toString();
            Toast.makeText(this, "Spinner 1: " + item, Toast.LENGTH_SHORT).show();
        } else if (spinner.getId() == R.id.gender) {
            String item = parent.getItemAtPosition(position).toString();
            Toast.makeText(this, "Spinner 2: " + item, Toast.LENGTH_SHORT).show();
        }
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
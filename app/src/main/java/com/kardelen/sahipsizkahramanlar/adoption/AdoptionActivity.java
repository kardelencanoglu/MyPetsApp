package com.kardelen.sahipsizkahramanlar.adoption;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kardelen.sahipsizkahramanlar.MainActivity;
import com.kardelen.sahipsizkahramanlar.R;
import com.kardelen.sahipsizkahramanlar.login.LoginActivity;
import com.kardelen.sahipsizkahramanlar.otp.CreatePetPostRequestTask;
import com.kardelen.sahipsizkahramanlar.otp.PostRequestTask;
import com.kardelen.sahipsizkahramanlar.utils.Utils;
import com.vishnusivadas.advanced_httpurlconnection.PutData;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class AdoptionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, CreatePetPostRequestTask.OnPostRequestListener {

    ImageButton backButton;
    String name, breed, age, species, gender;

    EditText nameText, breedText, ageText;
    Button saveButton;
    String serverUrl = "http://192.168.1.46/loginregister/createpet.php";
    Spinner speciesSpinner, genderSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setUpFullscreen(getWindow().getDecorView());
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

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int age;
                String name = String.valueOf(nameText.getText());
                String breed = String.valueOf(breedText.getText());

                try {
                    age = Integer.parseInt(String.valueOf(ageText.getText()));
                }catch (NumberFormatException e){
                    age = 0;
                }

                String species = String.valueOf(speciesSpinner.getSelectedItem());
                String gender = String.valueOf(genderSpinner.getSelectedItem());

                String ageString = age + "";

                if(!name.equals("") && !breed.equals("") && !ageString.equals("") && !species.equals("") && !gender.equals("")) {

                    CreatePetPostRequestTask createPetPostRequestTask = new CreatePetPostRequestTask(name,
                            breed,
                            age,
                            species,
                            gender,
                            AdoptionActivity.this);
                    try {
                        createPetPostRequestTask.execute(serverUrl);
                    } catch (IllegalStateException e) {
                        Log.d("OTP", e + "");
                    }
                } else {
                    Toasty.error(getApplicationContext(), "Please Fill All Information!", Toast.LENGTH_SHORT, true).show();
                }
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if ( spinner.getId() == R.id.species) {
            String item = parent.getItemAtPosition(position).toString();
            //Toast.makeText(this, "Spinner 1: " + item, Toast.LENGTH_SHORT).show();
        } else if (spinner.getId() == R.id.gender) {
            String item = parent.getItemAtPosition(position).toString();
            //Toast.makeText(this, "Spinner 2: " + item, Toast.LENGTH_SHORT).show();
        }
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onPostRequestCompleted(String result) {
        Log.d("TAG", "onPostRequestCompleted: " + result);


        if(Objects.equals(result, "Pet Save Success")){
            Toasty.success(getApplicationContext(), "Pet Save Success!", Toast.LENGTH_SHORT, true).show();
        } else {
            Toasty.error(getApplicationContext(), "An Unknown Error Occurred Try Again!" + result, Toast.LENGTH_SHORT, true).show();
        }
    }

    @Override
    public void onBackPressed() {

    }

}
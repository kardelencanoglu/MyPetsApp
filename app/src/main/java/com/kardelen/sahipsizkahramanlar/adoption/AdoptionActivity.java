package com.kardelen.sahipsizkahramanlar.adoption;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class AdoptionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageButton backButton;
    String name, breed, age, species, gender;

    EditText nameText, breedText, ageText;
    Button saveButton;
    String serverUrl = "http://192.168.1.46/loginregister/createpet.php";
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
//                name = String.valueOf((nameText.getText()));
//                breed = String.valueOf((breedText.getText()));
//                age = String.valueOf((ageText.getText()));
//                species = String.valueOf(speciesSpinner.getSelectedItem());
//                gender = String.valueOf(genderSpinner.getSelectedItem());
//                try {
//                    // Post verileri
//                    Map<String, String> postData = new HashMap<>();
//                    postData.put("name", name);
//                    postData.put("breed", breed);
//                    postData.put("age", age);
//                    postData.put("species", species);
//                    postData.put("gender", gender);
//
//                    // Post isteği gönderme
//                    URL url = new URL(serverUrl);
//                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                    connection.setRequestMethod("POST");
//                    connection.setDoOutput(true);
//
//                    // Verileri isteğe eklemek için OutputStream kullanalım
//                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
//                    outputStream.writeBytes(getPostDataString(postData));
//                    outputStream.flush();
//                    outputStream.close();
//
//                    // Sunucudan gelen cevabı okuyalım
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                    String line;
//                    StringBuilder response = new StringBuilder();
//
//                    while ((line = reader.readLine()) != null) {
//                        response.append(line);
//                    }
//
//                    reader.close();
//
//                    // Sunucudan gelen cevabı kullanın
//                    System.out.println("Server Response: " + response.toString());
//                    connection.disconnect();
//                } catch (
//                        IOException e) {
//                    e.printStackTrace();
//                }

//                name = String.valueOf((nameText.getText()));
//                breed = String.valueOf((breedText.getText()));
//                age = String.valueOf((ageText.getText()));
//                species = String.valueOf(speciesSpinner.getSelectedItem());
//                gender = String.valueOf(genderSpinner.getSelectedItem());
//
//                if (!name.equals("") && !breed.equals("") && !age.equals("") && !species.equals("") && !gender.equals("")) {
//
//                    Handler handler = new Handler(Looper.getMainLooper());
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            //Starting Write and Read data with URL
//                            //Creating array for parameters
//                            String[] field = new String[5];
//                            field[0] = "name";
//                            field[1] = "breed";
//                            field[2] = "age";
//                            field[3] = "species";
//                            field[4] = "gender";
//                            //Creating array for data
//                            String[] data = new String[5];
//                            data[0] = name;
//                            data[1] = breed;
//                            data[2] = age;
//                            data[3] = species;
//                            data[4] = gender;
//                            PutData putData = new PutData("http://192.168.1.46/loginregister/createpet.php", "POST", field, data);
//                            if (putData.startPut()) {
//                                if (putData.onComplete()) {
//                                    String result = putData.getResult();
//                                    //End ProgressBar (Set visibility to GONE)
//                                    Log.i("PutData", result);
//                                    if(result.equals("Pet Save Success")){
//                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                        startActivity(intent);
//                                        finish();
//                                    }
//                                    else {
//                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            }
//                            //End Write and Read data with URL
//                        }
//                    });
//                }
//                else {
//                    Toast.makeText(getApplicationContext(), "HATA",Toast.LENGTH_SHORT).show();
//                }

                String name = String.valueOf(nameText.getText());
                String breed = String.valueOf(breedText.getText());
                String age = String.valueOf(ageText.getText());
                String species = String.valueOf(speciesSpinner.getSelectedItem());
                String gender = String.valueOf(genderSpinner.getSelectedItem());

                NetworkTask task = new NetworkTask();
                task.execute(name, breed, age, species, gender);
            }
        });
    }

    private class NetworkTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            String breed = params[1];
            String age = params[2];
            String species = params[3];
            String gender = params[4];

            String response = null;
            try {
                // Post verileri
                Map<String, String> postData = new HashMap<>();
                postData.put("name", name);
                postData.put("breed", breed);
                postData.put("age", age);
                postData.put("species", species);
                postData.put("gender", gender);

                // Post isteği gönderme
                URL url = new URL(serverUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                // Verileri isteğe eklemek için OutputStream kullanalım
                DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                outputStream.writeBytes(getPostDataString(postData));
                outputStream.flush();
                outputStream.close();

                // Sunucudan gelen cevabı okuyalım
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder responseBuilder = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }

                reader.close();

                // Sunucudan gelen cevabı kullanın
                response = responseBuilder.toString();
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            // İşlem tamamlandıktan sonra sunucudan gelen cevabı kullanabilirsiniz
            if (response != null) {
                System.out.println("Server Response: " + response);
            }
        }
    }

    private String getPostDataString(Map<String, String> postData) throws IOException {
        StringBuilder postDataString = new StringBuilder();
        for (Map.Entry<String, String> entry : postData.entrySet()) {
            if (postDataString.length() != 0) {
                postDataString.append('&');
            }
            postDataString.append(entry.getKey());
            postDataString.append('=');
            postDataString.append(entry.getValue());
        }
        return postDataString.toString();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if ( spinner.getId() == R.id.species) {
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
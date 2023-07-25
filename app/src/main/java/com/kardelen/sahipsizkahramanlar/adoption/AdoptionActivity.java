package com.kardelen.sahipsizkahramanlar.adoption;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.kardelen.sahipsizkahramanlar.pet.ListPetActivity;
import com.kardelen.sahipsizkahramanlar.MainActivity;
import com.kardelen.sahipsizkahramanlar.R;
import com.kardelen.sahipsizkahramanlar.otp.CreatePetPostRequestTask;
import com.kardelen.sahipsizkahramanlar.utils.Utils;
import es.dmoral.toasty.Toasty;
import okhttp3.*;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class AdoptionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, CreatePetPostRequestTask.OnPostRequestListener {
    Uri uri;
    Button backButton;
    String name, breed, age, species, gender;
    ImageView imageView;
    EditText nameText, breedText, ageText;
    Button saveButton, uploadButton;
    String serverUrl = "http://192.168.1.46/androidpets/index.php";
    Spinner speciesSpinner, genderSpinner;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setUpFullscreen(getWindow().getDecorView());
        setContentView(R.layout.activity_adoption);

        nameText = findViewById(R.id.name);
        breedText = findViewById(R.id.breed);
        ageText = findViewById(R.id.age);
        backButton = findViewById(R.id.backButton);
        imageView = findViewById(R.id.petImage);
        uploadButton = findViewById(R.id.btnUpload);
        speciesSpinner = findViewById(R.id.species);
        genderSpinner = findViewById(R.id.gender);

        ActivityResultLauncher<Intent> activityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                       if(result.getResultCode() == Activity.RESULT_OK){
                           Intent data = result.getData();
                           uri = data.getData();
                           try {
                               bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                               imageView.setImageBitmap(bitmap);
                           } catch (IOException e) {
                               throw new RuntimeException(e);
                           }
                       }
                    }
                });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



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

//                ByteArrayOutputStream byteArrayOutputStream;
//                byteArrayOutputStream = new ByteArrayOutputStream();

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

                if(!name.equals("") && !breed.equals("") && !ageString.equals("") && !species.equals("") && !gender.equals("") && bitmap != null) {
//                    bitmap.compress(Bitmap.CompressFormat.JPEG,100, byteArrayOutputStream);
//                    byte [] bytes = byteArrayOutputStream.toByteArray();
//                    final String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);

                    //RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url ="http://192.168.1.46/androidpets/index.php?add=333";

//                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                            new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String response) {
//                                    if(true){
//                                        Toasty.success(getApplicationContext(), "Image Upload!", Toast.LENGTH_SHORT, true).show();
//                                    }
//                                    else  Toasty.error(getApplicationContext(), "Failed to Upload Image!", Toast.LENGTH_SHORT, true).show();
//                                }
//                            }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Toasty.error(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT, true).show();
//                        }
//                    }){
//                        protected Map<String, String> getParams(){
//                            Map<String, String> paramV = new HashMap<>();
//                            paramV.put("upload", base64Image);
//                            paramV.put("name", "base64Image");
//                            paramV.put("breed", "base64Image");
//                            paramV.put("age", "2");
//                            paramV.put("species", "base64Image");
//
//                            return paramV;
//                        }
//                    };
                    //queue.add(stringRequest);


//                    CreatePetPostRequestTask createPetPostRequestTask = new CreatePetPostRequestTask(name,
//                            breed,
//                            age,
//                            species,
//                            gender,
//                            AdoptionActivity.this);
//                    try {
//                        createPetPostRequestTask.execute(serverUrl);
//                    } catch (IllegalStateException e) {
//                        Log.d("OTP", e + "");
//                    }

                    Pet pet = new Pet(name, breed, species, age+"");

                    uploadImageToServer(getApplicationContext(),uri, pet);

                    Intent intent = new Intent(getApplicationContext(), ListPetActivity.class);
                    Toasty.success(getApplicationContext(), "Save Success!", Toast.LENGTH_SHORT, true).show();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    startActivity(intent);
                    finish();

                } else {
                    Toasty.error(getApplicationContext(), "Please Fill All Information!", Toast.LENGTH_SHORT, true).show();
                }
            }
        });
    }
    public static void uploadImageToServer(Context context, Uri imageUri, Pet pet) {
        File imageFile = new File(getRealPathFromURI(context, imageUri));

        OkHttpClient client = new OkHttpClient();

        // Resim dosyasını oluşturun
        RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), imageFile);

        // Diğer özellikleri JSON verisi olarak oluşturun
        Gson gson = new Gson();
        String petJson = gson.toJson(pet);
        RequestBody dataBody = RequestBody.create(MediaType.parse("application/json"), petJson);

        // Resim ve diğer özellikleri birleştirin
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("upload", imageFile.getName(), imageBody)
                .addFormDataPart("name", pet.getName())
                .addFormDataPart("breed", pet.getBreed())
                .addFormDataPart("species", pet.getSpecies())
                .addFormDataPart("age", pet.getAge())
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://192.168.1.46/androidpets/index.php?add=333")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                // Sunucuya gönderme hatası
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                // Sunucudan yanıt alma işlemleri
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();

                    // Sunucudan gelen yanıtı burada işleyebilirsiniz.
                } else {
                    // Sunucudan hatalı yanıt alındığında burası çalışır.
                }
            }
        });
    }
    public static String getRealPathFromURI(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor == null) {
            return null;
        }
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
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

        Intent i = new Intent(AdoptionActivity.this, ListPetActivity.class);
        if(true){
            Toasty.success(getApplicationContext(), "Pet Save Success!", Toast.LENGTH_SHORT, true).show();
            startActivity(i);
            finish();
        } else {
            Toasty.error(getApplicationContext(), "An Unknown Error Occurred Try Again!" + result, Toast.LENGTH_SHORT, true).show();
        }
    }

    @Override
    public void onBackPressed() {

    }



}
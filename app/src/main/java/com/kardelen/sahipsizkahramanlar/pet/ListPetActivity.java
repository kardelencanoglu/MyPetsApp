package com.kardelen.sahipsizkahramanlar.pet;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.kardelen.sahipsizkahramanlar.R;
import com.kardelen.sahipsizkahramanlar.utils.Utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ListPetActivity extends AppCompatActivity {

    public ListView listView;
    public ArrayAdapter<String> adapter;
    public ArrayList<String> dataList;

    GetApiDataTask getApiDataTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_pet);
        Utils.setUpFullscreen(getWindow().getDecorView());


        listView = findViewById(R.id.listView);
        dataList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);

        new GetApiDataTask(this).execute("http://192.168.1.46/loginregister/getpets.php");
    }

    @Override
    public void onBackPressed() {

    }

}

class GetApiDataTask extends AsyncTask<String, Void, String> {

    ListPetActivity listPetActivity;

    public GetApiDataTask(ListPetActivity activity) {
        this.listPetActivity = activity;
    }

    @Override
    protected String doInBackground(String... urls) {
        String result = "";
        HttpURLConnection connection = null;

        try {
            URL url = new URL(urls[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Bağlantıyı başlatın
            connection.connect();

            // API'dan gelen veriyi okuyun
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            result = stringBuilder.toString();

        } catch (IOException e) {
            Log.e("GetApiDataTask", "Error: " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {
            // JSON veriyi ekrana yazdır
            JSONObject jsonObject = new JSONObject(result);
            Log.e("JSON Data", "Data: " + jsonObject);
            JSONArray dataArray = jsonObject.getJSONArray("data");

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject dataObject = dataArray.getJSONObject(i);
                String name = dataObject.getString("name");
                String breed = dataObject.getString("breed");
                String age = dataObject.getString("age");
                String species = dataObject.getString("species");
                String gender = dataObject.getString("gender");

                // Veriyi birleştirin ve dataList'e ekleyin
                String listItem = "Name: " + name + "\nBreed: " + breed + "\nAge: " + age + "\nSpecies: " + species + "\nGender: " + gender;
                listPetActivity.dataList.add(listItem);
            }

            // Değişiklikleri adapter'a bildirin ve ListView'i güncelleyin
            listPetActivity.adapter.notifyDataSetChanged();
            //     textView.setText(jsonObject.toString(4));
        } catch (JSONException e) {
            Log.e("JSON Parsing", "Error: " + e.getMessage());
        }
    }
}
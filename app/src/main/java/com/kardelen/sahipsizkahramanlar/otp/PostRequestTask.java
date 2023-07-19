package com.kardelen.sahipsizkahramanlar.otp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostRequestTask extends AsyncTask<String, Void, String> {

    private final String email;
    private final OnPostRequestListener listener;

    public PostRequestTask(String email, OnPostRequestListener listener) {
        this.email = email;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... urls) {
        if (urls == null || urls.length == 0) {
            return null;
        }

        String result = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(urls[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Create the request body as JSON
            String requestBody = "{\"email\":\"" + email + "\"}";

            // Write the request body to the output stream
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestBody.getBytes());
            outputStream.flush();
            outputStream.close();

            // Read the response from the server
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
                bufferedReader.close();
                result = response.toString();
            } else {
                result = "Error: " + responseCode;
            }
        } catch (IOException e) {
            Log.e("PostRequestTask", "Error: " + e.getMessage());
            result = "Error: " + e.getMessage();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if (listener != null) {
            listener.onPostRequestCompleted(result);
        }
    }

    public interface OnPostRequestListener {
        void onPostRequestCompleted(String result);
    }
}

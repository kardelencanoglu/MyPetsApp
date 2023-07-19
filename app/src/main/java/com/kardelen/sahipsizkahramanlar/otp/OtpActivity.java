package com.kardelen.sahipsizkahramanlar.otp;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.kardelen.sahipsizkahramanlar.MainActivity;
import com.kardelen.sahipsizkahramanlar.R;
import com.kardelen.sahipsizkahramanlar.adoption.AdoptionActivity;

public class OtpActivity extends AppCompatActivity  implements PostRequestTask.OnPostRequestListener{

    PinView otpText;
    Button authButton;
    String otp , recOtp;

    TextView resendOtp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        otpText = findViewById(R.id.otpInput);
        authButton = findViewById(R.id.otpButton);
        resendOtp = findViewById(R.id.resendOtp);


        Intent intent = new Intent(this, MainActivity.class);

        String serverUrl = "https://otp-server-li85.onrender.com/sendotp";

        // İstek gönderilecek email adresi
        String email = "dev.ahmettopak@gmail.com";

        // PostRequestTask'i başlat
        PostRequestTask postRequestTask = new PostRequestTask(email, this);
        postRequestTask.execute(serverUrl);
        authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = otpText.getText().toString();
                if (otp.equals(recOtp)){
                    Toast.makeText(OtpActivity.this, "Hoşgeldiniz", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else {
                    Toast.makeText(OtpActivity.this, "Girdiğiniz OTP Kodu Hatalı!" + otp, Toast.LENGTH_SHORT).show();
                }
            }
        });


        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    postRequestTask.execute(serverUrl);
                }
                catch (IllegalStateException e){
                    Log.d("OTP" , e + "");
                }

            }
        });
    }
    @Override
    public void onPostRequestCompleted(String result) {
        Log.d("TAG", "onPostRequestCompleted: " + result);

        result = result.replaceAll("[^0-9]", ""); // Sadece rakamları koru
        int number = Integer.parseInt(result);
        System.out.println(number);
        recOtp = result;
    }
}
package com.kardelen.sahipsizkahramanlar.otp;

import android.view.WindowManager;
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
import com.kardelen.sahipsizkahramanlar.utils.Utils;
import es.dmoral.toasty.Toasty;


public class OtpActivity extends AppCompatActivity  implements PostRequestTask.OnPostRequestListener{

    PinView otpText;
    Button authButton;
    String otp , recOtp;

    TextView resendOtp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setUpFullscreen(getWindow().getDecorView());
        setContentView(R.layout.activity_otp);

        otpText = findViewById(R.id.otpInput);
        authButton = findViewById(R.id.otpButton);
        resendOtp = findViewById(R.id.resendOtp);

        String email = "";
        String serverUrl = "https://kardelen-service.onrender.com/sendotp";
        if (getIntent().hasExtra("email")) {
            email = getIntent().getStringExtra("email");
            //Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
        }

        // PostRequestTask'i başlat
        PostRequestTask postRequestTask = new PostRequestTask(email, this);
        postRequestTask.execute(serverUrl);
        authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = otpText.getText().toString();
                if (otp.equals(recOtp)){
                    Toasty.success(getApplicationContext(), "Verification Success!", Toast.LENGTH_SHORT, true).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toasty.error(getApplicationContext(), "Verification Failed!", Toast.LENGTH_SHORT, true).show();

                }
            }
        });


        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Toasty.info(getApplicationContext(), "Check Your Email!", Toast.LENGTH_SHORT, true).show();
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

    @Override
    public void onBackPressed() {

    }
}
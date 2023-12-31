package com.kardelen.sahipsizkahramanlar.login;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.kardelen.sahipsizkahramanlar.R;
import com.kardelen.sahipsizkahramanlar.otp.OtpActivity;
import com.kardelen.sahipsizkahramanlar.utils.Utils;
import com.vishnusivadas.advanced_httpurlconnection.PutData;
import es.dmoral.toasty.Toasty;


public class LoginActivity extends AppCompatActivity {

    EditText textInputEditTextEmail, textInputEditTextPassword;
    Button buttonLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        Utils.setUpFullscreen(getWindow().getDecorView());
        setContentView(R.layout.activity_login);

        textInputEditTextPassword = findViewById(R.id.passwordLogin);
        textInputEditTextEmail = findViewById(R.id.emailLogin);
        buttonLogin = findViewById(R.id.cirLoginButton);
        Intent intent = new Intent(this, OtpActivity.class);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email, password;
                email = String.valueOf(textInputEditTextEmail.getText());
                password = String.valueOf(textInputEditTextPassword.getText());

                if (!email.equals("") && !password.equals("")) {

                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "email";
                            field[1] = "password";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = email;
                            data[1] = password;
                            PutData putData = new PutData("http://192.168.137.95/loginregister/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    //End ProgressBar (Set visibility to GONE)
                                    Log.i("PutData", result);
                                    if(result.equals("Login Success")){
                                        Toasty.success(getApplicationContext(), " Login Success!", Toast.LENGTH_SHORT, true).show();
                                        intent.putExtra("email", email);
                                        startActivity(intent);
                                    }
                                    else {
                                        Toasty.error(getApplicationContext(), "Your Email Or Password Is Incorrect!", Toast.LENGTH_SHORT, true).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }
                else {
                    Toasty.info(getApplicationContext(), "Please Fill In The Blank Fields!", Toast.LENGTH_SHORT, true).show();
                }
            }
        });


    }

    public void onLoginClick(View View){
        startActivity(new Intent(this,RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);

    }

    @Override
    public void onBackPressed() {

    }
}
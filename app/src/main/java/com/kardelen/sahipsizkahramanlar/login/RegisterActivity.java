package com.kardelen.sahipsizkahramanlar.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.kardelen.sahipsizkahramanlar.MainActivity;
import com.kardelen.sahipsizkahramanlar.R;
import com.kardelen.sahipsizkahramanlar.utils.Utils;
import com.vishnusivadas.advanced_httpurlconnection.PutData;
import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity {

    EditText textInputEditTextFullName, textInputEditTextUserName, textInputEditTextPassword, textInputEditTextEmail;
    Button buttonSignUp;
    TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setUpFullscreen(getWindow().getDecorView());
        setContentView(R.layout.activity_register);
        changeStatusBarColor();

        textInputEditTextFullName = findViewById(R.id.fullname);
        textInputEditTextUserName = findViewById(R.id.username);
        textInputEditTextPassword = findViewById(R.id.password);
        textInputEditTextEmail = findViewById(R.id.email);
        buttonSignUp = findViewById(R.id.cirRegisterButton);
        textViewLogin = findViewById(R.id.registerText);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullname, username, password, email;
                fullname = String.valueOf(textInputEditTextFullName.getText());
                username = String.valueOf(textInputEditTextUserName.getText());
                password = String.valueOf(textInputEditTextPassword.getText());
                email = String.valueOf(textInputEditTextEmail.getText());

                if (!fullname.equals("") && !username.equals("") && isValidPassword(password) && isValidEmail(email)){



                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "fullname";
                            field[1] = "username";
                            field[2] = "password";
                            field[3] = "email";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = fullname;
                            data[1] = username;
                            data[2] = password;
                            data[3] = email;
                            PutData putData = new PutData("http://192.168.137.95/loginregister/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    //End ProgressBar (Set visibility to GONE)
                                    Log.i("PutData", result);
                                    if(result.equals("Sign Up Success")){
                                   //     Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toasty.info(getApplicationContext(), "This Username or E-mail Address Is Already Registered!",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                } else {
                    if (fullname.equals("")){
                        Toasty.info(getApplicationContext(), "Fullname Cannot Be Empty!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if (!isValidEmail(email)) {
                            Toasty.info(getApplicationContext(), "Cannot Enter Valid Email Address!",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (username.equals("")) {
                                Toasty.info(getApplicationContext(), "Username Cannot Be Empty!",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if ( !isValidPassword(password)){
                                    Toasty.info(getApplicationContext(), "Your Password Must Have At Least 6 Digits!",Toast.LENGTH_SHORT).show();
                                }
                            }

                        }

                    }

                }
            }
        });
    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.roseColor));
        }
    }

    public void onLoginClick(View view){
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        // Burada şifre için istediğiniz kriterlere göre kontrol sağlayabilirsiniz.
        // Örneğin, en az bir büyük harf, bir küçük harf, bir sayı ve minimum uzunluk şartı ekleyebilirsiniz.
        return password.length() >= 6;
}

    @Override
    public void onBackPressed() {

    }
}

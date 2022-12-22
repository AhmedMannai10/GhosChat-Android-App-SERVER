package com.content.xchat_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import entity.User;


public class Login extends AppCompatActivity {
    private AuthHandler authHandler;
    private EditText editEmail;
    private EditText editPassword;
    private Button btnLogin;
    private TextView btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.edit_email);
        editPassword = findViewById(R.id.edit_password);
        btnLogin = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btn_signup);
        authHandler = new AuthHandler();
        Thread t1  = new Thread( authHandler);
        t1.start();

        btnSignUp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                intent.putExtra("auth", authHandler);
                startActivity(intent);
            }
        }
        );

        btnLogin.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){

                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();


                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                if(login(email, password)){
                    startActivity(intent);
                }
            }
        });

    }

    private boolean login(String email, String password){
        if(email.length() == 0 || password.length() == 0){
            Toast.makeText(this.getBaseContext(),
                    "Please enter the full details",
                            Toast.LENGTH_SHORT
                    ).show();
            return false;
        }

        User user = new User(email, password);
        System.out.println(user);

        if(authHandler.login(user)){
            Toast.makeText(this.getBaseContext(),
                    "True",
                    Toast.LENGTH_SHORT
            ).show();
            return true;
        }else{
            Toast.makeText(this.getBaseContext(),
                    "Please Verify Your Information",
                    Toast.LENGTH_SHORT
            ).show();

            return false;

        }

    }
}
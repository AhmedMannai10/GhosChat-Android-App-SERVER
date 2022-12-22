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

public class SignUp extends AppCompatActivity {

    private AuthHandler authHandler;
    private EditText editName;
    private EditText editEmail;
    private EditText editPassword;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editName = findViewById(R.id.edit_name);
        editEmail = findViewById(R.id.edit_email);
        editPassword = findViewById(R.id.edit_password);
        btnSignUp = findViewById(R.id.btn_signup);
        Intent intent = getIntent();
        authHandler = (AuthHandler) intent.getSerializableExtra("auth");

        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString();
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                if(signUp(name, email, password)){
                    intent.putExtra("user", new User(name, email, password));
                    startActivity(intent);
                }
            }
        });


    }

    private boolean signUp(String name, String email, String password){
        if(email.length() == 0 || password.length() == 0 || name.length() == 0){
            Toast.makeText(this.getBaseContext(),
                    "Please enter the full details",
                    Toast.LENGTH_SHORT
            ).show();
            return false;
        }

        User user = new User(name, email, password);

        if (authHandler.login(user)) {
            Toast.makeText(this.getBaseContext(),
                    "True",
                    Toast.LENGTH_SHORT
            ).show();
            return true;
        } else {
            Toast.makeText(this.getBaseContext(),
                    "User already exist with that email",
                    Toast.LENGTH_SHORT
            ).show();
            return false;
        }

    }
}
package com.example.application1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Login extends AppCompatActivity {

    EditText email;
    EditText password;

    Button login;
    Button toRegister;

    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LinearLayout layout = findViewById(R.id.main_login_layout);

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);

        login = findViewById(R.id.Login);
        toRegister = findViewById(R.id.toRegister);

        db = new DB(this);

        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checkLogIn = db.logIn(Login.this, email.getText().toString(), password.getText().toString());

                if(checkLogIn) {

                    Intent intent = new Intent(Login.this, CalendarEvents.class);
                    intent.putExtra("user", email.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}

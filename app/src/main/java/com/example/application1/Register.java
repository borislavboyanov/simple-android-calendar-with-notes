package com.example.application1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText email;
    EditText password;
    EditText confirm;

    Button toLogin;
    Button register;

    DB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        confirm = findViewById(R.id.confirm);

        toLogin = findViewById(R.id.toLogin);
        register = findViewById(R.id.Register);

        email.setFocusable(true);
        email.setEnabled(true);
        email.setClickable(true);
        email.setFocusableInTouchMode(true);

        password.setFocusable(true);
        password.setEnabled(true);
        password.setClickable(true);
        password.setFocusableInTouchMode(true);

        confirm.setFocusable(true);
        confirm.setEnabled(true);
        confirm.setClickable(true);
        confirm.setFocusableInTouchMode(true);

        db = new DB(this);

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!password.getText().toString().equals(confirm.getText().toString()) || password.getText().toString().equals("")) {
                    Toast.makeText(Register.this, "Passwords do not match!", Toast.LENGTH_SHORT).show(); }

                else if (email.getText().toString().equals("")) {
                    Toast.makeText(Register.this, "Type an email!", Toast.LENGTH_SHORT).show();
                }

                else {
                    db.createUser(Register.this, email.getText().toString(), password.getText().toString());

                    db.close();

                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}

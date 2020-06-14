package com.example.demoapplication.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoapplication.Helper.DatabaseHandler;
import com.example.demoapplication.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar a = this.getSupportActionBar();
        if(a != null){
            a.hide();
        }
        final Button btnLogin = (Button) findViewById(R.id.login);
        final EditText edtUsername = findViewById(R.id.username);
        final EditText edtPassword =  findViewById(R.id.password);
        final TextView signUp = (TextView) findViewById(R.id.signup);

        final DatabaseHandler databaseHelper = new DatabaseHandler(MainActivity.this, null, null, 2);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isExist = databaseHelper.getregister(edtUsername.getText().toString());
                if(isExist){
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra("username", edtUsername.getText().toString());
                    startActivity(intent);
                    finish();
                } else {
                    edtPassword.setText(null);
                    Toast.makeText(MainActivity.this, "Login failed. Invalid username or password.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
package com.example.demoapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demoapplication.Helper.DatabaseHandler;
import com.example.demoapplication.R;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar a = this.getSupportActionBar();
        if(a != null){
            a.hide();
        }
        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password= (EditText) findViewById(R.id.password);
        final Button btnSignUp = (Button) findViewById(R.id.signup);
        final TextView back =(TextView) findViewById(R.id.back);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edfirst = username.getText().toString();
                String edpass = password.getText().toString();
                DatabaseHandler db = new DatabaseHandler(SignUpActivity.this, null, null, 2);
                boolean isExist = db.getregister(edfirst);
                if(isExist){
                    password.setError("Change the User details");
                    Toast.makeText(getApplicationContext(), "Already Registered, Please login or try with different combination", Toast.LENGTH_LONG).show();
                }else{
                    db.addregister(edfirst,edpass);
                    Toast.makeText(getApplicationContext(), "Registered..", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}

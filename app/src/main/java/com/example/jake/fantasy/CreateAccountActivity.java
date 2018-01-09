package com.example.jake.fantasy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateAccountActivity extends AppCompatActivity {


    Button signIn;
    EditText name,email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        signIn = findViewById(R.id.ca);
       // name = findViewById(R.id.name);
       // name = findViewById(R.id.name);
       // name = findViewById(R.id.name);
        signIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                startActivity(new Intent(CreateAccountActivity.this, WelcomeActiviy.class));
            }
        });
    }
}

package com.example.jake.fantasy;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStructure;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    TextView tv1,tv2;
    Typeface tf1;
    Button signIn, createA;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mAuth = FirebaseAuth.getInstance();
        tv1 = findViewById(R.id.textView);
        tv2 = findViewById(R.id.textView2);
        tf1 = Typeface.createFromAsset(getAssets(),  "abc.ttf");

        tv1.setTypeface(tf1);
        tv2.setTypeface(tf1);
        UpdateScore updateScore = new UpdateScore();
        updateScore.update();
        signIn = findViewById(R.id.button);
        createA = findViewById(R.id.button2);
        //signIn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        signIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //signIn.setBackgroundColor(Color.GRAY);
                startActivity(new Intent(MainActivity.this, SignInActivity.class));

            }
        });

        createA.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //signIn.setBackgroundColor(Color.GRAY);
                startActivity(new Intent(MainActivity.this, WelcomeActiviy.class));

            }
        });
    }

    @Override
    protected void onStart() {

        super.onStart();


    }
}

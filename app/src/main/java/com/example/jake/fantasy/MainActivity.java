package com.example.jake.fantasy;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    TextView tv1,tv2;
    Typeface tf1;
    Button signIn, createA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        tv1 = findViewById(R.id.textView);

        tv2 = findViewById(R.id.textView2);
        tf1 = Typeface.createFromAsset(getAssets(),  "abc.ttf");

        tv1.setTypeface(tf1);
        tv2.setTypeface(tf1);
        signIn = findViewById(R.id.button);
        createA = findViewById(R.id.button2);
        //signIn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        signIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //signIn.setBackgroundColor(Color.GRAY);
                startActivity(new Intent(MainActivity.this, PlayerList.class));

            }
        });

        createA.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //signIn.setBackgroundColor(Color.GRAY);
                startActivity(new Intent(MainActivity.this, CreateAccountActivity.class));

            }
        });
    }
}

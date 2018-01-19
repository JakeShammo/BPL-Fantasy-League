package com.example.jake.fantasy;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActiviy extends AppCompatActivity {


    TextView tv1,tv2,tv3,tv4;
    Typeface tf1;
    Button create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        final String userId=getIntent().getStringExtra("userId");
        tv1 = findViewById(R.id.welF);
        create = findViewById(R.id.button5);
        tv2 = findViewById(R.id.welL);
        tv3 = findViewById(R.id.welcome);
        tv4 = findViewById(R.id.rules);
        tf1 = Typeface.createFromAsset(getAssets(),  "abc.ttf");
        tv1.setTypeface(tf1);
        tv2.setTypeface(tf1);
        tv3.setTypeface(tf1);
        tv4.setTypeface(tf1);
        tv4.setPaintFlags(tv4.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        create.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //signIn.setBackgroundColor(Color.GRAY);
                Intent startIntent = new Intent(WelcomeActiviy.this,CreatingTeam1.class);
                startIntent.putExtra("userId",userId);
                startActivity(startIntent);

            }
        });
        tv4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //signIn.setBackgroundColor(Color.GRAY);
                startActivity(new Intent(WelcomeActiviy.this, RulesActivity.class));

            }
        });

    }
}

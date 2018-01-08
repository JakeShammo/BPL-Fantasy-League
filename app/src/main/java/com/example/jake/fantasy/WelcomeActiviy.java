package com.example.jake.fantasy;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class WelcomeActiviy extends AppCompatActivity {


    TextView tv1,tv2,tv3;
    Typeface tf1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        tv1 = findViewById(R.id.welF);

        tv2 = findViewById(R.id.welL);
        tv3 = findViewById(R.id.welcome);
        tf1 = Typeface.createFromAsset(getAssets(),  "abc.ttf");
        tv1.setTypeface(tf1);
        tv2.setTypeface(tf1);
        tv3.setTypeface(tf1);
    }
}

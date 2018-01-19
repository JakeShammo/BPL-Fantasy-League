package com.example.jake.fantasy;

import android.content.Intent;
import android.drm.DrmStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RulesActivity extends AppCompatActivity {
    TextView tb1,sr1,wr1;
    Button gob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Rules and Regulations");
        tb1 = findViewById(R.id.tbrule1);
        tb1.setText("\u2022 An user can build a team consisting 11 players.\n\n" +
                "\u2022 User will get 110M$ initially to build the team.\n\n" +
                "\u2022 Each player has a value, so the total value of the team should not exceed 110M$.\n\n" +
                "\u2022 User can choose his preferred formation. He can choose between 3-5 batsmen, 2-4 Allrounders, 2-4 Bowlers and 1 wicketkeeper. Whatever the formation, number of total players should be exactly 11.\n\n" +
                "\u2022 User can not hav more than 5 foreign players in his team.\n\n");
        sr1 = findViewById(R.id.Srule1);
        sr1.setText("\u2022 Scoring will be based on the performances of the players in the BPL T20 2017.\n\n" +
                "\u2022 For batting performance a player will get points by the following formula:\n\n" +
                "\tpoint = runs * (runs/balls)\n\n" +
                "\u2022 For bowling performance a player will get points by the following formula:\n\n" +
                "\tpoint = wkt*15 + (overs*7-ball)\n\n" +
                "\u2022 Also players will be awarded 5 points for each catch.\n\n");
        wr1 = findViewById(R.id.wrule1);
        wr1.setText("\u2022 Team's score will be the summation of the scores of the players in the team.\n\n" +
                "\u2022 Players' score will only be added after they have been added in the team.\n\n" +
                "\u2022 If a player is removed from the team his score will no longer be added.\n\n" +
                "\u2022 The highest scoring team at the end of the tournament will be the winner of the BPL Fantasy League 2017.\n\n");
        gob = findViewById(R.id.gob);
        gob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //signIn.setBackgroundColor(Color.GRAY);
                RulesActivity.super.onBackPressed();

            }
        });
    }
}

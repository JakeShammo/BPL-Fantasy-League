package com.example.jake.fantasy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class FilterActivity extends AppCompatActivity {


    EditText name;
    String []countries = {"Any","Bangladesh","Australia","England","West Indies","Zimbabwe","Pakistan"
            ,"Afghanistan","England","New Zealand","Sri Lanka","South Africa"};
    String []teams = {"Any","Dhaka","Chittagong","Rangpur","Sylhet","Rajshahi","Commilla","Khulna"};
    String []MaxPrices = {"15","14","13","12","11","10","9","8","7","6","5"};
    String []MinPrices = {"15","14","13","12","11","10","9","8","7","6","5"};
    ArrayAdapter<String> arrayCoun, arrayTeam,arrayMax,arrayMin;
    MaterialBetterSpinner coutSpin, teamSpin, maxSpin,minSpin;
    String role,userId,posi,one;
    Button filter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        role=getIntent().getStringExtra("Role");
        userId=getIntent().getStringExtra("UserId");
        posi=getIntent().getStringExtra("Position");
        one=getIntent().getStringExtra("One");
        name = findViewById(R.id.nameFilterInp);
        coutSpin = findViewById(R.id.spinnerCountry);
        teamSpin = findViewById(R.id.spinnerTeam);
        maxSpin = findViewById(R.id.spinnerMaxPrice);
        minSpin = findViewById(R.id.spinnerMinPrice);
        arrayCoun = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,countries);
        arrayTeam = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,teams);
        arrayMax = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,MaxPrices);
        arrayMin = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,MinPrices);
        coutSpin.setAdapter(arrayCoun);
        teamSpin.setAdapter(arrayTeam);
        maxSpin.setAdapter(arrayMax);
        minSpin.setAdapter(arrayMin);
        coutSpin.setText("Any");
        teamSpin.setText("Any");
        maxSpin.setText("15");
        minSpin.setText("5");
        filter = findViewById(R.id.filterPage);
        filter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //signIn.setBackgroundColor(Color.GRAY);
                Intent startIntent = new Intent(FilterActivity.this,PlayerList.class);
                startIntent.putExtra("UserId",userId);
                startIntent.putExtra("One",one);
                startIntent.putExtra("Position",posi);
                startIntent.putExtra("Role",role);
                Log.d("fil",coutSpin.getText().toString());
                startIntent.putExtra("Country",coutSpin.getText().toString());
                startIntent.putExtra("Name",name.getText().toString());
                startIntent.putExtra("Team",teamSpin.getText().toString());
                startIntent.putExtra("MaxPrice",maxSpin.getText().toString());
                startIntent.putExtra("MinPrice",minSpin.getText().toString());
                startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


                startActivity(startIntent);

            }
        });
    }
}

package com.example.jake.fantasy;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class CreatingTeam1 extends AppCompatActivity {


    EditText tname,tmotto;
    String []batOpt = {"3","4","5"};
    String []wktOpt = {"1"};
    String []allOpt = {"2","3","4"};
    String []bolOpt = {"2","3","4","5"};
    FirebaseAuth mAuth;
    DatabaseReference dref;
    ValueEventListener mListener;
    ArrayAdapter <String> arrayBat, arrayBol,arrayWkt,arrayAll;
    MaterialBetterSpinner batSpin, bolSpin, wktSpin,allSpin;
    int total, batsel,bolsel,wktsel, allsel;
    private static final String TAG = "playerlist";
    Button select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_team1);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Creating Team");
        tname = findViewById(R.id.teamName);
        tmotto = findViewById(R.id.teamMotto);
        batSpin = findViewById(R.id.spinnerBat);
        bolSpin = findViewById(R.id.spinnerBol);
        allSpin = findViewById(R.id.spinnerAll);
        wktSpin = findViewById(R.id.spinnerWkt);
        arrayBat = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,batOpt);
        arrayAll = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,allOpt);
        arrayBol = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,bolOpt);
        arrayWkt = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,wktOpt);
        batSpin.setAdapter(arrayBat);
        wktSpin.setAdapter(arrayWkt);
        bolSpin.setAdapter(arrayBol);
        allSpin.setAdapter(arrayAll);
        batSpin.setText("4");
        allSpin.setText("3");
        bolSpin.setText("3");
        wktSpin.setText("1");
        final String userId=getIntent().getStringExtra("userId");
        dref= FirebaseDatabase.getInstance().getReference();
        dref = dref.child("USERS").child(userId);
        mAuth = FirebaseAuth.getInstance();
        mListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("slow","ashche5");
                total = Integer.parseInt(dataSnapshot.child("TotalSelected").getValue().toString());
                if(total == 11) {
                    tname.setText(dataSnapshot.child("TeamName").getValue().toString());
                    tmotto.setText(dataSnapshot.child("TeamMotto").getValue().toString());
                    batsel = Integer.parseInt(dataSnapshot.child("BatsmenSel").getValue().toString());
                    bolsel = Integer.parseInt(dataSnapshot.child("BowlerSel").getValue().toString());
                    wktsel = Integer.parseInt(dataSnapshot.child("WktKeeperSel").getValue().toString());
                    allsel = Integer.parseInt(dataSnapshot.child("AllrounderSel").getValue().toString());
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        dref.addValueEventListener(mListener);
        select = findViewById(R.id.selectSquad);
        select.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!validateForm()) {

                    return;
                }
                final String name = tname.getText().toString();
                final String motto = tmotto.getText().toString();
                final int bat = Integer.parseInt(batSpin.getText().toString());
                final int all = Integer.parseInt(allSpin.getText().toString());
                final int wkt = Integer.parseInt(wktSpin.getText().toString());
                final int bol = Integer.parseInt(bolSpin.getText().toString());
                if(bat+all+wkt+bol!=11){
                    Toast.makeText(CreatingTeam1.this, "Invalid formation",
                            Toast.LENGTH_SHORT).show();
                    return;

                }
                //int total;


                        Log.d("slow","ashche6");
                        if (total!=11){
                            dref.child("Batsmen").setValue(Integer.toString(bat));
                            dref.child("Bowler").setValue(Integer.toString(bol));
                            dref.child("Allrounder").setValue(Integer.toString(all));
                            dref.child("WktKeeper").setValue(Integer.toString(wkt));
                            dref.child("TeamName").setValue(name);
                            dref.child("Rank").setValue(0);
                            dref.child("TeamMotto").setValue(motto);
                            dref.child("BatsmenSel").setValue(Integer.toString(0));
                            dref.child("BowlerSel").setValue(Integer.toString(0));
                            dref.child("AllrounderSel").setValue(Integer.toString(0));
                            dref.child("WktKeeperSel").setValue(Integer.toString(0));

                        }
                        else{
                            dref.child("Batsmen").setValue(Integer.toString(bat));
                            dref.child("Bowler").setValue(Integer.toString(bol));
                            dref.child("Allrounder").setValue(Integer.toString(all));
                            dref.child("WktKeeper").setValue(Integer.toString(wkt));
                            dref.child("TeamName").setValue(name);
                            dref.child("TeamMotto").setValue(motto);
                            dref.child("BatsmenSel").setValue(Integer.toString(Math.min(batsel,bat)));
                            dref.child("BowlerSel").setValue(Integer.toString(Math.min(bolsel,bol)));
                            dref.child("AllrounderSel").setValue(Integer.toString(Math.min(allsel,all)));
                            dref.child("WktKeeperSel").setValue(Integer.toString(1));
                        }
                Intent startIntent = new Intent(CreatingTeam1.this,CreatingTeam2.class);
                startIntent.putExtra("userId",userId);

                //Log.d(TAG,"intent");
                startActivity(startIntent);

                dref.addValueEventListener(mListener);


                //Log.d(TAG,"intent");


            }
        });
    }

    private boolean validateForm() {
        boolean valid = true;
        String motto = tmotto.getText().toString();
        if (TextUtils.isEmpty(motto)) {
            tmotto.setError("Required.");
            valid = false;
        }
        else {
            tmotto.setError(null);
        }

        String name = tname.getText().toString();
        if (TextUtils.isEmpty(name)) {
            tname.setError("Required.");
            valid = false;
        }
        else {
            tname.setError(null);
        }
        return valid;
    }
    @Override
    protected void onStop() {
        if (mListener != null && dref!=null) {
            dref.removeEventListener(mListener);
        }
        super.onStop();
    }

    @Override
    protected void onPause() {
        if (mListener != null && dref!=null) {
            dref.removeEventListener(mListener);
        }
        super.onPause();
    }
}

package com.example.jake.fantasy;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeamStats extends AppCompatActivity {
    private static final String TAG = "TeamFragment";
    int batno,bolno,allno,batsel,bolsel,wktsel,allsel,money,total,foreign = 0,price=0,k=0;
    DatabaseReference dref;
    TextView tmMotto,tmName,point,bestPlayer,worstPlayer;
    String userId,teamName,teamMotto;
    ValueEventListener mListener;
    BarChart barChart;
    ArrayList <BarEntry> barEntries;
    BarDataSet dataSet;
    ArrayList<String> labels;
    BarData barData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_stats);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Team Stat");
        tmName = findViewById(R.id.nameteamS);
        tmMotto = findViewById(R.id.MottoS);
        userId= getIntent().getStringExtra("userId");
        bestPlayer = findViewById(R.id.bpV);
        worstPlayer = findViewById(R.id.wpV);
        point = findViewById(R.id.pointsS);
        barChart = findViewById(R.id.teamBar);
        barEntries = new ArrayList<>();
        labels = new ArrayList<>();
        getData();
    }
    void getData(){
        dref = FirebaseDatabase.getInstance().getReference();
        mListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                DataSnapshot dataSnapshot = data.child("USERS").child(userId);
                //TextView balance = findViewById(R.id.total_balance_value);
                teamName = (String)dataSnapshot.child("TeamName").getValue();
                //Log.d(TAG,teamName);
                tmName.setText(teamName);
                teamMotto = (String)dataSnapshot.child("TeamMotto").getValue();
                //Log.d(TAG,teamName);
                tmMotto.setText(teamMotto);

                //Log.d(TAG,"hoise");
                batno = Integer.parseInt(dataSnapshot.child("Batsmen").getValue().toString());
                bolno  = Integer.parseInt(dataSnapshot.child("Bowler").getValue().toString());
                allno = Integer.parseInt(dataSnapshot.child("Allrounder").getValue().toString());

                int teamScore = 0;
                int maxScore = -1, minScore = 100000;
                String minName ="",maxName="";
                for(int i=0;i<batno;i++){
                    DataSnapshot ds = dataSnapshot.child("Bat").child(Integer.toString(i));
                    Log.d(TAG,ds.getValue().toString());
                    String pid = ds.child("PID").getValue().toString();
                    int sc = ((Long) ds.child("Score").getValue()).intValue();
                    teamScore += sc;
                    if(sc>maxScore){
                        maxScore = sc;
                        ds = data.child("PLAYERS").child(pid);
                        maxName = (String)ds.child("Name").getValue();
                    }
                    if(sc<minScore){
                        minScore = sc;
                        ds = data.child("PLAYERS").child(pid);
                        minName = (String)ds.child("Name").getValue();
                    }



                }

                for(int i=0;i<bolno;i++){
                    DataSnapshot ds = dataSnapshot.child("Bowl").child(Integer.toString(i));
                    Players player = new Players();
                    String pid = ds.child("PID").getValue().toString();
                    int sc = ((Long) ds.child("Score").getValue()).intValue();
                    teamScore += sc;
                    if(sc>maxScore){
                        maxScore = sc;
                        ds = data.child("PLAYERS").child(pid);
                        maxName = (String)ds.child("Name").getValue();
                    }
                    if(sc<minScore){
                        minScore = sc;
                        ds = data.child("PLAYERS").child(pid);
                        minName = (String)ds.child("Name").getValue();
                    }

                }
                // }
                // if(wktsel>0 && wkts.size()<wktsel){
                for(int i=0;i<1;i++){
                    DataSnapshot ds = dataSnapshot.child("Wkt").child(Integer.toString(i));
                    Players player = new Players();
                    String pid = ds.child("PID").getValue().toString();
                    int sc = ((Long) ds.child("Score").getValue()).intValue();
                    teamScore += sc;
                    if(sc>maxScore){
                        maxScore = sc;
                        ds = data.child("PLAYERS").child(pid);
                        maxName = (String)ds.child("Name").getValue();
                    }
                    if(sc<minScore){
                        minScore = sc;
                        ds = data.child("PLAYERS").child(pid);
                        minName = (String)ds.child("Name").getValue();
                    }

                }
                // }
                // if(allsel>0 && alls.size()<allsel){
                for(int i=0;i<allno;i++){
                    DataSnapshot ds = dataSnapshot.child("All").child(Integer.toString(i));
                    Players player = new Players();
                    String pid = ds.child("PID").getValue().toString();
                    int sc = ((Long) ds.child("Score").getValue()).intValue();
                    teamScore += sc;
                    if(sc>maxScore){
                        maxScore = sc;
                        ds = data.child("PLAYERS").child(pid);
                        maxName = (String)ds.child("Name").getValue();
                    }
                    if(sc<minScore){
                        minScore = sc;
                        ds = data.child("PLAYERS").child(pid);
                        minName = (String)ds.child("Name").getValue();
                    }

                }
                DataSnapshot dds = dataSnapshot.child("Scores");
                int j = 0;
                for(DataSnapshot dss: dds.getChildren()){
                    barEntries.add(new BarEntry(j,Float.parseFloat(dss.getValue().toString())));
                    labels.add("Match "+Integer.toString(++j));

                }
                dataSet = new BarDataSet(barEntries, "Points");
                barData = new BarData(dataSet);
                barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
                YAxis y = barChart.getAxisLeft();
                y.setLabelCount(5);
                y.setAxisMaximum(250);
                y.setAxisMinimum(0);
                YAxis rightYAxis = barChart.getAxisRight();
                rightYAxis.setEnabled(false);
                dataSet.setDrawValues(true);
                barChart.setDescription(null);
                barChart.setDrawGridBackground(false);
                XAxis xAxis = barChart.getXAxis();
                xAxis.setGranularity(1f);
                xAxis.setGranularityEnabled(true);
                barChart.animateY(1000);
                barChart.setDrawValueAboveBar(true);
                barChart.setData(barData);
                barChart.invalidate();
                Log.d("bar",Integer.toString(labels.size()));
                //dataSet.setColors(ColorTemplate.COLORFUL_COLORS);


                barChart.setData(barData);
                barChart.invalidate();




               /* }
                if(k==0){
                    k=1;
                    dref.child("USERS").child(userId).child("Foreign").setValue(foreign);
                    dref.child("USERS").child(userId).child("Price").setValue(price);
                }

                mone.setText(Integer.toString(money-price)+"M$");
                */
                dref.child("USERS").child(userId).child("Score").setValue(teamScore);
                point.setText("Points: "+Integer.toString(teamScore));
                bestPlayer.setText(maxName +"   Points: "+ Integer.toString(maxScore));
                worstPlayer.setText(minName+"   Points: "+Integer.toString(minScore));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        dref.addValueEventListener(mListener);
    }

    @Override
    public void onPause() {
        if (mListener != null && dref!=null) {
            dref.removeEventListener(mListener);
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        if (mListener != null && dref!=null) {
            dref.removeEventListener(mListener);
        }
        super.onStop();
    }
}

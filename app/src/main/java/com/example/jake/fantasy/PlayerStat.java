package com.example.jake.fantasy;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.jar.Attributes;

public class PlayerStat extends AppCompatActivity {
    ListView listView;
    TextView name,team,role,score;
    ImageView image;
    String url;
    int matcht,ball,runt,matchl,runl,over,wkt;
    int totMatch;
    String roles;
    DatabaseReference dref;
    ValueEventListener mListener;
    String pid;BarChart barChart;
    ArrayList <BarEntry> barEntries;
    BarDataSet dataSet;
    ArrayList<String> labels;
    BarData barData;
    int how;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_stat);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Player Stat");
        listView = findViewById(R.id.statList);
        image = findViewById(R.id.playerpic);
        name = findViewById(R.id.statName);
        team = findViewById(R.id.statTeam);
        role = findViewById(R.id.statRole);
        score = findViewById(R.id.statScore);
        barChart = findViewById(R.id.playerBar);
        barEntries = new ArrayList<>();
        labels = new ArrayList<>();
        pid=getIntent().getStringExtra("PlayerId");

        dref = FirebaseDatabase.getInstance().getReference();
        //DatabaseReference ddref = dref.child("Players")
        mListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot ds = dataSnapshot.child("PLAYERS").child(pid);
                roles = ds.child("Role").getValue().toString();
                name.setText(ds.child("Name").getValue().toString());
                team.setText(ds.child("Team").getValue().toString());
                url = ds.child("ImageURL").getValue().toString();
                loadimage(url,image);
                role.setText(roles);
                score.setText("Total Points: "+ds.child("TotScore").getValue().toString());
                int j = 0;
                ArrayList<Float> scoresForBar = new ArrayList<>();
                if(!roles.equals("Bowl")) {
                    matcht = Integer.parseInt(ds.child("BatPerform").child("Match").getValue().toString());
                    runt = Integer.parseInt(ds.child("BatPerform").child("Run").getValue().toString());
                    ball = Integer.parseInt(ds.child("BatPerform").child("Ball").getValue().toString());

                    for(DataSnapshot dss : ds.child("BatScores").getChildren()){
                        scoresForBar.add(Float.parseFloat(dss.child("Score").getValue().toString()));
                        j++;
                    }
                }
                if(roles.equals("Bowl") || roles.equals("All")){
                    matchl = Integer.parseInt(ds.child("BowlPerform").child("Match").getValue().toString());
                    runl = Integer.parseInt(ds.child("BowlPerform").child("Run").getValue().toString());
                    over = Integer.parseInt(ds.child("BowlPerform").child("Over").getValue().toString());
                    wkt = Integer.parseInt(ds.child("BowlPerform").child("Wkt").getValue().toString());
                    j = 0;
                    for(DataSnapshot dss : ds.child("BowlScores").getChildren()){
                        if(j<scoresForBar.size()){
                            scoresForBar.set(j,scoresForBar.get(j)+Float.parseFloat(dss.child("Score").getValue().toString()));
                        }
                        else{
                            scoresForBar.add(Float.parseFloat(dss.child("Score").getValue().toString()));

                        }
                        j++;
                    }
                }
                if(roles.equals("All")){
                    how = 2;
                }
                else how = 1;
                for(int i=0;i<scoresForBar.size();i++){

                    barEntries.add(new BarEntry(i,scoresForBar.get(i)));
                    labels.add("Match "+Integer.toString(i+1));
                }
                dataSet = new BarDataSet(barEntries, "Points");
                barData = new BarData(dataSet);
                barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
                YAxis y = barChart.getAxisLeft();
                y.setLabelCount(5);
                y.setAxisMaximum(150);
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
                generateList();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        dref.addValueEventListener(mListener);

    }

    void loadimage(String url,ImageView imageView){
        Picasso.with(PlayerStat.this).load(url).placeholder(R.drawable.anon).error(R.drawable.anon)
                .into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }
    void generateList(){
        CustomAdapter custopmAdapter = new CustomAdapter();

        listView.setAdapter(custopmAdapter);
    }

    class CustomAdapter extends BaseAdapter {


        CustomAdapter(){

        }
        @Override
        public int getCount() {
            return how;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.stats_item,null);
            TextView type = view.findViewById(R.id.statType);
            TextView vMatch = view.findViewById(R.id.statMatch);
            TextView vrun= view.findViewById(R.id.statRun);
            TextView vball = view.findViewById(R.id.statBall);
            TextView vavg = view.findViewById(R.id.statAvg);
            TextView vstrate = view.findViewById(R.id.statStRate);
            TextView vMatchv = view.findViewById(R.id.statMatchV);
            TextView vrunv= view.findViewById(R.id.statRunV);
            TextView vballv = view.findViewById(R.id.statBallV);
            TextView vavgv = view.findViewById(R.id.statAvgV);
            TextView vstratev = view.findViewById(R.id.statStRateV);
            vMatch.setText("Match");
            if((how == 1 && !roles.equals("Bowl"))||((how==2)&&(i==0))){
                type.setText("Batting Statistics");
                vMatchv.setText(Integer.toString(matcht));
                vrunv.setText(Integer.toString(runt));
                vballv.setText(Integer.toString(ball));
                float favg;
                float fsr ;
                if(matcht != 0) favg = (float)runt/matcht;
                else favg = 0;
                if(ball!=0) fsr = (((float)runt/ball))*100;
                else fsr = 0;

                vavgv.setText(String.format("%.1f", favg));
                vstratev.setText(String.format("%.1f", fsr));
                vrun.setText("Run");
                vball.setText("Ball");
                vavg.setText("Average");
                vstrate.setText("Strike Rate");
            }
            else{
                type.setText("Bowling Statistics");
                vMatchv.setText(Integer.toString(matchl));
                vrunv.setText(Integer.toString(over));
                vballv.setText(Integer.toString(runl));
                float fsr;
                if(over!= 0 ) fsr = ((float)(runl/over));
                else fsr = 0;
                vavgv.setText(Integer.toString(wkt));
                vstratev.setText(String.format("%.1f", fsr));
                vrun.setText("Over");
                vball.setText("Run");
                vavg.setText("Wickets");
                vstrate.setText("Econ Rate");
            }

            return view;
        }
    }
}

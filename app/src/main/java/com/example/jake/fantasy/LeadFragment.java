package com.example.jake.fantasy;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.min;

/**
 * Created by jake on 1/7/18.
 */

public class LeadFragment extends Fragment {
    private static final String TAG = "LeadFragment";
    DatabaseReference dref;
    ValueEventListener mListener;
    ArrayList <Leaders> leaders;
    ListView listView1,listView2;
    ArrayList <Players> players;
    String userId;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lead,container,false);

        listView1 = view.findViewById(R.id.leadTeamList);
        listView2 = view.findViewById(R.id.leadPlayerList);
        leaders = new ArrayList<>();
        players = new ArrayList<>();
        populateLeaders();
        userId=getActivity().getIntent().getStringExtra("userId");

        return view;

    }

    void populateLeaders(){
        dref = FirebaseDatabase.getInstance().getReference();
        //DatabaseReference ddref = dref.child("Players")
        mListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                leaders.clear();
                for (DataSnapshot ds : dataSnapshot.child("USERS").getChildren()){

                    int total = Integer.parseInt(ds.child("TotalSelected").getValue().toString());
                    if(total!=11) continue;
                    Leaders leader = new Leaders();
                    int totScore = 0;
                    int bat = Integer.parseInt(ds.child("BatsmenSel").getValue().toString());
                    for(int i=0;i<bat;i++){
                        totScore += ((Long) ds.child("Bat").child(Integer.toString(i)).child("Score").getValue()).intValue();
                    }
                    bat = Integer.parseInt(ds.child("BowlerSel").getValue().toString());
                    for(int i=0;i<bat;i++){
                        totScore += ((Long) ds.child("Bowl").child(Integer.toString(i)).child("Score").getValue()).intValue();
                    }
                    bat = Integer.parseInt(ds.child("WktKeeperSel").getValue().toString());
                    for(int i=0;i<bat;i++){
                        totScore += ((Long) ds.child("Wkt").child(Integer.toString(i)).child("Score").getValue()).intValue();
                    }
                    bat = Integer.parseInt(ds.child("AllrounderSel").getValue().toString());
                    for(int i=0;i<bat;i++){
                        totScore += ((Long) ds.child("All").child(Integer.toString(i)).child("Score").getValue()).intValue();
                    }
                    leader.setTeamName(ds.child("TeamName").getValue().toString());
                    leader.setOwner(ds.child("Name").getValue().toString());
                    leader.setUserId(ds.getKey());
                    leader.setPoints(totScore);
                    Log.d(TAG,leader.getUserId());
                    leaders.add(leader);
                    players.clear();
                    for(int i=0;i<=165;i++){
                        ds = dataSnapshot.child("PLAYERS").child(Integer.toString(i));
                        Players player = new Players();
                        player.setAge(Integer.parseInt((String) ds.child("Age").getValue()));
                        player.setCountry((String)ds.child("Country").getValue());
                        player.setName((String)ds.child("Name").getValue());
                        player.setRole((String)ds.child("Role").getValue());
                        player.setTeam((String)ds.child("Team").getValue());
                        player.setUrl((String)ds.child("ImageURL").getValue());
                        player.setTotScore(((Long) ds.child("TotScore").getValue()).intValue());
                        player.setPrice(((Long) ds.child("Price").getValue()).intValue());
                        player.setId(i);
                        Log.d("tot",Integer.toString(i)+ "  " +Integer.toString(player.getTotScore()));
                        players.add(player);

                    }

                }
                geterateList();

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        dref.addValueEventListener(mListener);

        //Log.d(TAG, "Dhuke");
        //while(players.size()<165);
        //Log.d(TAG, players.get(0).getName());


    }
    void geterateList(){
        Collections.sort(leaders, new Comparator<Leaders>() {
            @Override
            public int compare(Leaders o1, Leaders o2) {
                return o2.getPoints()-o1.getPoints();
            }
        });
        Collections.sort(players, new Comparator<Players>() {
            @Override
            public int compare(Players o1, Players o2) {
                return o2.getTotScore()-o1.getTotScore();
            }
        });
        for(int i=0;i<leaders.size();i++){
            if(leaders.get(i).getUserId().equals(userId)){
                dref.child("USERS").child(userId).child("Rank").setValue(i+1);
            }
        }
        CustopmAdapter custopmAdapter = new CustopmAdapter(1);

        //Log.d(TAG, Integer.toString(leaders.size()));

        listView1.setAdapter(custopmAdapter);
        CustopmAdapter custopmAdapter1 = new CustopmAdapter(2);


        listView2.setAdapter(custopmAdapter1);
        Utility.setListViewHeightBasedOnChildren(listView1);
        Utility.setListViewHeightBasedOnChildren(listView2);
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent startIntent = new Intent(getActivity(),PlayerStat.class);
                startIntent.putExtra("PlayerId",Integer.toString(players.get(position).getId()));
                //startIntent.putExtra("Money",Integer.toString(money));
                startActivity(startIntent);
            }
        });
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent startIntent = new Intent(getActivity(),OtherTeams.class);
                startIntent.putExtra("UserId",leaders.get(position).getUserId());
                //startIntent.putExtra("Money",Integer.toString(money));
                startActivity(startIntent);
            }
        });
    }


    class CustopmAdapter extends BaseAdapter {

        int c;

        public CustopmAdapter(int c) {
            this.c = c;
        }

        @Override
        public int getCount() {
            return 5;
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
            view = getLayoutInflater().inflate(R.layout.leaders_layout,null);
            TextView teamname = view.findViewById(R.id.leadName);
            TextView rank= view.findViewById(R.id.rankTno);
            TextView owner = view.findViewById(R.id.leadOwner);
            TextView points = view.findViewById(R.id.leadPno);
            if(c==1) {
                if(i>=leaders.size()){
                    teamname.setText("No Team");
                    rank.setText(Integer.toString(i+1));
                    owner.setText("");
                    points.setText("");
                }
                else {
                    teamname.setText(leaders.get(i).getTeamName());
                    rank.setText(Integer.toString(i+1));
                    owner.setText("Owner: " + leaders.get(i).getOwner());
                    points.setText(Integer.toString(leaders.get(i).getPoints()));
                }
            }
            else{
                teamname.setText(players.get(i).getName());
                rank.setText(Integer.toString(i+1));
                owner.setText(players.get(i).getTeam());
                points.setText(Integer.toString(players.get(i).getTotScore()));
            }

            return view;
        }
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

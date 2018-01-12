package com.example.jake.fantasy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by jake on 1/7/18.
 */

public class FixtFragment extends Fragment {
    private static final String TAG = "FixtFragment";
    DatabaseReference dref;
    ArrayList <String> teamsL,matchnoL,venueL,timeL;
    ListView listView;
    ValueEventListener mListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fixt,container,false);
        teamsL = new ArrayList<>();
        matchnoL = new ArrayList<>();
        venueL = new ArrayList<>();
        timeL = new ArrayList<>();
        listView = view.findViewById(R.id.matchList);
        populateMatches();

        return view;

    }

    void populateMatches(){
        dref = FirebaseDatabase.getInstance().getReference();
        //DatabaseReference ddref = dref.child("Players")
        mListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(int i=0;i<=41;i++){
                    DataSnapshot ds = dataSnapshot.child("MATCHES").child(Integer.toString(i));
                    String team1 = (String)ds.child("Team1").getValue();
                    String team2 = (String)ds.child("Team2").getValue();
                    teamsL.add(team1+" vs "+team2);
                    venueL.add((String)ds.child("Venue").getValue());
                    matchnoL.add(ds.child("Id").getValue().toString());
                    String times = (String)ds.child("Time").getValue();
                    String dates = (String)ds.child("Date").getValue();
                    timeL.add("Date: "+dates+" Time: "+times);

                }
                geterateList();

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        dref.addValueEventListener(mListener);

        Log.d(TAG, "Dhuke");
        //while(players.size()<165);
        //Log.d(TAG, players.get(0).getName());

    }
    void geterateList(){
        CustopmAdapter custopmAdapter = new CustopmAdapter();

        Log.d(TAG, "geege");
        listView.setAdapter(custopmAdapter);
    }
    class CustopmAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 42;
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
            view = getLayoutInflater().inflate(R.layout.fixture_items,null);
            TextView teams = view.findViewById(R.id.teams);
            TextView matchno= view.findViewById(R.id.matchNo);
            TextView venue = view.findViewById(R.id.venue);
            TextView time = view.findViewById(R.id.time);

            teams.setText(teamsL.get(i));
            time.setText(timeL.get(i));
            venue.setText("Venue: "+venueL.get(i));
            matchno.setText(matchnoL.get(i));

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

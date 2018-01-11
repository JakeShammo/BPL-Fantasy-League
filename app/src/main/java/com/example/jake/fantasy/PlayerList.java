package com.example.jake.fantasy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
 * Created by jake on 1/10/18.
 */

public class PlayerList extends AppCompatActivity {
    DatabaseReference dref;
    ArrayList <Players> players;
    ListView listView;

    public ProgressDialog mProgressDialog;
    private static final String TAG = "playerlist";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Log.d(TAG, "Ekhane");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Creating Team");
        /*String role=getIntent().getStringExtra("Role");
        String country=getIntent().getStringExtra("Country");
        String name=getIntent().getStringExtra("Name");
        String maxPrice=getIntent().getStringExtra("MaxPrice");
        String minPrice=getIntent().getStringExtra("MinPrice");
        String sortBy=getIntent().getStringExtra("SortBy");
        String userId=getIntent().getStringExtra("UserId");*/
        listView = findViewById(R.id.playerList);
        showProgressDialog();
        populatePlayers();

    }

    void populatePlayers(){
        players = new ArrayList<>();
        dref = FirebaseDatabase.getInstance().getReference().child("PLAYERS");

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d(TAG, "Dhuke");
                for(int i=0;i<=165;i++){
                    DataSnapshot ds = dataSnapshot.child(Integer.toString(i));
                    Players player = new Players();
                    player.setAge(Integer.parseInt((String) ds.child("Age").getValue()));
                    player.setCountry((String)ds.child("Country").getValue());
                    player.setName((String)ds.child("Name").getValue());
                    player.setRole((String)ds.child("Role").getValue());
                    player.setTeam((String)ds.child("Team").getValue());
                    player.setTotScore(((Long) ds.child("TotScore").getValue()).intValue());
                    player.setPrice(((Long) ds.child("Price").getValue()).intValue());
                    player.setId(((Long) ds.child("PlayerId").getValue()).intValue());
                    players.add(player);

                }
                generateListView();

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Log.d(TAG, "Dhuke");
        //while(players.size()<165);
        //Log.d(TAG, players.get(0).getName());

    }
    void generateListView(){

        Log.d(TAG, "geege");
        CustopmAdapter custopmAdapter = new CustopmAdapter();

        Log.d(TAG, "geege");
        listView.setAdapter(custopmAdapter);
        hideProgressDialog();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Log.d(TAG,"Ashe");
                Log.d(TAG, players.get(position).getName());
            }
        });

    }
    class CustopmAdapter extends BaseAdapter{

        int id;
        @Override
        public int getCount() {
            return players.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return id;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.custom_layout,null);
            ImageView image = view.findViewById(R.id.listIm);
            TextView name = view.findViewById(R.id.pName);
            TextView roll= view.findViewById(R.id.pRole);
            TextView price = view.findViewById(R.id.pPrice);
            TextView team = view.findViewById(R.id.pTeam);
            TextView prices = view.findViewById(R.id.price);

            image.setImageResource(R.drawable.anon);
            name.setText(players.get(i).getName());
            roll.setText(players.get(i).getRole());
            team.setText(players.get(i).getTeam());
            prices.setText(Integer.toString(players.get(i).getPrice()));
            price.setText("Price");


            id = players.get(i).getId();

            return view;
        }
    }

    public void showProgressDialog() {

        if (mProgressDialog == null) {

            mProgressDialog = new ProgressDialog(this);

            mProgressDialog.setMessage("Generating Player List");

            mProgressDialog.setIndeterminate(true);

        }



        mProgressDialog.show();

    }



    public void hideProgressDialog() {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {

            mProgressDialog.dismiss();

        }

    }
}

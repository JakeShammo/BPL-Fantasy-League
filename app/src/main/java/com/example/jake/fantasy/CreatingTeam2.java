package com.example.jake.fantasy;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class CreatingTeam2 extends AppCompatActivity {

    int batno,bolno,allno,batsel,bolsel,wktsel,allsel,money,total;
    DatabaseReference dref;
    TextView plse,mone,tmName;
    ArrayList<Players> bats,bowls,alls,wkts;
    ListView batL,bolL,allL,wktL;
    String userId;
    public ProgressDialog mProgressDialog;
    private static final String TAG = "playerlist";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_team2);
        plse = findViewById(R.id.plSe);
        mone = findViewById(R.id.mon);
        batL = findViewById(R.id.batList);
        bolL = findViewById(R.id.bowlList);
        allL = findViewById(R.id.allList);
        wktL = findViewById(R.id.wktList);
        userId=getIntent().getStringExtra("userId");
        showProgressDialog();
        bats = new ArrayList<>();
        bowls = new ArrayList<>();
        wkts = new ArrayList<>();
        alls = new ArrayList<>();
        getData();
    }
    void getData(){
        dref = FirebaseDatabase.getInstance().getReference().child("USERS").child(userId);
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //TextView balance = findViewById(R.id.total_balance_value);
                money = Integer.parseInt(dataSnapshot.child("Balance").getValue().toString());
                mone.setText(Integer.toString(money)+"M$");
                total = Integer.parseInt(dataSnapshot.child("TotalSelected").getValue().toString());
                plse.setText(Integer.toString(total)+"/11");

                batno = Integer.parseInt(dataSnapshot.child("Batsmen").getValue().toString());
                bolno  = Integer.parseInt(dataSnapshot.child("Bowler").getValue().toString());
                allno = Integer.parseInt(dataSnapshot.child("Allrounder").getValue().toString());
                batsel = Integer.parseInt(dataSnapshot.child("BatsmenSel").getValue().toString());
                bolsel = Integer.parseInt(dataSnapshot.child("BowlerSel").getValue().toString());
                wktsel = Integer.parseInt(dataSnapshot.child("WktKeeperSel").getValue().toString());
                allsel = Integer.parseInt(dataSnapshot.child("AllrounderSel").getValue().toString());

                //Log.d(TAG,"dsded");
                //Log.d(TAG,Integer.toString(batsel));
                if(batsel>0){
                    for(int i=0;i<batsel;i++){
                        DataSnapshot ds = dataSnapshot.child("BatsmenL").child(Integer.toString(i));
                        Players player = new Players();
                        player.setAge(Integer.parseInt((String) ds.child("Age").getValue()));
                        player.setCountry(dataSnapshot.child("Country").getValue().toString());
                        player.setName(dataSnapshot.child("Name").getValue().toString());
                        player.setRole(dataSnapshot.child("Role").getValue().toString());
                        player.setTeam(dataSnapshot.child("Team").getValue().toString());
                        player.setTotScore(Integer.parseInt(dataSnapshot.child("TotScore").getValue().toString()));
                        player.setPrice(Integer.parseInt(dataSnapshot.child("Price").getValue().toString()));
                        player.setId(Integer.parseInt(dataSnapshot.child("PlayerId").getValue().toString()));
                        bats.add(player);

                    }
                }
                if(batsel>0){
                    for(int i=0;i<batsel;i++){
                        DataSnapshot ds = dataSnapshot.child("BatsmenL").child(Integer.toString(i));
                        Players player = new Players();
                        player.setAge(Integer.parseInt((String) ds.child("Age").getValue()));
                        player.setCountry(dataSnapshot.child("Country").getValue().toString());
                        player.setName(dataSnapshot.child("Name").getValue().toString());
                        player.setRole(dataSnapshot.child("Role").getValue().toString());
                        player.setTeam(dataSnapshot.child("Team").getValue().toString());
                        player.setTotScore(Integer.parseInt(dataSnapshot.child("TotScore").getValue().toString()));
                        player.setPrice(Integer.parseInt(dataSnapshot.child("Price").getValue().toString()));
                        player.setId(Integer.parseInt(dataSnapshot.child("PlayerId").getValue().toString()));
                        bats.add(player);

                    }
                }
                if(bolsel>0){
                    for(int i=0;i<bolsel;i++){
                        DataSnapshot ds = dataSnapshot.child("BowlerL").child(Integer.toString(i));
                        Players player = new Players();
                        player.setAge(Integer.parseInt((String) ds.child("Age").getValue()));
                        player.setCountry(dataSnapshot.child("Country").getValue().toString());
                        player.setName(dataSnapshot.child("Name").getValue().toString());
                        player.setRole(dataSnapshot.child("Role").getValue().toString());
                        player.setTeam(dataSnapshot.child("Team").getValue().toString());
                        player.setTotScore(Integer.parseInt(dataSnapshot.child("TotScore").getValue().toString()));
                        player.setPrice(Integer.parseInt(dataSnapshot.child("Price").getValue().toString()));
                        player.setId(Integer.parseInt(dataSnapshot.child("PlayerId").getValue().toString()));
                        bowls.add(player);

                    }
                }
                if(wktsel>0){
                    for(int i=0;i<wktsel;i++){
                        DataSnapshot ds = dataSnapshot.child("WktkeeperL").child(Integer.toString(i));
                        Players player = new Players();
                        player.setAge(Integer.parseInt((String) ds.child("Age").getValue()));
                        player.setCountry(dataSnapshot.child("Country").getValue().toString());
                        player.setName(dataSnapshot.child("Name").getValue().toString());
                        player.setRole(dataSnapshot.child("Role").getValue().toString());
                        player.setTeam(dataSnapshot.child("Team").getValue().toString());
                        player.setTotScore(Integer.parseInt(dataSnapshot.child("TotScore").getValue().toString()));
                        player.setPrice(Integer.parseInt(dataSnapshot.child("Price").getValue().toString()));
                        player.setId(Integer.parseInt(dataSnapshot.child("PlayerId").getValue().toString()));
                        wkts.add(player);

                    }
                }
                if(allsel>0){
                    for(int i=0;i<allsel;i++){
                        DataSnapshot ds = dataSnapshot.child("AllrounderL").child(Integer.toString(i));
                        Players player = new Players();
                        player.setAge(Integer.parseInt((String) ds.child("Age").getValue()));
                        player.setCountry(dataSnapshot.child("Country").getValue().toString());
                        player.setName(dataSnapshot.child("Name").getValue().toString());
                        player.setRole(dataSnapshot.child("Role").getValue().toString());
                        player.setTeam(dataSnapshot.child("Team").getValue().toString());
                        player.setTotScore(Integer.parseInt(dataSnapshot.child("TotScore").getValue().toString()));
                        player.setPrice(Integer.parseInt(dataSnapshot.child("Price").getValue().toString()));
                        player.setId(Integer.parseInt(dataSnapshot.child("PlayerId").getValue().toString()));
                        alls.add(player);

                    }
                }

                generateLists();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    void generateLists(){

        //Log.d(TAG,Integer.toString(bats.size()));
        CustomAdapter batAdapter = new CustomAdapter(bats,batno);
        CustomAdapter bolAdapter = new CustomAdapter(bowls,bolno);
        CustomAdapter wktAdapter = new CustomAdapter(wkts,1);
        CustomAdapter allAdapter = new CustomAdapter(alls,allno);

        batL.setAdapter(batAdapter);
        bolL.setAdapter(bolAdapter);
        wktL.setAdapter(wktAdapter);
        allL.setAdapter(allAdapter);
        Utility.setListViewHeightBasedOnChildren(batL);
        Utility.setListViewHeightBasedOnChildren(wktL);
        Utility.setListViewHeightBasedOnChildren(allL);
        Utility.setListViewHeightBasedOnChildren(bolL);
        hideProgressDialog();
        //Log.d(TAG, "Ekhane1");
    }

    class CustomAdapter extends BaseAdapter {

        ArrayList<Players> pplayers;
        int count;
        CustomAdapter(ArrayList<Players> players, int no){
            count = no;
            pplayers = players;
        }
        @Override
        public int getCount() {
            return count;
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
            view = getLayoutInflater().inflate(R.layout.custom_layout,null);
            ImageView image = view.findViewById(R.id.listIm);
            TextView name = view.findViewById(R.id.pName);
            TextView roll= view.findViewById(R.id.pRole);
            TextView price = view.findViewById(R.id.pPrice);
            TextView team = view.findViewById(R.id.pTeam);
            TextView prices = view.findViewById(R.id.price);

            image.setImageResource(R.drawable.anon);
            price.setText("Price");
            //Log.d(TAG,Integer.toString(pplayers.size()));
            //Log.d(TAG,Integer.toString(i));
            if(i<pplayers.size()) {
                name.setText(pplayers.get(i).getName());
                roll.setText(pplayers.get(i).getRole());
                team.setText(pplayers.get(i).getTeam());
                prices.setText(Integer.toString(pplayers.get(i).getPrice()));
             }
             else{
                name.setText("Add Player");
                roll.setText("");
                team.setText("");
                prices.setText("");
                price.setText("");
            }
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

package com.example.jake.fantasy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OtherTeams extends AppCompatActivity {
    private static final String TAG = "dddd";
    int batno,bolno,allno,batsel,bolsel,wktsel,allsel,money,total,foreign = 0,price=0,k=0;
    DatabaseReference dref;
    TextView tmMotto,tmName,point;
    ArrayList<Players> bats,bowls,alls,wkts;
    ListView batL,bolL,allL,wktL;
    String userId,teamName,teamMotto;
    Button stats;

    ValueEventListener mListener;
    public ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_teams);
        total = 0;
        foreign = 0;
        tmName = findViewById(R.id.nameteam1);
        tmMotto = findViewById(R.id.Motto1);
        userId= getIntent().getStringExtra("UserId");
        //showProgressDialog();
        bats = new ArrayList<>();
        bowls = new ArrayList<>();
        wkts = new ArrayList<>();
        alls = new ArrayList<>();
        batL = findViewById(R.id.batList2);
        bolL = findViewById(R.id.bowlList2);
        allL = findViewById(R.id.allList2);
        wktL = findViewById(R.id.wktList2);
        point = findViewById(R.id.points1);
        stats = findViewById(R.id.viewStats1);
        stats.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //signIn.setBackgroundColor(Color.GRAY);
                Intent startIntent = new Intent(OtherTeams.this,TeamStats.class);
                startIntent.putExtra("userId",userId);

                startActivity(startIntent);
            }
        });
        //submit = findViewById(R.id.submit);
        showProgressDialog();
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
                // batsel = Integer.parseInt(dataSnapshot.child("BatsmenSel").getValue().toString());
                //bolsel = Integer.parseInt(dataSnapshot.child("BowlerSel").getValue().toString());
                //wktsel = Integer.parseInt(dataSnapshot.child("WktKeeperSel").getValue().toString());
                // allsel = Integer.parseInt(dataSnapshot.child("AllrounderSel").getValue().toString());
                //foreign = Integer.parseInt(dataSnapshot.child("Foreign").getValue().toString());
                //total = batsel + allsel + wktsel + bolsel;

                //plse.setText(Integer.toString(total)+"/11");
                //dref.child("USERS").child(userId).child("TotalSelected").setValue(Integer.toString(total));
                //Log.d(TAG,"dsded");
                //Log.d(TAG,Integer.toString(batsel));

                Log.d(TAG,"asda");
                int teamScore = 0;
                //if(batsel>0 && bats.size()<batsel){
                for(int i=0;i<batno;i++){
                    Players player = new Players();
                    DataSnapshot ds = dataSnapshot.child("Bat").child(Integer.toString(i));
                    //Log.d(TAG,ds.getValue().toString());
                    String pid = ds.child("PID").getValue().toString();
                    player.setTotScore(((Long) ds.child("Score").getValue()).intValue());
                    teamScore += player.getTotScore();
                    ds = data.child("PLAYERS").child(pid);
                    player.setAge(Integer.parseInt((String) ds.child("Age").getValue()));
                    player.setCountry((String)ds.child("Country").getValue());
                    player.setName((String)ds.child("Name").getValue());
                    player.setRole((String)ds.child("Role").getValue());
                    player.setTeam((String)ds.child("Team").getValue());
                    player.setUrl((String)ds.child("ImageURL").getValue());
                    player.setPrice(((Long) ds.child("Price").getValue()).intValue());
                    player.setId(Integer.parseInt(pid));
                    //if(!player.getCountry().startsWith("Bangla")) foreign++;
                    //price += player.getPrice();
                    bats.add(player);

                }
                //}
                // if(bolsel>0 && bowls.size()<bolsel){

                for(int i=0;i<bolno;i++){
                    DataSnapshot ds = dataSnapshot.child("Bowl").child(Integer.toString(i));
                    Players player = new Players();
                    String pid = ds.child("PID").getValue().toString();
                    player.setTotScore(((Long) ds.child("Score").getValue()).intValue());
                    teamScore += player.getTotScore();
                    ds = data.child("PLAYERS").child(pid);

                    player.setAge(Integer.parseInt((String) ds.child("Age").getValue()));
                    player.setCountry((String)ds.child("Country").getValue());
                    player.setName((String)ds.child("Name").getValue());
                    player.setRole((String)ds.child("Role").getValue());
                    player.setTeam((String)ds.child("Team").getValue());
                    player.setUrl((String)ds.child("ImageURL").getValue());
                    player.setPrice(((Long) ds.child("Price").getValue()).intValue());
                    player.setId(Integer.parseInt(pid));
                    //if(!player.getCountry().startsWith("Bangla")) foreign++;
                    //price += player.getPrice();
                    bowls.add(player);

                }
                // }
                // if(wktsel>0 && wkts.size()<wktsel){
                for(int i=0;i<1;i++){
                    DataSnapshot ds = dataSnapshot.child("Wkt").child(Integer.toString(i));
                    Players player = new Players();
                    String pid = ds.child("PID").getValue().toString();
                    player.setTotScore(((Long) ds.child("Score").getValue()).intValue());
                    teamScore += player.getTotScore();
                    ds = data.child("PLAYERS").child(pid);
                    player.setAge(Integer.parseInt((String) ds.child("Age").getValue()));
                    player.setCountry((String)ds.child("Country").getValue());
                    player.setName((String)ds.child("Name").getValue());
                    player.setRole((String)ds.child("Role").getValue());
                    player.setTeam((String)ds.child("Team").getValue());
                    player.setUrl((String)ds.child("ImageURL").getValue());
                    player.setPrice(((Long) ds.child("Price").getValue()).intValue());
                    player.setId(Integer.parseInt(pid));
                    //if(!player.getCountry().startsWith("Bangla")) foreign++;
                    // price += player.getPrice();
                    wkts.add(player);

                }
                // }
                // if(allsel>0 && alls.size()<allsel){
                for(int i=0;i<allno;i++){
                    DataSnapshot ds = dataSnapshot.child("All").child(Integer.toString(i));
                    Players player = new Players();
                    String pid = ds.child("PID").getValue().toString();
                    player.setTotScore(((Long) ds.child("Score").getValue()).intValue());
                    teamScore += player.getTotScore();
                    ds = data.child("PLAYERS").child(pid);
                    player.setAge(Integer.parseInt((String) ds.child("Age").getValue()));
                    player.setCountry((String)ds.child("Country").getValue());
                    player.setName((String)ds.child("Name").getValue());
                    player.setRole((String)ds.child("Role").getValue());
                    player.setTeam((String)ds.child("Team").getValue());
                    player.setUrl((String)ds.child("ImageURL").getValue());
                    player.setPrice(((Long) ds.child("Price").getValue()).intValue());
                    player.setId(Integer.parseInt(pid));
                    // if(!player.getCountry().startsWith("Bangla")) foreign++;
                    // price += player.getPrice();
                    alls.add(player);

                }


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


                generateLists();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        dref.addValueEventListener(mListener);
    }

    void generateLists(){
        CustomAdapter batAdapter = new CustomAdapter(bats,batno);
        CustomAdapter bolAdapter = new CustomAdapter(bowls,bolno);
        CustomAdapter wktAdapter = new CustomAdapter(wkts,1);
        CustomAdapter allAdapter = new CustomAdapter(alls,allno);

        batL.setAdapter(batAdapter);
        bolL.setAdapter(bolAdapter);
        wktL.setAdapter(wktAdapter);
        allL.setAdapter(allAdapter);

        Log.d(TAG,"Ekhane");
        Utility.setListViewHeightBasedOnChildren(batL);
        Utility.setListViewHeightBasedOnChildren(wktL);
        Utility.setListViewHeightBasedOnChildren(allL);
        Utility.setListViewHeightBasedOnChildren(bolL);
        batL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent startIntent = new Intent(OtherTeams.this,PlayerStat.class);
                startIntent.putExtra("PlayerId",Integer.toString(bats.get(position).getId()));
                //startIntent.putExtra("Money",Integer.toString(money));
                startActivity(startIntent);
            }
        });
        wktL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent startIntent = new Intent(OtherTeams.this,PlayerStat.class);
                startIntent.putExtra("PlayerId",Integer.toString(wkts.get(position).getId()));
                //startIntent.putExtra("Money",Integer.toString(money));
                startActivity(startIntent);
            }
        });
        bolL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent startIntent = new Intent(OtherTeams.this,PlayerStat.class);
                startIntent.putExtra("PlayerId",Integer.toString(bowls.get(position).getId()));
                //startIntent.putExtra("Money",Integer.toString(money));
                startActivity(startIntent);
            }
        });
        allL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent startIntent = new Intent(OtherTeams.this,PlayerStat.class);
                startIntent.putExtra("PlayerId",Integer.toString(alls.get(position).getId()));
                //startIntent.putExtra("Money",Integer.toString(money));
                startActivity(startIntent);
            }
        });

        hideProgressDialog();
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

            String url = pplayers.get(i).getUrl();
            loadimage(url,image);
            price.setText("Point");
            name.setText(pplayers.get(i).getName());
            roll.setText(pplayers.get(i).getRole());
            team.setText(pplayers.get(i).getTeam());
            prices.setText(Integer.toString(pplayers.get(i).getTotScore()));

            return view;
        }
    }

    void loadimage(String url,ImageView imageView){
        Picasso.with(this).load(url).placeholder(R.drawable.anon).error(R.drawable.anon)
                .into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    public void showProgressDialog() {

        if (mProgressDialog == null) {

            mProgressDialog = new ProgressDialog(OtherTeams.this);

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

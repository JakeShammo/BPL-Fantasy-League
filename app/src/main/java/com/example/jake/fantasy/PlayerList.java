package com.example.jake.fantasy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jake on 1/10/18.
 */

public class PlayerList extends AppCompatActivity {
    DatabaseReference dref;
    ArrayList <Players> players;
    ArrayList <Players> filtered;
    ListView listView;
    int userMon,fore;
    ArrayList<Integer> pids = new ArrayList<>();
    String role,country,name,maxPrice,minPrice,userId,team,posi,one;
    int tot,par;
    Button filter;
    public ProgressDialog mProgressDialog;
    ValueEventListener mListener;
    private static final String TAG = "filter";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Log.d(TAG, "Ekhane");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Select Squad");

        Log.d(TAG,"vorse");
        role=getIntent().getStringExtra("Role");
        country=getIntent().getStringExtra("Country");
        name=getIntent().getStringExtra("Name");
        team=getIntent().getStringExtra("Team");
        maxPrice=getIntent().getStringExtra("MaxPrice");
        minPrice=getIntent().getStringExtra("MinPrice");
        //String sortBy=getIntent().getStringExtra("SortBy");
        userId=getIntent().getStringExtra("UserId");
        Log.d(TAG,"vorse");
        posi=getIntent().getStringExtra("Position");
        one = getIntent().getStringExtra("One");
        //fore = Integer.parseInt(getIntent().getStringExtra("Fore"));
        //posi = "0";
        Log.d(TAG,"vorse");
        listView = findViewById(R.id.playerList);
        showProgressDialog();
        populatePlayers();
        filter = findViewById(R.id.filterP);

        filter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //signIn.setBackgroundColor(Color.GRAY);
                Intent startIntent = new Intent(PlayerList.this,FilterActivity.class);
                startIntent.putExtra("UserId",userId);
                startIntent.putExtra("One",one);
                startIntent.putExtra("Position",posi);
                startIntent.putExtra("Role",role);
                Log.d(TAG,"puts");
                startActivity(startIntent);
                Log.d(TAG,"puts");
            }
        });

    }

    void populatePlayers(){
        players = new ArrayList<>();
        dref = FirebaseDatabase.getInstance().getReference();
        //DatabaseReference ddref = dref.child("Players")
        mListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Log.d(TAG, "Dhuke");
                if(role.equals("Bat")) {
                    par = Integer.parseInt(dataSnapshot.child("USERS").child(userId).child("BatsmenSel").getValue().toString());

                }
                if(role.equals("Wkt"))
                    par = Integer.parseInt( dataSnapshot.child("USERS").child(userId).child("WktKeeperSel").getValue().toString());
                if(role.equals("Bowl"))
                    par = Integer.parseInt( dataSnapshot.child("USERS").child(userId).child("BowlerSel").getValue().toString());
                if(role.equals("All"))
                    par = Integer.parseInt( dataSnapshot.child("USERS").child(userId).child("AllrounderSel").getValue().toString());
                for(int i=0;i<par;i++){
                    pids.add(Integer.parseInt(dataSnapshot.child("USERS").child(userId).child(role).child(Integer.toString(i)).child("PID").getValue().toString())+1);
                    Log.d(TAG,Integer.toString(pids.get(i)));
                }
                userMon = Integer.parseInt( dataSnapshot.child("USERS").child(userId).child("Price").getValue().toString());
                fore = Integer.parseInt( dataSnapshot.child("USERS").child(userId).child("Foreign").getValue().toString());

                for(int i=0;i<=165;i++){
                     DataSnapshot ds = dataSnapshot.child("PLAYERS").child(Integer.toString(i));
                    Players player = new Players();
                    player.setAge(Integer.parseInt((String) ds.child("Age").getValue()));
                    player.setCountry((String)ds.child("Country").getValue());
                    //Log.d("cou", player.getCountry());
                    player.setName((String)ds.child("Name").getValue());
                    player.setRole((String)ds.child("Role").getValue());
                    player.setTeam((String)ds.child("Team").getValue());
                    player.setUrl((String)ds.child("ImageURL").getValue());
                    player.setTotScore(((Long) ds.child("TotScore").getValue()).intValue());
                    player.setPrice(((Long) ds.child("Price").getValue()).intValue());
                    player.setId(((Long) ds.child("PlayerId").getValue()).intValue());
                    players.add(player);

                }
                filterPlayers();
                generateListView();

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
    void filterPlayers(){
        filtered = new ArrayList<>();
        for(int i=0;i<players.size();i++){
            Players p = players.get(i);
            if((role.equals("Any") || role.equals(p.getRole()) || (role.equals("Bat") && p.getRole().equals("Wkt"))) && (name.equals("Any") || p.getName().toLowerCase().contains(name.toLowerCase()))&&
            (country.equals("Any") || p.getCountry().toLowerCase().contains(country.toLowerCase())) && (team.equals("Any") || p.getTeam().startsWith(team))
                    && ((maxPrice.equals("Any") || p.getPrice()<=Integer.parseInt(maxPrice))) &&
                    ((minPrice.equals("Any") || p.getPrice()>=Integer.parseInt(minPrice))) && (!pids.contains(p.getId())))
                filtered.add(p);

        }
    }
    void generateListView(){

        Log.d(TAG, "geege");
        CustopmAdapter custopmAdapter = new CustopmAdapter();

        Log.d(TAG, "geege");
        listView.setAdapter(custopmAdapter);
        hideProgressDialog();

        dref = FirebaseDatabase.getInstance().getReference();
        dref = dref.child("USERS").child(userId);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                int Pid = filtered.get(position).getId() - 1;
                Players pp = players.get(Pid);
                if(one.equals("Yes")&&pp.getPrice()+userMon>110){
                    Toast.makeText(PlayerList.this, "Price too high.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!pp.getCountry().startsWith("Bangla")){
                    if(fore==5) {
                        Toast.makeText(PlayerList.this, "Maximum 5 foreign players allowed",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
               // showProgressDialog(2);
                dref.child(role).child(posi).child("PID").setValue(Pid);
                dref.child(role).child(posi).child("Score").setValue(0);
                if(one.equals("Yes")){
                    if(role.equals("Bat")){
                        dref.child("BatsmenSel").setValue(Integer.toString(par+1));
                    }
                    if(role.equals("All")){
                        dref.child("AllrounderSel").setValue(Integer.toString(par+1));
                    }
                    if(role.equals("Bowl")){
                        dref.child("BowlerSel").setValue(Integer.toString(par+1));
                    }
                    if(role.equals("Wkt")){
                        dref.child("WktKeeperSel").setValue(Integer.toString(par+1));
                    }
                }
                Intent startIntent = new Intent(PlayerList.this,CreatingTeam2.class);
                try {
                    Thread.sleep(1000);
                } catch(Exception ex) {/* */}

                startIntent.putExtra("userId",userId);
                startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                hideProgressDialog();
                startActivity(startIntent);
                finish();

            }
        });

    }
    class CustopmAdapter extends BaseAdapter{

        int id;
        @Override
        public int getCount() {
            return filtered.size();
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
            String url = filtered.get(i).getUrl();
            TextView tv4 = view.findViewById(R.id.vstats);
            tv4.setText("View Stats");
            final int j = i;
            tv4.setPaintFlags(tv4.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            tv4.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent startIntent = new Intent(PlayerList.this,PlayerStat.class);
                    startIntent.putExtra("PlayerId",Integer.toString(filtered.get(j).getId()-1));
                    //startIntent.putExtra("Money",Integer.toString(money));
                    startActivity(startIntent);
                }
            });
            loadimage(url,image);
            //image.setImageResource(R.drawable.anon);
            name.setText(filtered.get(i).getName());
            roll.setText(filtered.get(i).getRole());
            team.setText(filtered.get(i).getTeam());
            prices.setText(Integer.toString(filtered.get(i).getPrice())+"M$");
            price.setText("Price");


            id = players.get(i).getId();

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

            mProgressDialog = new ProgressDialog(this);

            mProgressDialog.setMessage("Generating Player List");

                //mProgressDialog.setMessage("Adding Player to the squad.");
            mProgressDialog.setIndeterminate(true);

        }



        mProgressDialog.show();

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

    public void hideProgressDialog() {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {

            mProgressDialog.dismiss();

        }

    }
}

package com.example.jake.fantasy;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class CreatingTeam2 extends AppCompatActivity {

    int batno,bolno,allno,batsel,bolsel,wktsel,allsel,money,total,foreign = 0,price=0,k=0;
    DatabaseReference dref;
    TextView plse,mone,tmName;
    ArrayList<Players> bats,bowls,alls,wkts;
    ListView batL,bolL,allL,wktL;
    String userId,teamName;
    Button submit;
    FirebaseAuth mAuth;
    public ProgressDialog mProgressDialog;
    ValueEventListener mListener;
    private static final String TAG = "playerlist";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Select Squad");
        setContentView(R.layout.activity_creating_team2);
        plse = findViewById(R.id.plSe);
        mone = findViewById(R.id.mon);
        batL = findViewById(R.id.batList);
        bolL = findViewById(R.id.bowlList);
        allL = findViewById(R.id.allList);
        wktL = findViewById(R.id.wktList);
        total = 0;
        foreign = 0;
        tmName = (TextView) findViewById(R.id.teamnn);
        userId=getIntent().getStringExtra("userId");
        showProgressDialog();
        bats = new ArrayList<>();
        bowls = new ArrayList<>();
        wkts = new ArrayList<>();
        alls = new ArrayList<>();
        submit = findViewById(R.id.submit);
        //Log.d("Click","Aisha pore");
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //signIn.setBackgroundColor(Color.GRAY);
                if(total==11) {
                   // Log.d("Click","Aisha pore");
                    Intent startIntent = new Intent(CreatingTeam2.this,TabbedActiviy.class);
                    startIntent.putExtra("userId",userId);
                    startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(startIntent);
                    finish();
                }
                else{
                    Toast.makeText(CreatingTeam2.this, "Squad not complete",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        getData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawermenu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signOut:
                Toast.makeText(CreatingTeam2.this,"Signing Out",Toast.LENGTH_SHORT).show();
                mAuth.getInstance().signOut();
                Intent intent2= new Intent(CreatingTeam2.this,MainActivity.class);

                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                finish();
                break;
            case R.id.Rules:
                Intent intent3= new Intent(CreatingTeam2.this,RulesActivity.class);
                startActivity(intent3);
                break;
            default:
                break;
        }
        return true;
    }
    void getData(){
        dref = FirebaseDatabase.getInstance().getReference();

        mListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {

                DataSnapshot dataSnapshot = data.child("USERS").child(userId);

                Log.d(TAG,"intent4");
                //TextView balance = findViewById(R.id.total_balance_value);
                money = Integer.parseInt(dataSnapshot.child("Balance").getValue().toString());
                teamName = (String)dataSnapshot.child("TeamName").getValue();
                Log.d(TAG,teamName);
                tmName.setText(teamName);

                Log.d(TAG,"hoise");
                batno = Integer.parseInt(dataSnapshot.child("Batsmen").getValue().toString());
                bolno  = Integer.parseInt(dataSnapshot.child("Bowler").getValue().toString());
                allno = Integer.parseInt(dataSnapshot.child("Allrounder").getValue().toString());
                batsel = Integer.parseInt(dataSnapshot.child("BatsmenSel").getValue().toString());
                bolsel = Integer.parseInt(dataSnapshot.child("BowlerSel").getValue().toString());
                wktsel = Integer.parseInt(dataSnapshot.child("WktKeeperSel").getValue().toString());
                allsel = Integer.parseInt(dataSnapshot.child("AllrounderSel").getValue().toString());
                //foreign = Integer.parseInt(dataSnapshot.child("Foreign").getValue().toString());
                total = batsel + allsel + wktsel + bolsel;

                plse.setText(Integer.toString(total)+"/11");
                dref.child("USERS").child(userId).child("TotalSelected").setValue(Integer.toString(total));
                //Log.d(TAG,"dsded");
                //Log.d(TAG,Integer.toString(batsel));

                if(batsel>0 && bats.size()<batsel){
                    for(int i=0;i<batsel;i++){
                        Players player = new Players();
                        DataSnapshot ds = dataSnapshot.child("Bat").child(Integer.toString(i));
                        Log.d(TAG,ds.getValue().toString());
                        String pid = ds.child("PID").getValue().toString();
                        ds = data.child("PLAYERS").child(pid);
                        player.setAge(Integer.parseInt((String) ds.child("Age").getValue()));
                        player.setCountry((String)ds.child("Country").getValue());
                        player.setName((String)ds.child("Name").getValue());
                        player.setRole((String)ds.child("Role").getValue());
                        player.setTeam((String)ds.child("Team").getValue());
                        player.setTotScore(0);
                        player.setPrice(((Long) ds.child("Price").getValue()).intValue());
                        player.setUrl((String)ds.child("ImageURL").getValue());
                        player.setId(((Long) ds.child("PlayerId").getValue()).intValue());
                        if(!player.getCountry().startsWith("Bangla")) foreign++;
                        price += player.getPrice();
                        bats.add(player);

                    }
                }
                if(bolsel>0 && bowls.size()<bolsel){
                    for(int i=0;i<bolsel;i++){
                        DataSnapshot ds = dataSnapshot.child("Bowl").child(Integer.toString(i));
                        Players player = new Players();
                        String pid = ds.child("PID").getValue().toString();
                        ds = data.child("PLAYERS").child(pid);

                        player.setAge(Integer.parseInt((String) ds.child("Age").getValue()));
                        player.setCountry((String)ds.child("Country").getValue());
                        player.setName((String)ds.child("Name").getValue());
                        player.setRole((String)ds.child("Role").getValue());
                        player.setTeam((String)ds.child("Team").getValue());
                        player.setTotScore(0);
                        player.setPrice(((Long) ds.child("Price").getValue()).intValue());
                        player.setUrl((String)ds.child("ImageURL").getValue());
                        player.setId(((Long) ds.child("PlayerId").getValue()).intValue());
                        if(!player.getCountry().startsWith("Bangla")) foreign++;
                        price += player.getPrice();
                        bowls.add(player);

                    }
                }
                if(wktsel>0 && wkts.size()<wktsel){
                    for(int i=0;i<wktsel;i++){
                        DataSnapshot ds = dataSnapshot.child("Wkt").child(Integer.toString(i));
                        Players player = new Players();
                        String pid = ds.child("PID").getValue().toString();
                        ds = data.child("PLAYERS").child(pid);
                        player.setAge(Integer.parseInt((String) ds.child("Age").getValue()));
                        player.setCountry((String)ds.child("Country").getValue());
                        player.setName((String)ds.child("Name").getValue());
                        player.setRole((String)ds.child("Role").getValue());
                        player.setTeam((String)ds.child("Team").getValue());
                        player.setTotScore(0);
                        player.setPrice(((Long) ds.child("Price").getValue()).intValue());
                        player.setUrl((String)ds.child("ImageURL").getValue());
                        player.setId(((Long) ds.child("PlayerId").getValue()).intValue());
                        if(!player.getCountry().startsWith("Bangla")) foreign++;
                        price += player.getPrice();
                        wkts.add(player);

                    }
                }
                if(allsel>0 && alls.size()<allsel){
                    for(int i=0;i<allsel;i++){
                        DataSnapshot ds = dataSnapshot.child("All").child(Integer.toString(i));
                        Players player = new Players();
                        String pid = ds.child("PID").getValue().toString();
                        ds = data.child("PLAYERS").child(pid);
                        player.setAge(Integer.parseInt((String) ds.child("Age").getValue()));
                        player.setCountry((String)ds.child("Country").getValue());
                        player.setName((String)ds.child("Name").getValue());
                        player.setRole((String)ds.child("Role").getValue());
                        player.setTeam((String)ds.child("Team").getValue());
                        player.setTotScore(0);
                        player.setPrice(((Long) ds.child("Price").getValue()).intValue());
                        player.setUrl((String)ds.child("ImageURL").getValue());
                        player.setId(((Long) ds.child("PlayerId").getValue()).intValue());
                        if(!player.getCountry().startsWith("Bangla")) foreign++;
                        price += player.getPrice();
                        alls.add(player);

                    }

                }
                if(k==0){
                    k=1;
                    dref.child("USERS").child(userId).child("Foreign").setValue(foreign);
                    dref.child("USERS").child(userId).child("Price").setValue(price);
                }

                mone.setText(Integer.toString(money-price)+"M$");

                generateLists();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        dref.addValueEventListener(mListener);
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
        batL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent startIntent = new Intent(CreatingTeam2.this,PlayerList.class);
                startIntent.putExtra("UserId",userId);
                startIntent.putExtra("Role","Bat");
                startIntent.putExtra("Country","Any");
                startIntent.putExtra("Name","Any");
                startIntent.putExtra("MaxPrice","Any");
                startIntent.putExtra("MinPrice","Any");
                startIntent.putExtra("Team","Any");
                //startIntent.putExtra("Money",Integer.toString(money));
                //startIntent.putExtra("Fore",Integer.toString(foreign));
                if(position>=batsel) {
                    startIntent.putExtra("Position", Integer.toString(batsel));
                    startIntent.putExtra("One","Yes");
                }
                else{
                    startIntent.putExtra("Position",Integer.toString(position));
                    startIntent.putExtra("One","No");
                }
                Log.d(TAG,"vorse");
                startActivity(startIntent);
            }
        });
        wktL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent startIntent = new Intent(CreatingTeam2.this,PlayerList.class);
                startIntent.putExtra("UserId",userId);
                startIntent.putExtra("Role","Wkt");
                startIntent.putExtra("Country","Any");
                startIntent.putExtra("Name","Any");
                startIntent.putExtra("MaxPrice","Any");
                startIntent.putExtra("MinPrice","Any");
                startIntent.putExtra("Team","Any");
                //startIntent.putExtra("Money",Integer.toString(money));
                //startIntent.putExtra("Fore",Integer.toString(foreign));
                if(position>=wktsel){
                    startIntent.putExtra("Position",Integer.toString(wktsel));
                    startIntent.putExtra("One","Yes");
                }
                else{
                    startIntent.putExtra("Position",Integer.toString(position));
                    startIntent.putExtra("One","No");
                }
                startActivity(startIntent);
            }
        });
        allL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent startIntent = new Intent(CreatingTeam2.this,PlayerList.class);
                startIntent.putExtra("UserId",userId);
                startIntent.putExtra("Role","All");
                startIntent.putExtra("Country","Any");
                startIntent.putExtra("Name","Any");
                startIntent.putExtra("MaxPrice","Any");
                startIntent.putExtra("MinPrice","Any");
                startIntent.putExtra("Team","Any");
                //startIntent.putExtra("Money",Integer.toString(money));
               //startIntent.putExtra("Fore",Integer.toString(foreign));
                if(position>=allsel) {
                    startIntent.putExtra("Position", Integer.toString(allsel));
                    startIntent.putExtra("One","Yes");
                }
                else{
                    startIntent.putExtra("Position",Integer.toString(position));
                    startIntent.putExtra("One","No");
                }
                startActivity(startIntent);
            }
        });
        bolL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent startIntent = new Intent(CreatingTeam2.this,PlayerList.class);
                startIntent.putExtra("UserId",userId);
                startIntent.putExtra("Role","Bowl");
                startIntent.putExtra("Country","Any");
                startIntent.putExtra("Name","Any");
                startIntent.putExtra("MaxPrice","Any");
                startIntent.putExtra("MinPrice","Any");
                startIntent.putExtra("Team","Any");
                //startIntent.putExtra("Money",Integer.toString(money));
                //startIntent.putExtra("Fore",Integer.toString(foreign));
                if(position>=bolsel){
                    startIntent.putExtra("Position",Integer.toString(bolsel));
                    startIntent.putExtra("One","Yes");

                }
                else{
                    startIntent.putExtra("Position",Integer.toString(position));
                    startIntent.putExtra("One","No");
                }
                startActivity(startIntent);
            }
        });
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

            price.setText("Price");
            //Log.d(TAG,Integer.toString(pplayers.size()));
            //Log.d(TAG,Integer.toString(i));
            if(i<pplayers.size()) {
                String url = pplayers.get(i).getUrl();
                loadimage(url,image);
                name.setText(pplayers.get(i).getName());
                roll.setText(pplayers.get(i).getRole());
                team.setText(pplayers.get(i).getTeam());
                prices.setText(Integer.toString(pplayers.get(i).getPrice())+"M$");
             }
             else{

                image.setImageResource(R.drawable.anon);
                name.setText("Add Player");
                roll.setText("");
                team.setText("");
                prices.setText("");
                price.setText("");
            }
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

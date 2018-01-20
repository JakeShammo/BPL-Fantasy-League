package com.example.jake.fantasy;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by jake on 1/18/18.
 */

public class UpdateScore {
    TextView txt;
    String s;
    ArrayList<String> Name = new ArrayList<>();
    ArrayList<Integer> Run = new ArrayList<>();
    ArrayList<Integer> Ball = new ArrayList<>();
    ArrayList<Integer> pid = new ArrayList<>();
    ArrayList<String> bName = new ArrayList<>();
    ArrayList<Integer> bRun = new ArrayList<>();
    ArrayList<Integer> Over = new ArrayList<>();
    ArrayList<Integer> Wkt = new ArrayList<>();
    DatabaseReference dref;
    ArrayList<Integer> scores = new ArrayList<>();
    int mm = 10;
    public UpdateScore() {
    }

    void update(){
        String s = null;
        try {
            s = new ParseScore().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        for(int i=0;i<Name.size();i++) {
            Log.d("ds", Name.get(i) + " " + Integer.toString(Run.get(i)) + " " + Integer.toString(Ball.get(i)));
            final int j = i;
            final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("PLAYERS");
            mDatabase.orderByChild("Name").startAt(Name.get(i)).endAt(Name.get(i)+"\uf8ff").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {

                        int match = Integer.parseInt(snapshot.child("BatPerform").child("Match").getValue().toString());
                        int run = Integer.parseInt(snapshot.child("BatPerform").child("Run").getValue().toString());
                        int ball = Integer.parseInt(snapshot.child("BatPerform").child("Ball").getValue().toString());
                        int totScore = Integer.parseInt(snapshot.child("TotScore").getValue().toString());
                        int id = Integer.parseInt(snapshot.child("PlayerId").getValue().toString()) - 1;

                        Log.d("ds","Updating Data of id "+ Integer.toString(id));
                         mDatabase.child(Integer.toString(id)).child("BatPerform").child("Match").setValue(match + 1);
                         mDatabase.child(Integer.toString(id)).child("BatPerform").child("Ball").setValue(ball + Ball.get(j));
                        mDatabase.child(Integer.toString(id)).child("BatPerform").child("Run").setValue(run + Run.get(j));
                        int score;
                        if(Ball.get(j)!=0) score = Run.get(j) * Run.get(j)/Ball.get(j);
                        else score = 0;
                        if(pid.contains(id)){
                            scores.set(pid.indexOf(id),scores.get(pid.indexOf(id))+score);
                        }
                        else {
                            pid.add(id);
                            scores.add(score);
                        }
                        mDatabase.child(Integer.toString(id)).child("BatScores").child(Integer.toString(match)).child("Mid").setValue(mm);
                        mDatabase.child(Integer.toString(id)).child("BatScores").child(Integer.toString(match)).child("Score").setValue(score);
                        mDatabase.child(Integer.toString(id)).child("TotScore").setValue(totScore+scores.get(pid.indexOf(id)));


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });
        }
        Log.d("ds","bat sesh");

        try{ Thread.sleep(3000); }catch(InterruptedException e){ }
        Log.d("ds","bol shuru");

        for(int i=0;i<bName.size();i++) {
            Log.d("ds", bName.get(i) + " " + Integer.toString(bRun.get(i)) + " " + Integer.toString(Over.get(i)));
            final int j = i;
            final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("PLAYERS");
            mDatabase.orderByChild("Name").startAt(bName.get(i)).endAt(bName.get(i)+"\uf8ff").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {

                        int match = Integer.parseInt(snapshot.child("BowlPerform").child("Match").getValue().toString());
                        int run = Integer.parseInt(snapshot.child("BowlPerform").child("Run").getValue().toString());
                        int over = Integer.parseInt(snapshot.child("BowlPerform").child("Over").getValue().toString());
                        int wkt = Integer.parseInt(snapshot.child("BowlPerform").child("Wkt").getValue().toString());
                        int totScore = Integer.parseInt(snapshot.child("TotScore").getValue().toString());
                        int id = Integer.parseInt(snapshot.child("PlayerId").getValue().toString()) - 1;
                        Log.d("ds","Updating Data of id "+ Integer.toString(id));
                        mDatabase.child(Integer.toString(id)).child("BowlPerform").child("Match").setValue(match + 1);
                        mDatabase.child(Integer.toString(id)).child("BowlPerform").child("Over").setValue(over + Over.get(j));
                        mDatabase.child(Integer.toString(id)).child("BowlPerform").child("Run").setValue(run + bRun.get(j));
                        mDatabase.child(Integer.toString(id)).child("BowlPerform").child("Wkt").setValue(wkt + Wkt.get(j));
                        int score = Wkt.get(j)*15 + (7*Over.get(j)-bRun.get(j));
                        if(score<0) score = 0;
                        if(pid.contains(id)){
                            scores.set(pid.indexOf(id),scores.get(pid.indexOf(id))+score);
                        }
                        else {
                            pid.add(id);
                            scores.add(score);
                        }
                        mDatabase.child(Integer.toString(id)).child("BowlScores").child(Integer.toString(match)).child("Mid").setValue(mm);
                        mDatabase.child(Integer.toString(id)).child("BowlScores").child(Integer.toString(match)).child("Score").setValue(score);
                        mDatabase.child(Integer.toString(id)).child("TotScore").setValue(totScore + scores.get(pid.indexOf(id)));


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });
        }
        dref = FirebaseDatabase.getInstance().getReference();
        dref = dref.child("USERS");
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    String uid = ds.getKey();
                    int total = Integer.parseInt(ds.child("TotalSelected").getValue().toString());
                    int score = 0;
                    if(total == 11){
                        int batno = Integer.parseInt(ds.child("Batsmen").getValue().toString());
                        int allno = Integer.parseInt(ds.child("Allrounder").getValue().toString());
                        int bolno = Integer.parseInt(ds.child("Bowler").getValue().toString());
                        for(int i=0;i<batno;i++){
                            int temp = Integer.parseInt(ds.child("Bat").child(Integer.toString(i)).child("PID").getValue().toString());
                            if(pid.contains(temp)){
                                int sc = Integer.parseInt(ds.child("Bat").child(Integer.toString(i)).child("Score").getValue().toString());
                                dref.child(uid).child("Bat").child(Integer.toString(i)).child("Score").setValue(sc + scores.get(pid.indexOf(temp)));
                                score += scores.get(pid.indexOf(temp));
                            }
                        }
                        for(int i=0;i<allno;i++){
                            int temp = Integer.parseInt(ds.child("All").child(Integer.toString(i)).child("PID").getValue().toString());
                            if(pid.contains(temp)){
                                int sc = Integer.parseInt(ds.child("All").child(Integer.toString(i)).child("Score").getValue().toString());
                                dref.child(uid).child("All").child(Integer.toString(i)).child("Score").setValue(sc + scores.get(pid.indexOf(temp)));
                                score += scores.get(pid.indexOf(temp));
                            }
                        }
                        for(int i=0;i<1;i++){
                            int temp = Integer.parseInt(ds.child("Wkt").child(Integer.toString(i)).child("PID").getValue().toString());
                            if(pid.contains(temp)){
                                int sc = Integer.parseInt(ds.child("Wkt").child(Integer.toString(i)).child("Score").getValue().toString());
                                dref.child(uid).child("Wkt").child(Integer.toString(i)).child("Score").setValue(sc + scores.get(pid.indexOf(temp)));
                                score += scores.get(pid.indexOf(temp));
                            }
                        }
                        for(int i=0;i<bolno;i++){
                            int temp = Integer.parseInt(ds.child("Bowl").child(Integer.toString(i)).child("PID").getValue().toString());
                            if(pid.contains(temp)){
                                int sc = Integer.parseInt(ds.child("Bowl").child(Integer.toString(i)).child("Score").getValue().toString());
                                dref.child(uid).child("Bowl").child(Integer.toString(i)).child("Score").setValue(sc + scores.get(pid.indexOf(temp)));
                                score += scores.get(pid.indexOf(temp));
                            }
                        }
                        dref.child(uid).child("Scores").child(Integer.toString(mm)).setValue(score);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    class ParseScore extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            org.jsoup.nodes.Document doc;
            try {
                s = "";
                doc = Jsoup.connect("http://www.cricbuzz.com/api/html/cricket-scorecard/"+Integer.toString(19000+mm-2)).get();
                Elements element = doc.getElementsByClass("cb-col cb-col-100 cb-scrd-itms");

                for(int i=0;i<element.size();i++){
                    s = element.get(i).text() ;
                    String []ss = s.split(" ");
                    if(ss[0].equals("Extras")||ss[0].equals("Did")||ss[0].equals("Total")){
                        continue;
                    }
                    Name.add(ss[0]+" "+ss[1]);
                    int j = 2;
                    while (j<ss.length){
                        boolean flag = true;
                        Integer temp = 0 ;
                        try {
                            temp = Integer.parseInt(ss[j]);
                        } catch (NumberFormatException ex) {
                            flag = false;
                        }
                        if(flag == true){
                            Run.add(temp);
                            Ball.add(Integer.parseInt(ss[j+1]));
                            break;
                        }
                        j++;
                    }
                }

                Elements element1 = doc.getElementsByClass("cb-col cb-col-100 cb-scrd-itms ");

                for(int i=0;i<element1.size();i++){
                    s = element1.get(i).text() ;
                    String []ss = s.split(" ");
                    if(ss[0].equals("Extras")||ss[0].equals("Did")||ss[0].equals("Total")){
                        continue;
                    }
                    bName.add(ss[0]+" "+ss[1]);
                    int j = 2;
                    while (j<ss.length){
                        boolean flag = true;
                        Float temp = (float)0;
                        try {
                            temp = Float.parseFloat(ss[j]);
                        } catch (NumberFormatException ex) {
                            flag = false;
                        }
                        if(flag == true){
                            Over.add(Math.round(temp));
                            bRun.add(Integer.parseInt(ss[j+2]));
                            Wkt.add(Integer.parseInt(ss[j+3]));
                            break;
                        }
                        j++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return s;
        }
    }
}


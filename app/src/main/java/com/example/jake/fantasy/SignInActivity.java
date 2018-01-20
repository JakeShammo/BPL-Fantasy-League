package com.example.jake.fantasy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {


    Button signIn;
    EditText mEmailField,mPasswordField;
    int total;
    public ProgressDialog mProgressDialog;
    private FirebaseAuth mAuth;
    String userId;
    ValueEventListener mListener;DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mEmailField = findViewById(R.id.nameinp);
        mPasswordField = findViewById(R.id.editText2);
        mAuth = FirebaseAuth.getInstance();
        signIn = findViewById(R.id.button3);
        //mEmailField.setText("iamjacobzz@outlook.com");
        //mPasswordField.setText("1234567");
        signIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Log.d(TAG, "signIn:" + email);
                if (!validateForm()) {
                    return;
                }
                showProgressDialog();
                String email = mEmailField.getText().toString();
                String password = mPasswordField.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    updateUI();
                                }
                                else {
                                   // Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(SignInActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                   // updateUI(null);
                                }
                                if (!task.isSuccessful()) {

                                    Toast.makeText(SignInActivity.this,"Autehentication Failed.",Toast.LENGTH_SHORT);

                                }

                                hideProgressDialog();
                            }

                        });}
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){

            Log.d("slow","ashche1");
            updateUI();
        }
    }
    void updateUI(){
        //Log.d(TAG, "signInWithEmail:success");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Log.d("slow","ashche2");
        userId=user.getUid();

        Log.d("slow","ashche3");
        Toast.makeText(SignInActivity.this,"Signed In",Toast.LENGTH_SHORT).show();
        db= FirebaseDatabase.getInstance().getReference("USERS/"+userId).child("TotalSelected");

        Log.d("slow","ashche4");
        mListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("slow","ashche5");
                total = Integer.parseInt(dataSnapshot.getValue().toString());

                Log.d("slow","ashche6");
                if (total==11){
                    Intent startIntent = new Intent(SignInActivity.this,TabbedActiviy.class);
                    startIntent.putExtra("userId",userId);
                    Log.d("slow","ashche7");
                    startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(startIntent);
                    finish();
                                        /*---you might want to call finish() method here but never do that
                                        ----call finish() method from outside the listener---
                                         */
                }
                else{
                    Intent startIntent = new Intent(SignInActivity.this,CreatingTeam2.class);
                    startIntent.putExtra("userId",userId);
                    startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(startIntent);
                    finish();
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        Log.d("slow","ashchemaj");
        db.addValueEventListener(mListener);
        //db.removeEventListener(mListener);
        //finish();

    }

    void change(){

    }

    private boolean validateForm() {
        boolean valid = true;
        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        }
        else {
            mEmailField.setError(null);
        }
        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        }
        else {
            mPasswordField.setError(null);
        }
        return valid;
    }
    public void showProgressDialog() {

        if (mProgressDialog == null) {

            mProgressDialog = new ProgressDialog(this);

            mProgressDialog.setMessage("Loading");

            mProgressDialog.setIndeterminate(true);

        }



        mProgressDialog.show();

    }

    @Override
    protected void onStop() {
        if (mListener != null && db!=null) {
        db.removeEventListener(mListener);
    }
        super.onStop();
    }

    @Override
    protected void onPause() {
        if (mListener != null && db!=null) {
            db.removeEventListener(mListener);
        }
        super.onPause();
    }

    public void hideProgressDialog() {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {

            mProgressDialog.dismiss();

        }

    }
}

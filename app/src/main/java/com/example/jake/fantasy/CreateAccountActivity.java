package com.example.jake.fantasy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class CreateAccountActivity extends AppCompatActivity implements
        View.OnClickListener {


    Button signIn;
    EditText name,email,password,conpass;
    private FirebaseAuth mAuth;
    DatabaseReference dref;
    private static final String TAG = "EmailPassword";
    @VisibleForTesting

    public ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Log.d(TAG, "createUserWithEmail:starting");
        mAuth = FirebaseAuth.getInstance();
        signIn = findViewById(R.id.ca);
        name = findViewById(R.id.nameinp);
        email = findViewById(R.id.emailinp);
        password = findViewById(R.id.passinp);
        conpass = findViewById(R.id.conpassinp);
        signIn.setOnClickListener(this);
    }

    public void createAccount(){
        if (!validateForm()) {
            Log.d(TAG, "createUserWithEmail:validation failed");

            return;
        }

        Log.d(TAG, "createUserWithEmail:ekhane ashlo");
        final String semail = email.getText().toString();
        final String spassword = password.getText().toString();
        final  String scon = conpass.getText().toString();
        if(!spassword.equals(scon)){
            Toast.makeText(CreateAccountActivity.this, "Passwords do not match.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        showProgressDialog();
        Log.d(TAG, "createUserWithEmail:ekhane ashlo");
        mAuth.createUserWithEmailAndPassword(semail, spassword)
                .addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:complete");

                        if (task.isSuccessful()) {

                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String userId=user.getUid();dref= FirebaseDatabase.getInstance().getReference();
                            dref = dref.child("USERS").child(userId);
                            dref.child("Name").setValue(name.getText().toString());
                            dref.child("Email").setValue(semail);dref.child("Password").setValue(spassword);
                            dref.child("Balance").setValue(110);dref.child("Score").setValue(0);
                            dref.child("Since").setValue(ServerValue.TIMESTAMP);
                            dref.child("TotalSelected").setValue(0);
                            Toast.makeText(CreateAccountActivity.this,"Account Created",Toast.LENGTH_SHORT).show();
                            Intent startIntent = new Intent(CreateAccountActivity.this,WelcomeActiviy.class);
                            startIntent.putExtra("userId",userId);
                            startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            hideProgressDialog();
                            startActivity(startIntent);
                            finish();
                        }
                        else {
                            //log
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CreateAccountActivity.this, "Create Account failed",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                        hideProgressDialog();

                    }

                });

    }
    @Override
    public void onStart() {
        super.onStart();
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    @Override
    public void onStop() {

        super.onStop();

        hideProgressDialog();

    }

    public void showProgressDialog() {

        if (mProgressDialog == null) {

            mProgressDialog = new ProgressDialog(this);

            mProgressDialog.setMessage("Loading");

            mProgressDialog.setIndeterminate(true);

        }



        mProgressDialog.show();

    }

    private boolean validateForm() {
        boolean valid = true;
        String semail = email.getText().toString();
        if (TextUtils.isEmpty(semail)) {
            email.setError("Required.");
            valid = false;
        }
        else {
            email.setError(null);
        }
        String spassword = password.getText().toString();
        if (TextUtils.isEmpty(spassword)) {
            password.setError("Required.");
            valid = false;
        }
        else if(spassword.length()<6){
            password.setError("Minimum 6 charecters.");
            valid = false;
        }
        else {
            password.setError(null);
        }
        String sname = name.getText().toString();
        if (TextUtils.isEmpty(sname)) {
            name.setError("Required.");
            valid = false;
        }
        else {
            name.setError(null);
        }
        return valid;
    }

    public void hideProgressDialog() {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {

            mProgressDialog.dismiss();

        }

    }

    @Override
    public void onClick(View view) {
        createAccount();
    }
}

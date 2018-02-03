package com.example.admin.fcmmessage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button loginbutton;
    String currentDeviceId;
    TextView username;
    FirebaseDatabase database;
    User user = User.getInstance();
    DatabaseReference userref;

    SharedPreferences sharedpreferences;
    Firebaseusermodel firebaseusermodel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(user.apppreference, Context.MODE_PRIVATE);
        user.sharedprefernces = sharedpreferences;
        currentDeviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        database = FirebaseDatabase.getInstance();
        userref = database.getReference("users");
        final ProgressDialog Dialog = new ProgressDialog(this);
        Dialog.setMessage("pleasewait");
        Dialog.setCancelable(false);
        userref.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            public Firebaseusermodel firebaseuserModel;

            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                Dialog.dismiss();
                for (com.google.firebase.database.DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Firebaseusermodel firebaseusermodel = userSnapshot.getValue(Firebaseusermodel.class);

                    if (firebaseuserModel.getDeviceId().equals(currentDeviceId)) {
                        firebaseuserModel.setDeviceToken(FirebaseInstanceId.getInstance().getToken());
                        user.login(firebaseuserModel);
                        user.savefirebaseKey(userSnapshot.getKey());
                        moveToChattingScreen();


                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Dialog.dismiss();
                System.out.println("The read failed" + databaseError.getMessage());


            }
        });
        username = (TextView) findViewById(R.id.loginid);


    }

    public void moveToChattingScreen() {
        Intent intent = new Intent(this, );
        startActivity();
        finish();
    }
    public void loginbuttonTapped(View view){
        String struser=username.getText().toString().trim();
        if (struser.isEmpty() ){
            System.out.println("invalid name");


        }
        else {
            final Firebaseusermodel firebaseusermodel=new Firebaseusermodel();
            firebaseusermodel.setUserName(struser);
            firebaseusermodel.setDeviceId(currentDeviceId);
            firebaseusermodel.setDeviceToken(FirebaseInstanceId.getInstance().getToken());
            final  DatabaseReference newRef=userref.push();
            newRef.setValue(firebaseusermodel, new DatabaseReference.CompletionListener() {

                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    if (user.login(firebaseusermodel)){
                        moveToChattingScreen();
                    }

                }
            });
        }

    }
}


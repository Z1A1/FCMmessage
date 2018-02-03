package com.example.admin.fcmmessage;

import android.app.DownloadManager;
import android.content.AsyncQueryHandler;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ChattingActivity extends AppCompatActivity {
    private static final String TAG="ChattingActivity";

    User user=User.getInstance();
    EditText messagecomment;
    Button sendbtn;
    ListView listView;
    List<Firebaseusermodel> message=new ArrayList<Firebaseusermodel>();
    FirebaseDatabase database;
    DatabaseReference mesgRef;
    DatabaseReference userRef;
    public static  ChattingActivity chattingActivity;
    JSONArray registration_id=new JSONArray();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        database=FirebaseDatabase.getInstance();
        userRef=database.getReference("users");
        mesgRef=database.getReference("messages");
        setTitle("chatting");
        chattingActivity=this;
        listView=(ListView)findViewById(R.id.Chattinglist);
        messagecomment=(EditText)findViewById(R.id.dt);
        sendbtn=(Button)findViewById(R.id.button);
        sendbtn.setEnabled(false);
        final com.google.firebase.database.ValueEventListener connectvalue= new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                message.clear();
                for (com.google.firebase.database.DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    System.out.println("child"+postSnapshot);
                    FirebaseMessageModel firebaseMessageModel=postSnapshot.getValue(FirebaseMessageModel.class);
                    firebaseMessageModel.setId(postSnapshot.getKey());

                }
                upadateListview();



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                message.clear();
                updateListView();

            }
        };
        final com.google.firebase.database.ValueEventListener uservalueEventListener= new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                registration_id=new JSONArray();
                for (com.google.firebase.database.DataSnapshot postDataSnapshot:dataSnapshot.getChildren()){
                    System.out.println("Child:"+postDataSnapshot);
                    Firebaseusermodel firebaseusermodel=postDataSnapshot.getValue(Firebaseusermodel.class);
                    if (!firebaseusermodel.getDeviceId().equals(user.deviceId)&&!firebaseusermodel.getDeviceToken().isEmpty()){
                        registration_id.put(firebaseusermodel.getDeviceToken());
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        userRef.addValueEventListener(uservalueEventListener);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final  DatabaseReference NEWREF=mesgRef.push();
                NEWREF.setValue("firebasemessagemodel", new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError!=null){
                            Log.i(TAG,databaseError.toString() );

                        }
                        else {
                         messagecomment.setText("");
                        }
                        if (registration_id.length()>0){
                            String url="https://fcm.googleapis.com/fcm/send";
                            AsyncHttpClient client=new  AsyncHttpClient;
                            client.adHeader[HttpHeader.AUTHORIZATION,"AIzaSyCViYiy6p3BRPyOHjiRnLS_c6CFrs5DR5M"];
                            client.adHEADER[Http.CONTENT_TYPE, RequestParams_APPLICATION_JASON];
                            try {
                                JSONObject params=new JSONObject();
                                params.put("registration_ids",registration_id);
                                notificationo.put("title",user.name);
                                StringEntity entity=new StringEntity;
                                client.post(getApplicationContext(),url).entity.RequestParams.APPLICATIN_JSON ;

                            }

                        }

                    }
                });

            }
        });




    }
}

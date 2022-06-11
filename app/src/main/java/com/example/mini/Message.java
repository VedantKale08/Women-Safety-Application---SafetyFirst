package com.example.mini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Message extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    FusedLocationProviderClient client;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Contacts");
        client = LocationServices.getFusedLocationProviderClient(this);
        b=findViewById(R.id.button4);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String p1 =snapshot.child(firebaseUser.getUid()).child("phone1").getValue(String.class);
                        String p2 =snapshot.child(firebaseUser.getUid()).child("phone2").getValue(String.class);
                        String p3 =snapshot.child(firebaseUser.getUid()).child("phone3").getValue(String.class);

                        LocationMap locationMap = new LocationMap();
                        if (ActivityCompat.checkSelfPermission(Message.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Message.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                double lt=location.getLatitude();
                                double lg=location.getLongitude();
                                SmsManager mySms = SmsManager.getDefault();
                                mySms.sendTextMessage(p1,null,"Help !!",null,null);
                                mySms.sendTextMessage(p1,null,"http://maps.google.com/?q="+lt+","+lg,null,null);
                                mySms.sendTextMessage(p2,null,"Help !!",null,null);
                                mySms.sendTextMessage(p2,null,"http://maps.google.com/?q="+lt+","+lg,null,null);
                                mySms.sendTextMessage(p3,null,"Help !!",null,null);
                                mySms.sendTextMessage(p3,null,"http://maps.google.com/?q="+lt+","+lg,null,null);
                                Toast.makeText(Message.this, "Message Sent successfully", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}
package com.example.mini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    FirebaseDatabase fb;
    DatabaseReference reference;
    FirebaseAuth fAuth;
    TextView username,yourPhoneNo,p1,p2,p3;
    Button edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = fAuth.getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference("Contacts");

        username = findViewById(R.id.textname);
        p1=findViewById(R.id.textPhone2);
        p2=findViewById(R.id.textPhone3);
        p3=findViewById(R.id.textPhone4);
        yourPhoneNo = findViewById(R.id.textPhone);
        edit=findViewById(R.id.button6);

        Intent i = getIntent();
        String user=i.getStringExtra("UserName");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String u = snapshot.child(firebaseUser.getUid()).child("user").getValue(String.class);
                String m1 = snapshot.child(firebaseUser.getUid()).child("phone1").getValue(String.class);
                String m2 = snapshot.child(firebaseUser.getUid()).child("phone2").getValue(String.class);
                String m3 = snapshot.child(firebaseUser.getUid()).child("phone3").getValue(String.class);
                String p = snapshot.child(firebaseUser.getUid()).child("mobile").getValue(String.class);

                username.setText(u);
                p1.setText(m1);
                p2.setText(m2);
                p3.setText(m3);
                yourPhoneNo.setText(p);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new  Intent(getApplicationContext(),EditProfile.class);
                intent.putExtra("username",username.getText());
                intent.putExtra("yourPhoneNo",yourPhoneNo.getText());
                intent.putExtra("p1",p1.getText());
                intent.putExtra("p2",p2.getText());
                intent.putExtra("p3",p3.getText());
                startActivity(intent);
            }
        });
    }
}
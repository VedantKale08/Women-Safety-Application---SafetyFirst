package com.example.mini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditProfile extends AppCompatActivity {

    EditText u,yp,p1,p2,p3;
    FirebaseAuth fAuth;
    DatabaseReference reference;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        u=findViewById(R.id.editTextTextPersonName2);
        yp=findViewById(R.id.editTextPhone2);
        p1=findViewById(R.id.editAddPhone3);
        p2=findViewById(R.id.editAddPhone);
        p3=findViewById(R.id.editAddPhone2);
        b=findViewById(R.id.button7);

        fAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = fAuth.getCurrentUser();
        String getUID = firebaseUser.getUid();

        reference= FirebaseDatabase.getInstance().getReference("Contacts");

        Intent intent = getIntent();
        String user= intent.getStringExtra("username");
        String myPhone=intent.getStringExtra("yourPhoneNo");
        String ph1=intent.getStringExtra("p1");
        String ph2=intent.getStringExtra("p2");
        String ph3=intent.getStringExtra("p3");

        u.setText(user);
        yp.setText(myPhone);
        p1.setText(ph1);
        p2.setText(ph2);
        p3.setText(ph3);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(!user.equals(u)||!myPhone.equals(yp)||!ph1.equals(p1)||!ph2.equals(p2)||!ph3.equals(p3)){
                    PhoneNoStore p = new PhoneNoStore();
                    p.setUser(u.getText().toString());
                    p.setPhone1(p1.getText().toString());
                    p.setPhone2(p2.getText().toString());
                    p.setPhone3(p3.getText().toString());
                    p.setMobile(yp.getText().toString());

                    reference.child(firebaseUser.getUid()).setValue(p).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(EditProfile.this, "Saved", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(EditProfile.this, "error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
        });

    }

}
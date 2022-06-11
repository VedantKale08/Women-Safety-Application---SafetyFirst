package com.example.mini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Phone extends AppCompatActivity {

    FirebaseDatabase fb;
    DatabaseReference reference;
    TextView t1,t2,t3,t4;
    ImageView i1,i2,i3,i4;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        fAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = fAuth.getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference("Contacts");

        t1=findViewById(R.id.textp1);
        t2=findViewById(R.id.textp2);
        t3=findViewById(R.id.textp3);

        i1=findViewById(R.id.imageView);
        i2=findViewById(R.id.imageView2);
        i3=findViewById(R.id.imageView4);

        Intent i = getIntent();
        String user=i.getStringExtra("UserName");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //PhoneNoStore p = snapshot.getValue(PhoneNoStore.class);
                        String p1 =snapshot.child(firebaseUser.getUid()).child("phone1").getValue(String.class);
                        String p2 =snapshot.child(firebaseUser.getUid()).child("phone2").getValue(String.class);
                        String p3 =snapshot.child(firebaseUser.getUid()).child("phone3").getValue(String.class);
                        t1.setText(p1);
                        t2.setText(p2);
                        t3.setText(p3);

                    i1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i =new Intent(Intent.ACTION_CALL);
                            i.setData(Uri.parse("tel:"+p1));
                            startActivity(i);
                        }
                    });

                    i2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i =new Intent(Intent.ACTION_CALL);
                            i.setData(Uri.parse("tel:"+p2));
                            startActivity(i);
                        }
                    });

                    i3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i =new Intent(Intent.ACTION_CALL);
                            i.setData(Uri.parse("tel:"+p3));
                            startActivity(i);
                        }
                    });
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}
package com.example.mini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginUser extends AppCompatActivity {

    EditText email1,pass;
    Button login;
    TextView reg;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        email1=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        login=findViewById(R.id.button);
        reg=findViewById(R.id.textView);
        fAuth=FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email1.getText().toString().trim();
                String passW = pass.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    email1.setError("Enter Email");
                }
                else if(TextUtils.isEmpty(passW)){
                    pass.setError("Enter Password");
                }
                else if(passW.length() < 6){
                    pass.setError("Password must be greater than 6");
                }
                else{
                    fAuth.signInWithEmailAndPassword(email,passW).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(loginUser.this, "Logged in successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Tutorial1.class);
                                startActivity(intent);
                                finish();

                            }
                            else{
                                Toast.makeText(loginUser.this, "Error ! : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }
}
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class Register extends AppCompatActivity {

    EditText usename,email,password,phone1,phone2,phone3,otp;
    Button register,getOTP,verify;
    EditText mobile;
    TextView login;
    FirebaseAuth fAuth;
    FirebaseDatabase fb;
    DatabaseReference reference;
    String verificationId;
    private static final String KEY_VERIFICATION_ID = "key_verification_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usename = findViewById(R.id.editTextTextPersonName);
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        phone1=findViewById(R.id.editAddPhone6);
        phone2=findViewById(R.id.editAddPhone7);
        phone3=findViewById(R.id.editAddPhone8);
        otp= findViewById(R.id.edittOTP);
        register = (Button) findViewById(R.id.button);
        mobile=findViewById(R.id.editTextPhone);
        login = findViewById(R.id.loginPage);
        getOTP=findViewById(R.id.button2);
        verify=findViewById(R.id.button5);

        fAuth = FirebaseAuth.getInstance();
        fb=FirebaseDatabase.getInstance();
        reference=fb.getReference("Contacts");



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getname = usename.getText().toString().trim();
                String getEmail = email.getText().toString().trim();
                String getPass = password.getText().toString().trim();
                String getMob = mobile.getText().toString().trim();
                String p1= phone1.getText().toString();
                String p2= phone2.getText().toString();
                String p3= phone3.getText().toString();

                if(TextUtils.isEmpty(getEmail)){
                    email.setError("Enter Email");
                }
                else if(TextUtils.isEmpty(getPass)){
                    password.setError("Enter Password");
                }
                else if(getPass.length() < 6){
                    password.setError("Password must be greater than 6");
                }
                else{
                    fAuth.createUserWithEmailAndPassword(getEmail,getPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser firebaseUser = fAuth.getCurrentUser();
                                PhoneNoStore p = new PhoneNoStore();
                                p.setUser(getname);
                                p.setPhone1(p1);
                                p.setPhone2(p2);
                                p.setPhone3(p3);
                                p.setMobile(getMob);

                                reference.child(firebaseUser.getUid()).setValue(p).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getApplicationContext(),loginUser.class);
                                            i.putExtra("UserName",getname);
                                            startActivity(i);
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(Register.this, "Error ! : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                            else{
                                Toast.makeText(Register.this, "Error ! : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


        getOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + mobile.getText().toString(), 60, TimeUnit.SECONDS, Register.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        Toast.makeText(Register.this, "OTP sent", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        verificationId=s;
                        Toast.makeText(Register.this, "OTP sent", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otp.getText().toString().isEmpty()){
                    Toast.makeText(Register.this, "Enter valid op", Toast.LENGTH_SHORT).show();
                }
                String code=otp.getText().toString();
                if(verificationId!=null){
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Register.this, "Verified", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(Register.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),loginUser.class));
            }
        });
    }
}
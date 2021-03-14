package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends  AppCompatActivity {
    private static final String TAG = "EmailPassword";

    private EditText Name, Email,Phone, Password;
    private Button SignUp;
    private TextView AlreadyRegistered;
    //private ImageView i3;
    String UName , UEmail , UPhone , UPassword , CountryCode="+91",JustPhone;

    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().setTitle("Sign Up");

        mAuth = FirebaseAuth.getInstance();

        setUpUI();


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                UEmail= Email.getText().toString().trim();
                UPassword= Password.getText().toString().trim();
                JustPhone= Phone.getText().toString().trim();
                UPhone= CountryCode.concat(JustPhone);


                mAuth.createUserWithEmailAndPassword(UEmail,UPassword).
                        addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(Registration.this,"Please Check Your Email for Verification" ,Toast.LENGTH_LONG).show();
                                                Log.d(TAG, "Email sent.");
                                            }
                                            else
                                            {
                                                try {
                                                    throw task.getException();
                                                }
                                                catch(Exception e) {
                                                    String err = e.getMessage().toString();
                                                    Toast.makeText(Registration.this, err, Toast.LENGTH_LONG).show();
                                                }

                                            }
                                        }
                                    });


                                    UName = Name.getText().toString().trim();
                                        sendUserData();
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");

                                    mAuth.signOut();
                                    finish();
                                    startActivity(new Intent(Registration.this, MainActivity.class));
                                        Toast.makeText(Registration.this, "Registration Successful",
                                                Toast.LENGTH_SHORT).show();

                                }
                                else {

                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    try{ throw task.getException();}
                                    catch(Exception e){ String error= e.getMessage().toString();

                                    Toast.makeText(Registration.this, error, Toast.LENGTH_SHORT).show();}

                                }

                            }
                        });

            }
        });

        AlreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this, MainActivity.class));
            }
        });


    }



    private void sendUserData()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(mAuth.getUid());
        UserProfile users= new UserProfile(UName, UEmail, UPhone, UPassword);
        myRef.setValue(users);
    }

    private void setUpUI()
    {
        Email = (EditText) findViewById(R.id.iEmail);
        Password = (EditText) findViewById(R.id.iPassword);
        Phone = (EditText) findViewById(R.id.iPhone);
        Name = (EditText) findViewById(R.id.iName);
        SignUp = (Button) findViewById(R.id.iSignUp);
        AlreadyRegistered = (TextView) findViewById(R.id.iAlreadyReg);
        //i3 = (ImageView)findViewById(R.id.imageI3);
    }
}



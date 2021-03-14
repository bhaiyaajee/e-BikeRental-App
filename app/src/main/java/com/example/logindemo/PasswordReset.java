package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PasswordReset extends AppCompatActivity {

    private EditText Email;
    private Button PasswordResetButton;
    private TextView Message;
    private ImageView img;
    String UEmail;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Reset Password");
        setContentView(R.layout.activity_password_reset);
        setUpUI();
        img.setImageResource(R.drawable.i3);
        mAuth= FirebaseAuth.getInstance();

        PasswordResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UEmail=Email.getText().toString().trim();
                mAuth.sendPasswordResetEmail(UEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                               @Override
                                                                               public void onComplete(@NonNull Task<Void> task) {
                                                                                   if (task.isSuccessful()) {
                                                                                       Toast.makeText(getApplicationContext(),"Password Reset Link has been sent to your email",Toast.LENGTH_LONG).show();
                                                                                       startActivity(new Intent(getApplicationContext(),MainActivity.class));

                                                                                   } else {
                                                                                       try {
                                                                                           throw task.getException();
                                                                                       }
                                                                                       catch(Exception e)
                                                                                       {
                                                                                           String err= e.getMessage().toString();
                                                                                           Toast.makeText(getApplicationContext(),err,Toast.LENGTH_LONG).show();
                                                                                       }

                                                                                   } }
                });



            }
        });


    }

    private void setUpUI()
    {
        Email = (EditText) findViewById(R.id.iEmail);
        PasswordResetButton = (Button) findViewById(R.id.iPasswordResetButton);
        Message= (TextView)findViewById(R.id.iMessage);
        img=(ImageView)findViewById(R.id.imgI3);
    }
}

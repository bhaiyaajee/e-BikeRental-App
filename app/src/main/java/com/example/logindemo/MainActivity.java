package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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


public class MainActivity extends AppCompatActivity {

    private EditText Email, Password;
    private Button SignIn;
    private TextView SignUp , ForgotPassword;
    private ImageView i3;
    private ProgressDialog progress;

    private static final String TAG = "EmailPassword";


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("i3 Advanved Tech");
        setUpUI();
        i3.setImageResource(R.drawable.i3);

        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,PasswordReset.class));

            }
        });

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user= mAuth.getCurrentUser();

            if (user != null)
            {
                startActivity(new Intent(MainActivity.this, Booking.class));
            }


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Registration.class));

            }
        });

        progress = new ProgressDialog(this);
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String UserName = Email.getText().toString().trim();
                String Pass = Password.getText().toString().trim();


                progress.setTitle("Logging in");
                progress.setMessage("Bhaiyaajee is Logging you in");
                    progress.show();


                    mAuth.signInWithEmailAndPassword(UserName, Pass).
                            addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful())
                                    {
                                        progress.dismiss();
                                        Log.d(TAG, "createUserWithEmail:success");

                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if(!user.isEmailVerified())
                                        {

                                            Toast.makeText(getApplicationContext(),"Check your email for verification",Toast.LENGTH_LONG).show();
                                            user.sendEmailVerification();
                                            mAuth.signOut();
                                        }
                                        else
                                            {
                                            startActivity(new Intent(MainActivity.this, Booking.class));
                                            Toast.makeText(MainActivity.this, "Login Successful",
                                                    Toast.LENGTH_SHORT).show();
                                            finish();
                                        }

                                    }
                                    else
                                        {
                                        progress.dismiss();
                                            try {
                                                throw task.getException();
                                            }
                                            catch(Exception e)
                                            {
                                                String err= e.getMessage().toString();
                                                Toast.makeText(getApplicationContext(),err,Toast.LENGTH_LONG).show();
                                            }

                                    }

                                }
                            });


                }
        });
    }

    private void setUpUI() {

        Email = (EditText) findViewById(R.id.iEmail);
        Password = (EditText) findViewById(R.id.iPassword);
        SignIn = (Button) findViewById(R.id.iSignIn);
        SignUp = (TextView) findViewById(R.id.iSignUp);
        ForgotPassword = (TextView) findViewById(R.id.iForgot);
        i3 = (ImageView)findViewById(R.id.imageI3);
    }




}


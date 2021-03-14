package com.example.logindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Booking extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    String[] country = { "Select Pick Up", "Paundha", "Bidholi", "Prem Nagar", "Ballupur", "Clock Tower"};
    String RUID , RDate , RPickUpLoc , RTime;

 private TextView Book;
 private Button SignOut;
 private Button BookARide;
 private ImageView EBike;
 private Spinner spin;
 private DatePicker datePicker ;
         private TimePicker timePicker;

 FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        Book=(TextView)findViewById(R.id.iBooking);
        SignOut=(Button)findViewById(R.id.iSignOut);
        BookARide=(Button)findViewById(R.id.iBookARide);
        EBike=(ImageView)findViewById(R.id.iEBike);
        timePicker=(TimePicker) findViewById(R.id.iTimePicker);
        datePicker= (DatePicker) findViewById(R.id.iDatePicker);
        mAuth = FirebaseAuth.getInstance();

        Spinner spin = (Spinner) findViewById(R.id.iSpinner);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        BookARide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBookingDetails();
                Toast.makeText(getApplicationContext(),"Booking Done",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),MapsActivity.class));
            }
        });
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
                //startActivity(new Intent(Booking.this, MainActivity.class));
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        RPickUpLoc= country[position];

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    private void sendBookingDetails()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(mAuth.getUid());
        NewRide newRide= new NewRide(RUID, RDate, RPickUpLoc, RTime);
        myRef.setValue(newRide);
    }
}

package com.example.logindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DashBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_board_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DashBoardFragment.newInstance())
                    .commitNow();
        }
    }
}

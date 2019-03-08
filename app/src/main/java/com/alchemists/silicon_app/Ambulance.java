package com.alchemists.silicon_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Ambulance extends AppCompatActivity {

    public void loginUser(View view){
        Intent i = new Intent(getApplicationContext(),MapsActivity.class);
        startActivity(i);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);
    }
}

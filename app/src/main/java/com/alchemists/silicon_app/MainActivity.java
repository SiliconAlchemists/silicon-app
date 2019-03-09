package com.alchemists.silicon_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView user = (TextView) findViewById(R.id.user_button);

        user.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link NumbersActivity}
                Intent userIntent = new Intent(MainActivity.this, User.class);

                // Start the new activity
                startActivity(userIntent);
            }
        });
        TextView ambulance = findViewById(R.id.ambulance_button);

        // Set a click listener on that View
        ambulance.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the family category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link FamilyActivity}
                Intent ambulanceIntent = new Intent(MainActivity.this, Ambulance.class);

                // Start the new activity
                startActivity(ambulanceIntent);
            }
        });
    }
}

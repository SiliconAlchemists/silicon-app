package com.alchemists.silicon_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    TextView helloText;
    LinearLayout detailLayout;
    TextView detailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        detailLayout = findViewById(R.id.detailLayout);
        detailText = detailLayout.findViewById(R.id.userDetails);
        helloText=findViewById(R.id.name_text);
        createSummery();

    }
    public String createSummery(){
        String onlyName;
        String Message="Name :"+UserData.get().getName();
        onlyName = UserData.get().getName();
        Message=Message+"\nEmail:"+UserData.get().getEmail();
        detailText.setText(Message);
        helloText.setText((helloText.getText()+onlyName));

        return Message;
    }
        private void displayMessage(String message) {
            TextView SummaryTextView = (TextView) findViewById(R.id.details_text);
            SummaryTextView.setText(message);
        }
    }


package com.alchemists.silicon_app;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class UserDashboard extends AppCompatActivity {

    TextView helloText;
    LinearLayout detailLayout;
    TextView detailText;
    private FusedLocationProviderClient fusedLocationClient;
    JSONObject obj;
    public OkHttpClient client;
    Location currLoc;
    String driverName;
    String driverPhone;
    String driverLat;
    String driverLongitude;
    private final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {

            webSocket.send(obj.toString());

        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {

            try {
                JSONObject obj = new JSONObject(text);
                driverName = obj.getString("name");
                driverPhone = obj.getString("phone");
                driverLat = obj.getString("latitude");
                driverLongitude = obj.getString("longitude");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void reqCon() {
        Request request = new Request.Builder().url("ws://10.177.7.176:3006/sos").build();
        Request newReq = new Request.Builder().url("ws://10.177.7.176:3006/getGyroPhoneTest").build();

        UserDashboard.EchoWebSocketListener listener = new UserDashboard.EchoWebSocketListener();

        WebSocket ws = client.newWebSocket(request, listener);

        WebSocket newWS = client.newWebSocket(newReq, listener);
        client.dispatcher().executorService().shutdown();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        detailLayout = findViewById(R.id.detailLayout);
        detailText = detailLayout.findViewById(R.id.userDetails);
        helloText=findViewById(R.id.name_text);
        createSummery();
        obj = new JSONObject();
        client = new OkHttpClient();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }
    public void sendSOS(View view){
        Toast.makeText(getApplicationContext(), "pressed SOS", Toast.LENGTH_SHORT).show();
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            currLoc = location;
//                            updateMap(location);
                            try {
                                obj.put("latitude",location.getLatitude());
                                obj.put("longitude",location.getLongitude());
                                obj.put("email", UserSingleton.get().getEmail());

                                reqCon();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("jsonTag",e.toString());
                            }

                        }
                    }
                });

    }
    public String createSummery(){
        String onlyName;
        String Message="Name :"+ UserSingleton.get().getName();
        onlyName = UserSingleton.get().getName();
        Message=Message+"\nEmail:"+ UserSingleton.get().getEmail();
        detailText.setText(Message);
        helloText.setText((helloText.getText()+onlyName));

        return Message;
    }
        private void displayMessage(String message) {
            TextView SummaryTextView = (TextView) findViewById(R.id.details_text);
            SummaryTextView.setText(message);
        }
    }


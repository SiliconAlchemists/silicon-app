package com.alchemists.silicon_app;

import android.content.Intent;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    public OkHttpClient client;
    Location currLocation;


    private final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            JSONObject signedIn = new JSONObject();
            try {
                signedIn.put("status","ready");
                signedIn.put("email",DriverSingleton.get().getEmail());
                webSocket.send(signedIn.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


        @Override
        public void onMessage(WebSocket webSocket, String text) {

            try {
                JSONObject recievedObj = new JSONObject(text);
                Log.d("JsonValue",recievedObj.toString());
                if(recievedObj.getString("action").equals("getCurrentLocation")){
                    JSONObject returnObj = new JSONObject();
                    returnObj.put("status","locationResponse");
                    returnObj.put("latitude",currLocation.getLatitude());
                    returnObj.put("longitude",currLocation.getLongitude());
                    returnObj.put("email",DriverSingleton.get().getEmail());
                    webSocket.send(returnObj.toString());

                }else if(recievedObj.getString("action").equals("startNavigation")){
                    String userName = recievedObj.getString("name");
                    String userPhone = recievedObj.getString("phone");
                    String userLat = recievedObj.getString("latitude");
                    String userLong = recievedObj.getString("longitude");
                    Log.d("ReceivedInfo",recievedObj.toString());
                }
            } catch (JSONException e) {
                Log.d("ExceptionRec",e.toString());
                e.printStackTrace();
            }

        }
    }



    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    public void updateMap(Location newLoc){
        LatLng latest = new LatLng(newLoc.getLatitude(),newLoc.getLongitude());

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latest));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latest,14));

        mMap.addMarker(new MarkerOptions().position(latest).title("My Loc"));

    }
    private void start() {
        Request request = new Request.Builder().url("ws://10.177.7.176:3006/driverReady").build();
        MapsActivity.EchoWebSocketListener listener = new MapsActivity.EchoWebSocketListener();
        WebSocket ws = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            currLocation = location;
                            updateMap(location);
                        }
                    }
                });

        client = new OkHttpClient();
        start();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}

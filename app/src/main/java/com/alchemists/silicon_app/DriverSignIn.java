package com.alchemists.silicon_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class DriverSignIn extends AppCompatActivity {

    public void loginUser(View view){
        Intent i = new Intent(getApplicationContext(),TestSend.class);
        startActivity(i);


    }
    public OkHttpClient client;

    EditText email;
    EditText password;
    Button driverLoginBtn;


    String emailVal;
    String passVal;


    private final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            String emailvar = "Silicon@alchemist.com";
            String passvar = "passsssss";
            emailVal = email.getText().toString();
            passVal = password.getText().toString();
            JSONObject driverLog = new JSONObject();
            try {
                driverLog.put("email", emailVal);
                driverLog.put("password", passVal);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            webSocket.send(driverLog.toString());

        }

        public void updateDriverSingleton(String email,String name){
            DriverSingleton.get().setEmail(email);
            DriverSingleton.get().setName(name);
        }



        @Override
        public void onMessage(WebSocket webSocket, String text) {
            JSONObject obj=null;
            //Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            Log.d("ReplyInfo",text);
            try {
                obj = new JSONObject(text);

                Log.d("MyApp", obj.toString());


            if(obj.getString("response").equals("success")){
                Log.d("LoginStatus","Success");


                updateDriverSingleton(obj.getString("email"),obj.getString("name"));


                JSONObject loginReply = new JSONObject();
                loginReply.put("status","ready");
                loginReply.put("email",obj.getString("email"));

                //webSocket.send(loginReply.toString());

                Intent i = new Intent(DriverSignIn.this,MapsActivity.class);
                startActivity(i);
            }else{
                Log.d("LoginStatus","Failed");
            }
            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + text + "\"");
            }
        }


        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
            Toast.makeText(getApplicationContext(), "Conn closed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            Toast.makeText(getApplicationContext(), "nigger", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
//            output("Closing : " + code + " / " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
//            output("Error : " + t.getMessage());
        }
    }
    private void start() {
        Request request = new Request.Builder().url("ws://10.177.7.176:3006/signinwsambulance").build();
        DriverSignIn.EchoWebSocketListener listener = new DriverSignIn.EchoWebSocketListener();
        WebSocket ws = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);
        email = findViewById(R.id.driverEmail);
        password = findViewById(R.id.driverPassword);


        client = new OkHttpClient();

        driverLoginBtn = findViewById(R.id.driverLogInBtn);
        driverLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });

    }

}

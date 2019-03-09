package com.alchemists.silicon_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class RegisterData extends AppCompatActivity {

    private OkHttpClient client;

    EditText name;
    EditText email;
    EditText age;
    EditText sex;
    EditText password;

    JSONObject student1;

    public void sendData(View view){
        String nameVal = name.getText().toString();
        String emailVal = email.getText().toString();
        String ageVal = age.getText().toString();
        String sexVal = sex.getText().toString();
        String passVal = password.getText().toString();

        student1 = new JSONObject();
        try {
            student1.put("email", emailVal);
            student1.put("password", passVal);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {

            webSocket.send(student1.toString());

        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        }
    }
    private void start() {
        Request request = new Request.Builder().url("ws://10.177.7.176:3006/echo").build();
        RegisterData.EchoWebSocketListener listener = new RegisterData.EchoWebSocketListener();
        WebSocket ws = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_data);
        name = findViewById(R.id.name_id);
        email = findViewById(R.id.email_id);
        age = findViewById(R.id.age_id);
        sex = findViewById(R.id.sex_id);
        password = findViewById(R.id.pass_id);
    }

}

package com.alchemists.silicon_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class UserSignIn extends AppCompatActivity {

    public OkHttpClient client;

    EditText email;
    EditText password;

    Button regiser ;

    JSONObject student1;

    public void sendData(){
        String emailVal = email.getText().toString();
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
    private final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            String emailvar = "Silicon@alchemist.com";
            String passvar = "passsssss";
            String emailVal = email.getText().toString();
            String passVal = password.getText().toString();
            JSONObject student1 = new JSONObject();
            try {
                student1.put("email", emailVal);
                student1.put("password", passVal);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            webSocket.send(student1.toString());

        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
//           Log.d("ReplyInfo",text);
           String four="404ncc";
            JSONObject obj;
            try {
                obj = new JSONObject(text);

            if(text!=four){
//                Log.d("ReplyInfo",text);
                UserSingleton.get().setEmail(obj.getString("email"));
                UserSingleton.get().setName(obj.getString("username"));
                Intent DashboardIntent = new Intent(UserSignIn.this, UserDashboard.class);
                startActivity(DashboardIntent);
            }
            else {
                //login failed
                Log.d("ReplyInfo","else");
            }
            //convert text  to strings and assign em
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("ReplyInfo","catch", e);
            }
        }


        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
//            output("Receiving bytes : " + bytes.hex());
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
        Request request = new Request.Builder().url("ws://10.177.7.176:3006/signinwsuser").build();
        UserSignIn.EchoWebSocketListener listener = new UserSignIn.EchoWebSocketListener();
        WebSocket ws = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        final TextView user = (TextView) findViewById(R.id.registertextview);

        user.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link NumbersActivity}
                Intent RegisterIntent = new Intent(UserSignIn.this, RegisterUser.class);

                // Start the new activity
                startActivity(RegisterIntent);
            }
        });
        regiser = findViewById(R.id.registerBtn);
        regiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });


        client = new OkHttpClient();


        email = findViewById(R.id.emailVal);
        password = findViewById(R.id.passVal);
    }
}

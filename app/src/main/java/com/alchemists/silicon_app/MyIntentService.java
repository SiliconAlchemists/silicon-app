package com.alchemists.silicon_app;

import android.app.IntentService;
import android.content.Intent;

public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    public MyIntentService(String name) {
        // Used to name the worker thread
        // Important only for debugging
        super(MyIntentService.class.getName());
        setIntentRedelivery(true);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        startActivity(intent);
        // Invoked on the worker thread
        // Do some work in background without affecting the UI thread
    }
}

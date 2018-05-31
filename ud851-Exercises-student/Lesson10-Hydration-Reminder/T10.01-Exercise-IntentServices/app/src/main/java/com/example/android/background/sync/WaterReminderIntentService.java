package com.example.android.background.sync;

// completed (9) Create WaterReminderIntentService and extend it from IntentService
//  completed (10) Create a default constructor that calls super with the name of this class
//  completed (11) Override onHandleIntent
//      completed (12) Get the action from the Intent that started this Service
//      completed (13) Call ReminderTasks.executeTaskForTag and pass in the action to be performed

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class WaterReminderIntentService extends IntentService{

    public WaterReminderIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        ReminderTasks.executeTask(this, action);
    }
}
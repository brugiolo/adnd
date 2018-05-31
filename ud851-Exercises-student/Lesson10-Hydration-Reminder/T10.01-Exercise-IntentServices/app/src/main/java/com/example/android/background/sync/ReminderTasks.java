package com.example.android.background.sync;

// completed (1) Create a class called ReminderTasks
// completed (2) Create a public static constant String called ACTION_INCREMENT_WATER_COUNT
// completed (3) Create a private static void method called incrementWaterCount
// completed (4) Add a Context called context to the argument list
// completed (5) From incrementWaterCount, call the PreferenceUtility method that will ultimately update the water count
// completed (6) Create a public static void method called executeTask
// completed (7) Add a Context called context and String parameter called action to the parameter list
// completed (8) If the action equals ACTION_INCREMENT_WATER_COUNT, call this class's incrementWaterCount

import android.content.Context;

import com.example.android.background.utilities.PreferenceUtilities;

public class ReminderTasks {

    public static String ACTION_INCREMENT_WATER_COUNT;

    private static void incrementWaterCount(Context context) {
        PreferenceUtilities.incrementWaterCount(context);
    }

    public static void executeTask(Context context, String action){
        if (action.equals(ACTION_INCREMENT_WATER_COUNT)){
            incrementWaterCount(context);
        }
    }
}

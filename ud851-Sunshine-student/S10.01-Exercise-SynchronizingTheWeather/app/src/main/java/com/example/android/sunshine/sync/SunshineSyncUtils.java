package com.example.android.sunshine.sync;

import android.content.Context;
import android.content.Intent;

import com.example.android.sunshine.sync.SunshineSyncIntentService;

// completed (9) Create a class called SunshineSyncUtils
public class SunshineSyncUtils {
    public static void startImmediateSync(Context context) {
        Intent intentToSyncImmediately = new Intent(context, SunshineSyncIntentService.class);
        context.startService(intentToSyncImmediately);
    }
}
    //  completed (10) Create a public static void method called startImmediateSync
    //  completed (11) Within that method, start the SunshineSyncIntentService
package com.example.rafael.famousmovies.utilities;

/**
 * Created by Rafael on 19/04/2018.
 */

import android.app.Application;
import android.content.Context;

public class ApplicationContextProvider extends Application {
    /**
     * Keeps a reference of the application context
     */
    private static Context sContext;
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }
    /**
     * Returns the application context
     *
     * @return application context
     */
    public static Context getContext() {
        return sContext;
    }
}
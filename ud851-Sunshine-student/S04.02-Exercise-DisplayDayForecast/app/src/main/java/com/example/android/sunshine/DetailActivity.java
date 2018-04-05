package com.example.android.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";

    private TextView mDisplayWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // complete (2) Display the weather forecast that was passed from MainActivity
        mDisplayWeather = (TextView) findViewById(R.id.tv_display_data);

        Intent startedIntent = getIntent();

        if (startedIntent.hasExtra(Intent.EXTRA_TEXT)){
            String extraText = startedIntent.getStringExtra(Intent.EXTRA_TEXT);
            mDisplayWeather.setText(extraText);
        }
    }
}
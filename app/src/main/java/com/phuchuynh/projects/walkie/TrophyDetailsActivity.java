package com.phuchuynh.projects.walkie;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

public class TrophyDetailsActivity extends AppCompatActivity {

    private CollapsingToolbarLayout trophyDetailsCollapsingToolBar;
    private TextView locationTitleTextView;
    private TextView locationDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trophy_details);

        trophyDetailsCollapsingToolBar = findViewById(R.id.trophyDetailsCollapsingToolBar);
        locationTitleTextView = findViewById(R.id.locationTitleTextView);
        locationDescriptionTextView = findViewById(R.id.locationDescriptionTextView);

        Trophy trophy = getIntent().getParcelableExtra("Trophy");

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(trophy.getObjective());

        locationTitleTextView.setText(trophy.getLocationTitle());
        locationDescriptionTextView.setText(trophy.getDescription());

    }
}

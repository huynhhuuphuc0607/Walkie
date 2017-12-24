package com.phuchuynh.projects.walkie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class TrophyActivity extends AppCompatActivity {

    private ListView trophyListView;
    private TrophyListAdpapter mTrophyListAdpapter;
    private List<Trophy> mTrophies;

    private Toolbar mToolbar;

    private DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trophy);

        mDBHelper = new DBHelper(this);

        setSupportActionBar((android.support.v7.widget.Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Phuc Huynh");

        trophyListView = findViewById(R.id.trophyListView);

        populateTrophiesDatabase();
        mTrophies = mDBHelper.getAllTrophies();

        mTrophyListAdpapter = new TrophyListAdpapter(this,R.layout.trophy_row_layout, mTrophies);
        trophyListView.setAdapter(mTrophyListAdpapter);
    }

    private void populateTrophiesDatabase()
    {
        mDBHelper.deleteAllTrophies();
        mDBHelper.importTrophiesFromCSV("trophies.csv");
    }
}

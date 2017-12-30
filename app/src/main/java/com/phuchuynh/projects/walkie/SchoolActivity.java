package com.phuchuynh.projects.walkie;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class SchoolActivity extends AppCompatActivity {

    private TabLayout schoolTabLayout;
    private ViewPager schoolViewPager;
    private DBHelper mDBHelper;

    private ArrayList<School> universities;
    private ArrayList<School> stateUniversities;
    private ArrayList<School> communityColleges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);

        schoolTabLayout = findViewById(R.id.schoolTabLayout);
        schoolViewPager = findViewById(R.id.schoolViewPager);
        mDBHelper = new DBHelper(this);
        populateSchoolsDatabase();

        universities = mDBHelper.getSchools(0);
        stateUniversities = mDBHelper.getSchools(1);
        communityColleges = mDBHelper.getSchools(2);

        schoolViewPager.setAdapter(new SchoolFragmentPagerAdapter(getSupportFragmentManager(),this,
                universities, stateUniversities, communityColleges));
        schoolTabLayout.setupWithViewPager(schoolViewPager);
    }

    private void populateSchoolsDatabase()
    {
        mDBHelper.deleteAllSchools();
        mDBHelper.importSchoolsFromCSV("schools.csv");
    }
}

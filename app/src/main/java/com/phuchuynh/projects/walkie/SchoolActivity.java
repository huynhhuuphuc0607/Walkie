package com.phuchuynh.projects.walkie;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SchoolActivity extends AppCompatActivity implements OnMapReadyCallback, ViewPager.OnPageChangeListener {

    private TabLayout schoolTabLayout;
    private ViewPager schoolViewPager;
    private int currentPage;

    private DBHelper mDBHelper;

    private ArrayList<School> universities;
    private ArrayList<School> stateUniversities;
    private ArrayList<School> communityColleges;

    private SupportMapFragment mSupportMapFragment;
    private GoogleMap mGoogleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);

        schoolTabLayout = findViewById(R.id.schoolTabLayout);
        schoolViewPager = findViewById(R.id.schoolViewPager);
        mSupportMapFragment =(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);

        mDBHelper = new DBHelper(this);
        populateSchoolsDatabase();

        universities = mDBHelper.getSchools(0);
        stateUniversities = mDBHelper.getSchools(1);
        communityColleges = mDBHelper.getSchools(2);

        schoolViewPager.setAdapter(new SchoolFragmentPagerAdapter(getSupportFragmentManager(),this,
                universities, stateUniversities, communityColleges));
        schoolViewPager.addOnPageChangeListener(this);
        schoolTabLayout.setupWithViewPager(schoolViewPager);

        mSupportMapFragment.getMapAsync(this);
    }

    private void populateSchoolsDatabase()
    {
        mDBHelper.deleteAllSchools();
        mDBHelper.importSchoolsFromCSV("schools.csv");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        loadSchoolMarkers(currentPage);
    }

    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = null;
        Bitmap resizedBitmap = null;
        try {
            imageBitmap = BitmapFactory.decodeStream(getAssets().open(iconName));
            resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resizedBitmap;
    }
    private void loadSchoolMarkers(int type)
    {
        mGoogleMap.clear();
        CameraPosition cameraPosition = null;

        switch (type)
        {
            case 0:
                for(School school : universities)
                    mGoogleMap.addMarker(
                            new MarkerOptions().title(school.getName().replace("\\\\,",",")).
                                    position(school.getLatLng()).
                                    icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(school.getImageName(),100,100))));;
                cameraPosition = new CameraPosition.Builder().target(universities.get(0).getLatLng()).zoom(10.0f).build();
                break;

            case 1:
                for(School school : stateUniversities)
                    mGoogleMap.addMarker(
                            new MarkerOptions().title(school.getName().replace("\\\\,",",")).
                                    position(new LatLng(school.getLatitude(),school.getLongitude())).
                                    icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(school.getImageName(),100,100))));
                cameraPosition = new CameraPosition.Builder().target(stateUniversities.get(0).getLatLng()).zoom(10.0f).build();
                break;

            case 2:
                for(School school : communityColleges)
                    mGoogleMap.addMarker(
                            new MarkerOptions().title(school.getName().replace("\\\\,",",")).
                                    position(new LatLng(school.getLatitude(),school.getLongitude())).
                                    icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(school.getImageName(),100,100))));;
                cameraPosition = new CameraPosition.Builder().target(communityColleges.get(0).getLatLng()).zoom(10.0f).build();
                break;
        }

        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mGoogleMap.animateCamera(cameraUpdate);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPage = position;
        loadSchoolMarkers(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void seeSchoolOnMap(View v)
    {
        School school = (School) v.getTag();
        CameraPosition cameraPosition = new CameraPosition.Builder().target(school.getLatLng()).zoom(14.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mGoogleMap.animateCamera(cameraUpdate);
    }
}

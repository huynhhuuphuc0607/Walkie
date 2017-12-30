package com.phuchuynh.projects.walkie;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HuynhHuu on 30-Dec-17.
 */

public class SchoolFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private Bundle mBundle;
    public SchoolFragmentPagerAdapter(FragmentManager fm, Context context,
                                      ArrayList<School> universities, ArrayList<School> stateUniversities, ArrayList<School> communityColleges) {
        super(fm);
        mContext = context;

        mBundle = new Bundle();

        mBundle.putParcelableArrayList("universities", universities);
        mBundle.putParcelableArrayList("stateUniversities", stateUniversities);
        mBundle.putParcelableArrayList("communityColleges", communityColleges);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                UniversitySchoolFragment fragment0 = new UniversitySchoolFragment();
                fragment0.setArguments(mBundle);
                return fragment0;
            case 1:
                StateCollegeFragment fragment1 = new StateCollegeFragment();
                fragment1.setArguments(mBundle);
                return fragment1;
            case 2:
                OtherSchoolsFragment fragment2 = new OtherSchoolsFragment();
                fragment2.setArguments(mBundle);
                return fragment2;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "UC's";
            case 1:
                return "CSU's";
            case 2:
                return "Others";
            default:
                return "Error";
        }
    }
}

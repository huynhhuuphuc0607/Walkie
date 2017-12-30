package com.phuchuynh.projects.walkie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class StateCollegeFragment extends Fragment {

    private ArrayList<School> mStateUniversities;
    private SchoolListAdapter mSchoolListAdapter;

    public StateCollegeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.school_fragment_page, container, false);
        ListView schoolListView = view.findViewById(R.id.schoolListView);

        mStateUniversities = getArguments().getParcelableArrayList("stateUniversities");
        mSchoolListAdapter = new SchoolListAdapter(getActivity(),R.layout.school_row_layout, mStateUniversities);
        schoolListView.setAdapter(mSchoolListAdapter);

        return view;
    }

}

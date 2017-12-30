package com.phuchuynh.projects.walkie;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HuynhHuu on 30-Dec-17.
 */

public class SchoolListAdapter extends ArrayAdapter {
    private Context mContext;
    private int resId;
    private List<School> schools;

    public SchoolListAdapter(@NonNull Context context, int resource, ArrayList<School> schools) {
        super(context, resource, schools);
        mContext = context;
        resId = resource;
        this.schools = schools;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)  mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(resId,null);

        TextView schoolNameTextView = v.findViewById(R.id.schoolNameTextView);
        ImageView schoolImageView = v.findViewById(R.id.schoolImageView);

        School school = schools.get(position);
        schoolNameTextView.setText(school.getName().replaceAll("\\\\\\\\,",","));

        v.setTag(school);
        return v;
    }
}

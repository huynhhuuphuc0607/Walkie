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

import java.util.List;

/**
 * Created by HuynhHuu on 24-Dec-17.
 */

public class TrophyListAdpapter extends ArrayAdapter<Trophy> {

    private Context mContext;
    private int resId;
    private List<Trophy> trophies;

    public TrophyListAdpapter(@NonNull Context context, int resource, List<Trophy> trophies) {
        super(context, resource, trophies);
        mContext = context;
        resId = resource;
        this.trophies = trophies;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)  mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(resId,null);

        TextView trophyObjectiveTextView = v.findViewById(R.id.trophyObjectiveTextView);
        ImageView trophyImageView = v.findViewById(R.id.trophyImageView);

        Trophy trophy = trophies.get(position);
        trophyObjectiveTextView.setText(trophy.getObjective());

        return v;
    }
}

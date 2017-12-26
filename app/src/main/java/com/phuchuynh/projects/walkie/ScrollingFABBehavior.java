package com.phuchuynh.projects.walkie;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by HuynhHuu on 26-Dec-17.
 */

public class ScrollingFABBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {
    private int toolBarHeight;
    private Context mContext;

    public ScrollingFABBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        toolBarHeight = getToolbarHeight(context);
        mContext = context;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        if(dependency instanceof  AppBarLayout)
        {
            if(dependency.getY() > toolBarHeight)
                child.hide();
            else
                child.show();
        }
        return true;
    }

    private  int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }
}

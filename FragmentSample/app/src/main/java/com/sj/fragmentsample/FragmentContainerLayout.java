package com.sj.fragmentsample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by sid on 21/01/2016.
 */
public class FragmentContainerLayout extends FrameLayout {

    public FragmentContainerLayout(Context context) {
        super(context);
    }

    public FragmentContainerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FragmentContainerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);

        // After the view is added find its index and set visiblity of every other view to false
        final int viewIndex = indexOfChild(child);
        final int childCount = getChildCount();
        for (int i=0;i<childCount;i++) {
            if (i != viewIndex) {
                getChildAt(i).setVisibility(GONE);
            }
        }
    }


    @Override
    public void onViewRemoved(View child) {
        super.onViewRemoved(child);

        // View added during fragment traction are added in sequential order
        // so after the fragment and its view is removed, then make visible the child added
        // just before that fragment view
        final int childCount = getChildCount();
        if (childCount > 0) {
            getChildAt(childCount-1).setVisibility(VISIBLE);
        }
    }
}

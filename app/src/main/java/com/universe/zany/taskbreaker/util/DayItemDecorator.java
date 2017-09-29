package com.universe.zany.taskbreaker.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;


public class DayItemDecorator extends RecyclerView.ItemDecoration {

    private int spacing;

    public DayItemDecorator(int offset) {
        this.spacing = offset;
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        super.getItemOffsets(outRect, itemPosition, parent);

        outRect.top = spacing;
        outRect.left = spacing;
        outRect.right = spacing;
        outRect.bottom = spacing;
    }
}

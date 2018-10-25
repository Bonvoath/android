package com.example.bonvoath.tms.utils.swipes;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class SwipeFrameLayout extends FrameLayout {

    private SwipeDismissTouchListener mTouchListener;

    public SwipeFrameLayout(Context context) {
        super(context);
    }

    public void setSwipeDismissTouchListener(SwipeDismissTouchListener touchListener) {
        mTouchListener = touchListener;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mTouchListener != null) {
            if (mTouchListener.onTouch(this, ev)) {
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }
}

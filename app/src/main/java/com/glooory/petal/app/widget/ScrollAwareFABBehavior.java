package com.glooory.petal.app.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

/**
 * Created by Glooory on 2016/9/3 0003.
 */
public class ScrollAwareFABBehavior extends FloatingActionButton.Behavior {

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private boolean mIsAnimatingOut = false; //fab 是否被 压出

    public ScrollAwareFABBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
            View directTargetChild, View target, int nestedScrollAxes) {
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes)
                || nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
            View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

        if (dyConsumed > 0 && !this.mIsAnimatingOut && child.getVisibility() == View.VISIBLE) {
            animateOut(child);
        } else if (dyConsumed < 0 && this.mIsAnimatingOut && child.getVisibility() != View.VISIBLE) {
            animateIn(child);
        }
    }

    private void animateOut(FloatingActionButton button) {
        ViewCompat.animate(button)
                .translationY(button.getHeight() + getMarginBottom(button))
                .setInterpolator(INTERPOLATOR)
                .withLayer()
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        ScrollAwareFABBehavior.this.mIsAnimatingOut = true;
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        view.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        ScrollAwareFABBehavior.this.mIsAnimatingOut = false;
                    }
                }).start();

    }

    private void animateIn(FloatingActionButton button) {
        button.setVisibility(View.VISIBLE);
        ViewCompat.animate(button)
                .translationY(0)
                .setInterpolator(INTERPOLATOR)
                .withLayer()
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        ScrollAwareFABBehavior.this.mIsAnimatingOut = false;
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        view.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        ScrollAwareFABBehavior.this.mIsAnimatingOut = true;
                    }
                }).start();
    }

    private int getMarginBottom(View view) {
        int marginBottom = 0;
        final ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            marginBottom = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        }
        return marginBottom;
    }
}

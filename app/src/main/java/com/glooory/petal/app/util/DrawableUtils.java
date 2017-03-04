package com.glooory.petal.app.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

import com.glooory.petal.R;
import com.glooory.petal.mvp.model.entity.PinBean;

import common.PEApplication;

/**
 * Created by Glooory on 17/2/18.
 */

public final class DrawableUtils {

    private DrawableUtils(){
    }

    public static Drawable getTintListDrawable(Context context, int drawableResId, int tintResId) {
        Drawable drawable = DrawableCompat.wrap(ContextCompat
                .getDrawable(context, drawableResId).mutate());
        DrawableCompat.setTintList(drawable, ContextCompat.getColorStateList(context, tintResId));
        return drawable;
    }

    public static Drawable getTintDrawable(Context context, int drawableResId, @ColorInt int tint) {
        Drawable drawable = DrawableCompat.wrap(ContextCompat
                .getDrawable(context, drawableResId).mutate());
        DrawableCompat.setTint(drawable, tint);
        return drawable;
    }

    public static Drawable getColoredPlaceHolderDrawable(PinBean pinBean) {
        ColorDrawable colorDrawable;
        if (pinBean.getFile().getColors() != null && pinBean.getFile().getColors().size() > 0) {
            int color = pinBean.getFile().getColors().get(0).getColor();
            if (color == 0) {
                colorDrawable = new ColorDrawable(ContextCompat.getColor(PEApplication.getContext(),
                        R.color.grey_1000));
            } else {
                String hexColor = Integer.toHexString(color);
                if (hexColor.length() < 6) {
                    if (hexColor.length() == 1) {
                        hexColor = "00000" + hexColor;
                    }
                    if (hexColor.length() == 2) {
                        hexColor = "0000" + hexColor;
                    }
                    if (hexColor.length() == 3) {
                        hexColor = "000" + hexColor;
                    }
                    if (hexColor.length() == 4) {
                        hexColor = "00" + hexColor;
                    }
                    if (hexColor.length() == 5) {
                        hexColor = "0" + hexColor;
                    }
                }
                colorDrawable = new ColorDrawable(Color.parseColor("#" + hexColor));
            }
        } else {
            colorDrawable = new ColorDrawable(ContextCompat.getColor(PEApplication.getContext(),
                    R.color.grey_a700));
        }
        return colorDrawable;
    }
}

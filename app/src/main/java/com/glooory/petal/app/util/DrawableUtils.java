package com.glooory.petal.app.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;

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
        if (TextUtils.isEmpty(pinBean.getFile().getTheme())) {
            colorDrawable = new ColorDrawable(ContextCompat.getColor(PEApplication.getContext(),
                    R.color.grey_a700));
        } else {
            colorDrawable = new ColorDrawable(Color.parseColor("#" + pinBean.getFile().getTheme()));
        }
        return colorDrawable;
    }

    public static String getBasicColorStr(PinBean pinBean) {
        if (TextUtils.isEmpty(pinBean.getFile().getTheme())) {
            return "#616161";
        } else {
            return "#" + pinBean.getFile().getTheme();
        }
    }

    public static Drawable getColoredDrwable(String colorStr) {
        return new ColorDrawable(Color.parseColor(colorStr));
    }
}

package com.glooory.petal.app.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

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
}

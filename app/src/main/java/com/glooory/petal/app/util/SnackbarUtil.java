package com.glooory.petal.app.util;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.glooory.petal.R;

/**
 * Created by Glooory on 17/3/13.
 */

public class SnackbarUtil {

    private SnackbarUtil() {
    }

    public static void showLong(Activity activity, int messageId) {
        SnackbarUtil.show(toView(activity), messageId, Snackbar.LENGTH_LONG);
    }

    public static void showLong(Activity activity, String message) {
        SnackbarUtil.show(toView(activity), message, Snackbar.LENGTH_LONG);
    }

    public static void showLong(View view, int messageId) {
        SnackbarUtil.show(view, messageId, Snackbar.LENGTH_LONG);
    }

    public static void showLong(View view, String message) {
        SnackbarUtil.show(view, message, Snackbar.LENGTH_LONG);
    }

    public static void showShort(Activity activity, int messageId) {
        SnackbarUtil.show(toView(activity), messageId, Snackbar.LENGTH_SHORT);
    }

    public static void showShort(Activity activity, String message) {
        SnackbarUtil.show(toView(activity), message, Snackbar.LENGTH_SHORT);
    }

    public static void showShort(View view, int messageId) {
        SnackbarUtil.show(view, messageId, Snackbar.LENGTH_SHORT);
    }

    public static void showShort(View view, String message) {
        SnackbarUtil.show(view, message, Snackbar.LENGTH_SHORT);
    }

    private static void show(View view, int messageId, int duration) {
        Snackbar.make(view, messageId, duration).show();
    }

    private static void show(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

    private static View toView(Activity activity) {
        View view = null;
        try {
            view = activity.findViewById(R.id.coordinator);
            if (view == null) {
                view = activity.findViewById(android.R.id.content);
            }
        } catch (Exception ignored) {
        }
        return view;
    }
}

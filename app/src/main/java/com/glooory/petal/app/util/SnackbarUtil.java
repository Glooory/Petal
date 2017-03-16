package com.glooory.petal.app.util;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.glooory.petal.R;
import com.jess.arms.base.BaseApplication;

import common.PEApplication;

/**
 * Created by Glooory on 17/3/13.
 */

public class SnackbarUtil {

    private SnackbarUtil() {
    }

    public static void showLong(int message) {
        showLong(
                ((BaseApplication) PEApplication.getContext()).getAppManager().getCurrentActivity(),
                message
        );
    }

    public static void showLong(String message) {
        showLong(
                ((BaseApplication) PEApplication.getContext()).getAppManager().getCurrentActivity(),
                message
        );
    }

    public static void showShort(int message) {
        showShort(
                ((BaseApplication) PEApplication.getContext()).getAppManager().getCurrentActivity(),
                message
        );
    }

    public static void showShort(String message) {
        showShort(
                ((BaseApplication) PEApplication.getContext()).getAppManager().getCurrentActivity(),
                message
        );
    }

    public static void showLong(Activity activity, int messageId) {
        if (activity == null) {
            return;
        }
        SnackbarUtil.show(toView(activity), messageId, Snackbar.LENGTH_LONG);
    }

    public static void showLong(Activity activity, String message) {
        if (activity == null) {
            return;
        }
        SnackbarUtil.show(toView(activity), message, Snackbar.LENGTH_LONG);
    }

    public static void showLong(Activity activity, int messageResId,
            int actionMsgResId, View.OnClickListener onClickListener) {
        if (activity == null) {
            return;
        }
        SnackbarUtil.show(toView(activity), messageResId, Snackbar.LENGTH_LONG,
                actionMsgResId, onClickListener);
    }

    public static void showLong(Activity activity, String message,
            String actionMsg, View.OnClickListener onClickListener) {
        if (activity == null) {
            return;
        }
        SnackbarUtil.show(toView(activity), message, Snackbar.LENGTH_LONG,
                actionMsg, onClickListener);
    }

    public static void showLong(View view, int messageId) {
        SnackbarUtil.show(view, messageId, Snackbar.LENGTH_LONG);
    }

    public static void showLong(View view, String message) {
        SnackbarUtil.show(view, message, Snackbar.LENGTH_LONG);
    }

    public static void showLong(View view, int messageResId,
            int actionMsgResId, View.OnClickListener onClickListener) {
        SnackbarUtil.show(view, messageResId, Snackbar.LENGTH_LONG,
                actionMsgResId, onClickListener);
    }

    public static void showLong(View view, String message,
            String actionMsg, View.OnClickListener onClickListener) {
        SnackbarUtil.show(view, message, Snackbar.LENGTH_LONG,
                actionMsg, onClickListener);
    }

    public static void showShort(Activity activity, int messageId) {
        if (activity == null) {
            return;
        }
        SnackbarUtil.show(toView(activity), messageId, Snackbar.LENGTH_SHORT);
    }

    public static void showShort(Activity activity, String message) {
        if (activity == null) {
            return;
        }
        SnackbarUtil.show(toView(activity), message, Snackbar.LENGTH_SHORT);
    }

    public static void showShort(View view, int messageId) {
        SnackbarUtil.show(view, messageId, Snackbar.LENGTH_SHORT);
    }

    public static void showShort(View view, String message) {
        SnackbarUtil.show(view, message, Snackbar.LENGTH_SHORT);
    }

    public static void showShort(Activity activity, int messageResId,
            int actionMsgResId, View.OnClickListener onClickListener) {
        if (activity == null) {
            return;
        }
        SnackbarUtil.show(toView(activity), messageResId, Snackbar.LENGTH_SHORT,
                actionMsgResId, onClickListener);
    }

    public static void showShort(Activity activity, String message,
            String actionMsg, View.OnClickListener onClickListener) {
        if (activity == null) {
            return;
        }
        SnackbarUtil.show(toView(activity), message, Snackbar.LENGTH_SHORT,
                actionMsg, onClickListener);
    }

    public static void showShort(View view, int messageResId,
            int actionMsgResId, View.OnClickListener onClickListener) {
        SnackbarUtil.show(view, messageResId, Snackbar.LENGTH_SHORT,
                actionMsgResId, onClickListener);
    }

    public static void showShort(View view, String message,
            String actionMsg, View.OnClickListener onClickListener) {
        SnackbarUtil.show(view, message, Snackbar.LENGTH_SHORT,
                actionMsg, onClickListener);
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

    private static void show(View view, int messageResId, int duration,
            int actionMsgResId, View.OnClickListener onClickListener) {
        Snackbar.make(view, messageResId, duration)
                .setAction(actionMsgResId, onClickListener)
                .show();
    }

    private static void show(View view, int messageResId, int duration,
            CharSequence actionMsg, View.OnClickListener onClickListener) {
        Snackbar.make(view, messageResId, duration)
                .setAction(actionMsg, onClickListener)
                .show();
    }

    private static void show(View view, String message, int duration,
            int actionMsgResId, View.OnClickListener onClickListener) {
        Snackbar.make(view, message, duration)
                .setAction(actionMsgResId, onClickListener)
                .show();
    }

    private static void show(View view, String message, int duration,
             CharSequence actionMsg, View.OnClickListener onClickListener) {
        Snackbar.make(view, message, duration)
                .setAction(actionMsg, onClickListener)
                .show();
    }
}

package com.glooory.petal.app.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

/**
 * Created by Glooory on 17/3/21.
 */

public class DialogUtils {

    private DialogUtils() {
    }

    public static void show(@NonNull Context context, String message, String positiveBtnMsg,
            DialogInterface.OnClickListener positiveClickListener) {
        show(context, null, message, null, null, positiveBtnMsg,
                null, null, positiveClickListener);
    }

    public static void show(@NonNull Context context, String message, String negativeBtnMsg,
            String positiveBtnMsg, DialogInterface.OnClickListener negativeClickListener,
            DialogInterface.OnClickListener positiveClickListener) {
        show(context, null, message, null, negativeBtnMsg, positiveBtnMsg,
                null, negativeClickListener, positiveClickListener);
    }

    public static void show(@NonNull Context context, String title, String message, String negativeBtnMsg,
            String positiveBtnMsg, DialogInterface.OnClickListener negativeClickListener,
            DialogInterface.OnClickListener positiveClickListener) {
        show(context, title, message, null, negativeBtnMsg, positiveBtnMsg,
                null, negativeClickListener, positiveClickListener);
    }

    public static void show(@NonNull Context context, String title, String message,
            String neutralBtnMsg, String negativeBtnMsg,
            String positiveBtnMsg, DialogInterface.OnClickListener neutralBtnClickListener,
            DialogInterface.OnClickListener negativeClickListener,
            DialogInterface.OnClickListener positiveClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (title != null) {
            builder.setTitle(title);
        }
        if (message != null) {
            builder.setMessage(message);
        }
        if (neutralBtnMsg != null) {
            builder.setNeutralButton(neutralBtnMsg, neutralBtnClickListener);
        }
        if (positiveBtnMsg != null) {
            builder.setPositiveButton(positiveBtnMsg, positiveClickListener);
        }
        if (negativeBtnMsg != null) {
            builder.setNegativeButton(negativeBtnMsg, negativeClickListener);
        }
        builder.show();
    }

    public static void show(@NonNull Context context, int messageResId, int positiveBtnMsgResId,
            DialogInterface.OnClickListener positiveClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(messageResId);
        builder.setPositiveButton(positiveBtnMsgResId, positiveClickListener);
        builder.show();
    }

    public static void show(@NonNull Context context, int messageResId,
            int negativeBtnMsgResId, int positiveBtnMsgResId,
            DialogInterface.OnClickListener negativeClickListener,
            DialogInterface.OnClickListener positiveClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(messageResId);
        builder.setNegativeButton(negativeBtnMsgResId, negativeClickListener)
                .setPositiveButton(positiveBtnMsgResId, positiveClickListener);
        builder.show();
    }

    public static void show(@NonNull Context context, int messageResId,
            int neutralBtnMsgResId, int negativeBtnMsgResId,
            int positiveBtnMsgResId, DialogInterface.OnClickListener neutralBtnClickListener,
            DialogInterface.OnClickListener negativeClickListener,
            DialogInterface.OnClickListener positiveClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(messageResId);
        builder.setNeutralButton(neutralBtnMsgResId, neutralBtnClickListener)
                .setNegativeButton(negativeBtnMsgResId, negativeClickListener)
                .setPositiveButton(positiveBtnMsgResId, positiveClickListener);
        builder.show();
    }

    public static void show(@NonNull Context context, int titleResId, int messageResId,
            int neutralBtnMsgResId, int negativeBtnMsgResId,
            int positiveBtnMsgResId, DialogInterface.OnClickListener neutralBtnClickListener,
            DialogInterface.OnClickListener negativeClickListener,
            DialogInterface.OnClickListener positiveClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titleResId);
        builder.setMessage(messageResId);
        builder.setNeutralButton(neutralBtnMsgResId, neutralBtnClickListener)
                .setNegativeButton(negativeBtnMsgResId, negativeClickListener)
                .setPositiveButton(positiveBtnMsgResId, positiveClickListener);
        builder.show();
    }
}

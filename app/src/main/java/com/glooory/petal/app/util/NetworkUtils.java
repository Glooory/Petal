package com.glooory.petal.app.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.glooory.petal.R;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import common.PEApplication;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by Glooory on 17/3/16.
 */

public class NetworkUtils {

    private NetworkUtils() {
    }

    public static boolean isConnected() {
        ConnectivityManager manager = (ConnectivityManager) PEApplication.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (manager != null) {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void handleHttpException(Throwable e) {
        if (e instanceof HttpException) {
            SnackbarUtil.showLong(R.string.msg_error_http_exception);
        } else if (e instanceof IOException) {
            SnackbarUtil.showLong(R.string.msg_error_io_exception);
        } else if (e instanceof JsonSyntaxException) {
            SnackbarUtil.showLong(R.string.msg_error_json_syntax_exception);
        } else {
            SnackbarUtil.showLong(R.string.msg_error_common_exception);
        }
    }

}

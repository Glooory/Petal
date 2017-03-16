package com.glooory.petal.app.rx;

import com.glooory.petal.R;
import com.glooory.petal.app.util.NetworkUtils;
import com.glooory.petal.app.util.SnackbarUtil;

import rx.Subscriber;

/**
 * Created by Glooory on 17/3/16.
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {

    @Override
    public void onStart() {
        if (!NetworkUtils.isConnected()) {
            SnackbarUtil.showLong(R.string.msg_network_unavailable);
            onError(null);
        }
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (e == null) {
            return;
        }
        NetworkUtils.handleHttpException(e);
        e.printStackTrace();
    }
}

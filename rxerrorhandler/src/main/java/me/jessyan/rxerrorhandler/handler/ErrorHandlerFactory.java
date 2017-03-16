package me.jessyan.rxerrorhandler.handler;

import me.jessyan.rxerrorhandler.handler.listener.ResponseErroListener;

/**
 * Created by jess on 9/2/16 13:47
 * Contact with jess.yan.effort@gmail.com
 */
public class ErrorHandlerFactory {
    public final String TAG = this.getClass().getSimpleName();
    private ResponseErroListener mResponseErroListener;

    public ErrorHandlerFactory(ResponseErroListener mResponseErroListener) {
        this.mResponseErroListener = mResponseErroListener;
    }

    /**
     *  处理错误
     * @param throwable
     */
    public void handleError(Throwable throwable) {
        mResponseErroListener.handleResponseError((Exception) throwable);
    }
}

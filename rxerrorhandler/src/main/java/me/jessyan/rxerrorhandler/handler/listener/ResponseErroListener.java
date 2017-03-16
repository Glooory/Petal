package me.jessyan.rxerrorhandler.handler.listener;

/**
 * Created by jess on 9/2/16 13:58
 * Contact with jess.yan.effort@gmail.com
 */
public interface ResponseErroListener {

    void handleResponseError(Exception e);

    ResponseErroListener EMPTY = new ResponseErroListener() {
        @Override
        public void handleResponseError(Exception e) {

        }
    };
}

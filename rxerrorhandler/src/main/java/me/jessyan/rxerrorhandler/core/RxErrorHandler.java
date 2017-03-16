package me.jessyan.rxerrorhandler.core;

import me.jessyan.rxerrorhandler.handler.ErrorHandlerFactory;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErroListener;

import static me.jessyan.rxerrorhandler.utils.Preconditions.checkNotNull;

/**
 * Created by jess on 9/2/16 13:27
 * Contact with jess.yan.effort@gmail.com
 */
public class RxErrorHandler {
    public final String TAG = this.getClass().getSimpleName();
    private ErrorHandlerFactory mHandlerFactory;

    private RxErrorHandler(Builder builder) {
        this.mHandlerFactory = builder.errorHandlerFactory;
    }

    public static Builder builder() {
        return new Builder();
    }

    public ErrorHandlerFactory getmHandlerFactory() {
        return mHandlerFactory;
    }

    public static final class Builder {
        private ResponseErroListener responseErroListener;
        private ErrorHandlerFactory errorHandlerFactory;

        private Builder() {
        }

        public Builder responseErroListener(ResponseErroListener responseErroListener) {
            this.responseErroListener = responseErroListener;
            return this;
        }

        public RxErrorHandler build() {
            checkNotNull(responseErroListener,"responseErroListener is required");


            this.errorHandlerFactory = new ErrorHandlerFactory(responseErroListener);

            return new RxErrorHandler(this);
        }
    }


}

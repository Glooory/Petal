package com.glooory.petal.app.rx;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by Glooory on 17/3/15.
 */

public class RxBus {

    private static volatile RxBus sInstance;
    private final Subject<Object, Object> bus;

    public RxBus() {
        this.bus = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus getInstance() {
        if (sInstance == null) {
            synchronized (RxBus.class) {
                if (sInstance == null) {
                    sInstance = new RxBus();
                }
            }
        }
        return sInstance;
    }

    /**
     * 发送一个新事件
     * @param object
     */
    public void post(Object object) {
        bus.onNext(object);
    }

    /**
     * 返回特定类型的被观察者
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }
}

package com.glooory.petal.app.rx;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import rx.Observable;
import rx.Subscriber;

import static com.jakewharton.rxbinding.internal.Preconditions.checkUiThread;

/**
 * Created by Glooory on 2016/9/2.
 */
public class AnimOnSubscribe implements Observable.OnSubscribe<Void> {

    final Animator animator;

    public AnimOnSubscribe(Animator animator) {
        this.animator = animator;
    }

    @Override
    public void call(final Subscriber<? super Void> subscriber) {
        checkUiThread(); //检查是否在 UI 线程调用

        AnimatorListenerAdapter adapter = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                subscriber.onNext(null);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                subscriber.onCompleted();
            }
        };

        animator.addListener(adapter);
        animator.start();
    }
}

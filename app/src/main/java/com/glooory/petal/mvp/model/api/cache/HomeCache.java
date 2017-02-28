package com.glooory.petal.mvp.model.api.cache;

import com.glooory.petal.mvp.model.entity.PinListBean;

import java.util.concurrent.TimeUnit;

import io.rx_cache.DynamicKey;
import io.rx_cache.EvictProvider;
import io.rx_cache.LifeCache;
import io.rx_cache.Reply;
import rx.Observable;

/**
 * Created by Glooory on 17/2/20.
 */

public interface HomeCache {

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<PinListBean>> getLatestAllPins(
            Observable<PinListBean> observablePinList,
            EvictProvider evictProvider);

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<PinListBean>> getLatestAllPinsNext(
            Observable<PinListBean> observablePinList,
            DynamicKey maxPinId,
            EvictProvider evictProvider);

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<PinListBean>> getLatestFollowingPins(
            Observable<PinListBean> observablePinList,
            EvictProvider evictProvider);

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<PinListBean>> getLatestFollowingPinsNext(
            Observable<PinListBean> observablePinList,
            DynamicKey maxPinId,
            EvictProvider evictProvider);

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<PinListBean>> getLatestPopularPins(
            Observable<PinListBean> observablePinList,
            EvictProvider evictProvider);

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<PinListBean>> getLatestPopularPinsNext(
            Observable<PinListBean> observablePinList,
            DynamicKey maxPinId,
            EvictProvider evictProvider);
}

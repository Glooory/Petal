package com.glooory.petal.mvp.model.api.cache;

import com.glooory.petal.mvp.model.entity.ListPinsBean;
import com.glooory.petal.mvp.model.entity.PinsListBean;

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
    Observable<Reply<PinsListBean>> getLatestAllPins(
            Observable<PinsListBean> observablePinsList,
            EvictProvider evictProvider);

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<PinsListBean>> getLatestAllPinsNext(
            Observable<PinsListBean> observablePinsList,
            DynamicKey maxPinId,
            EvictProvider evictProvider);

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<ListPinsBean>> getLatestFollowingPins(
            Observable<ListPinsBean> observableListPins,
            EvictProvider evictProvider);

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<ListPinsBean>> getLatestFollowingPinsNext(
            Observable<ListPinsBean> observableListPins,
            DynamicKey maxPinId,
            EvictProvider evictProvider);

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<ListPinsBean>> getLatestPopularPins(
            Observable<ListPinsBean> observableListPins,
            EvictProvider evictProvider);

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<ListPinsBean>> getLatestPopularPinsNext(
            Observable<ListPinsBean> observableListPins,
            DynamicKey maxPinId,
            EvictProvider evictProvider);
}

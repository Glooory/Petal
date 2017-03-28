package com.glooory.petal.mvp.model;

import com.glooory.petal.app.Constants;
import com.glooory.petal.app.util.SPUtils;
import com.glooory.petal.mvp.model.api.cache.CacheManager;
import com.glooory.petal.mvp.model.api.service.ServiceManager;
import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.model.entity.board.BoardSingleBean;
import com.glooory.petal.mvp.ui.board.BoardContract;
import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import common.BasePetalModel;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 17/3/27.
 */
@ActivityScope
public class BoardModel extends BasePetalModel<ServiceManager, CacheManager>
        implements BoardContract.Model{

    @Inject
    public BoardModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }


    @Override
    public boolean isMine(String userId) {
        if (!isLogin()) {
            return false;
        }
        return userId.equals(String.valueOf(SPUtils.get(Constants.PREF_USER_ID, 0)));
    }

    @Override
    public Observable<BoardBean> getBoard(final String boardId) {
        return mServiceManager.getBoardService()
                .getBoard(boardId)
                .retryWhen(new RetryWithDelay(1, 1))
                .map(new Func1<BoardSingleBean, BoardBean>() {
                    @Override
                    public BoardBean call(BoardSingleBean boardSingleBean) {
                        return boardSingleBean.getBoard();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

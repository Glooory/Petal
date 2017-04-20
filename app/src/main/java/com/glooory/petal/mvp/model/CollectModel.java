package com.glooory.petal.mvp.model;

import com.glooory.petal.app.Constants;
import com.glooory.petal.app.util.SPUtils;
import com.glooory.petal.mvp.model.api.cache.CacheManager;
import com.glooory.petal.mvp.model.api.service.ServiceManager;
import com.glooory.petal.mvp.model.entity.LatestEditBoardsBean;
import com.glooory.petal.mvp.model.entity.collect.CollectResultBean;
import com.glooory.petal.mvp.model.entity.collect.UploadResultBean;
import com.glooory.petal.mvp.ui.collect.CollectContract;
import com.jess.arms.di.scope.ActivityScope;

import java.io.File;

import javax.inject.Inject;

import common.BasePetalModel;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 17/4/3.
 */
@ActivityScope
public class CollectModel extends BasePetalModel<ServiceManager, CacheManager>
        implements CollectContract.Model {

    @Inject
    public CollectModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<LatestEditBoardsBean> getUserLatestBoards() {
        return mServiceManager.getUserService()
                .requestLatestBoardInfo(Constants.HTTP_RECOMMEND_TAGS)
                .retryWhen(new RetryWithDelay(1, 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<UploadResultBean> uploadImage(String imagePath) {
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        return mServiceManager.getPinService()
                .uploadImage(body)
                .retryWhen(new RetryWithDelay(1, 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<CollectResultBean> collect(String boardId, String des, String field) {
        return mServiceManager.getPinService()
                .collect(boardId, des, field, 1, true, 0)
                .retryWhen(new RetryWithDelay(1, 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void saveLatestEditedBoard(String boardName) {
        SPUtils.putByApply(Constants.PREF_LAST_SAVE_BOARD, boardName);
    }
}

package com.glooory.petal.mvp.model.api.service;

import com.jess.arms.http.BaseServiceManager;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Glooory on 17/2/20.
 */
@Singleton
public class ServiceManager implements BaseServiceManager {

    private PinService mPinService;
    private UserService mUserService;
    private BoardService mBoardService;
    private SearchService mSearchService;

    /**
     * 如果需要添加service只需在构造方法中添加对应的service,在提供get方法返回出去,只要在ServiceModule提供了该service
     * Dagger2会自行注入
     *
     * @param
     */
    @Inject
    public ServiceManager(PinService pinService, UserService userService,
            BoardService boardService, SearchService searchService) {
        mPinService = pinService;
        mUserService = userService;
        mBoardService = boardService;
        mSearchService = searchService;
    }

    /**
     * 这里可以释放一些资源(注意这里是单例，即不需要在activity的生命周期调用)
     */
    @Override
    public void onDestory() {

    }

    public PinService getPinService() {
        return mPinService;
    }

    public UserService getUserService() {
        return mUserService;
    }

    public BoardService getBoardService() {
        return mBoardService;
    }

    public SearchService getSearchService() {
        return mSearchService;
    }
}

package com.glooory.petal.mvp.model;

import com.glooory.petal.app.Constants;
import com.glooory.petal.app.util.SPUtils;
import com.glooory.petal.mvp.model.api.cache.CacheManager;
import com.glooory.petal.mvp.model.api.service.ServiceManager;
import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.model.entity.PinListBean;
import com.glooory.petal.mvp.model.entity.PinSingleBean;
import com.glooory.petal.mvp.model.entity.UserBean;
import com.glooory.petal.mvp.model.entity.board.FollowBoardResultBean;
import com.glooory.petal.mvp.model.entity.user.UserBoardListBean;
import com.glooory.petal.mvp.model.entity.user.UserBoardSingleBean;
import com.glooory.petal.mvp.model.entity.user.UserListBean;
import com.glooory.petal.mvp.ui.user.UserContract;
import com.jess.arms.di.scope.FragmentScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import common.BasePetalModel;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 17/3/23.
 */
@FragmentScope
public class UserSectionModel extends BasePetalModel<ServiceManager, CacheManager>
        implements UserContract.SectionModel {

    private int mMaxId;

    @Inject
    public UserSectionModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public boolean isMe(String userId) {
        String userIdTemp = String.valueOf(SPUtils.get(Constants.PREF_USER_ID, 0));
        return userId.equals(userIdTemp);
    }

    @Override
    public Observable<List<BoardBean>> getBoards(String userId) {
        return mServiceManager.getUserService()
                .getBoards(userId, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(1, 1))
                .map(new Func1<UserBoardListBean, List<BoardBean>>() {
                    @Override
                    public List<BoardBean> call(UserBoardListBean userBoardListBean) {
                        if (userBoardListBean.getBoards() != null && userBoardListBean.getBoards().size() > 0) {
                            mMaxId = userBoardListBean.getBoards().
                                    get(userBoardListBean.getBoards().size() - 1).getBoardId();
                        } else {
                            userBoardListBean.setBoards(new ArrayList<BoardBean>(0));
                        }
                        return userBoardListBean.getBoards();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<BoardBean>> getBoardsMore(final String userId) {
        return mServiceManager.getUserService()
                .getBoardsMore(userId, mMaxId, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(1, 1))
                .map(new Func1<UserBoardListBean, List<BoardBean>>() {
                    @Override
                    public List<BoardBean> call(UserBoardListBean userBoardListBean) {
                        if (userBoardListBean.getBoards() != null && userBoardListBean.getBoards().size() > 0) {
                            mMaxId = userBoardListBean.getBoards()
                                    .get(userBoardListBean.getBoards().size() - 1).getBoardId();
                        } else {
                            userBoardListBean.setBoards(new ArrayList<BoardBean>(0));
                        }
                        return userBoardListBean.getBoards();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<FollowBoardResultBean> followBoard(String boardId, boolean isFollowed) {
        String operate = isFollowed ? Constants.HTTP_ARGS_UNFOLLOW : Constants.HTTP_ARGS_FOLLOW;
        return mServiceManager.getBoardService()
                .followBoard(boardId, operate)
                .retryWhen(new RetryWithDelay(1, 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<UserBoardSingleBean> editBoard(String boardId, String boardName,
            String des, String category) {
        return mServiceManager.getBoardService()
                .editBoard(boardId, boardName, des, category)
                .retryWhen(new RetryWithDelay(1, 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<UserBoardSingleBean> createBoard(String boardName,
            String des, String category) {
        return mServiceManager.getBoardService()
                .createBoard(boardName, des, category)
                .retryWhen(new RetryWithDelay(1, 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<UserBoardSingleBean> deleteBoard(String boardId) {
        return mServiceManager.getBoardService()
                .deleteBoard(boardId, Constants.HTTP_ARGS_DELETE)
                .retryWhen(new RetryWithDelay(1, 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<PinBean>> getUserPins(String userId) {
        return mServiceManager.getUserService()
                .getUserPins(userId, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(1, 1))
                .map(new Func1<PinListBean, List<PinBean>>() {
                    @Override
                    public List<PinBean> call(PinListBean pinListBean) {
                        if (pinListBean.getPins() != null && pinListBean.getPins().size() > 0) {
                            mMaxId = pinListBean.getPins().get(pinListBean.getPins().size() - 1).getPinId();
                        } else {
                            pinListBean.setPins(new ArrayList<PinBean>(0));
                        }
                        return pinListBean.getPins();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<PinBean>> getUserPinsMore(String userId) {
        return mServiceManager.getUserService()
                .getUserPinsMore(userId, mMaxId, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(1, 1))
                .map(new Func1<PinListBean, List<PinBean>>() {
                    @Override
                    public List<PinBean> call(PinListBean pinListBean) {
                        if (pinListBean.getPins() != null && pinListBean.getPins().size() > 0) {
                            mMaxId = pinListBean.getPins().get(pinListBean.getPins().size() - 1).getPinId();
                        } else {
                            pinListBean.setPins(new ArrayList<PinBean>(0));
                        }
                        return pinListBean.getPins();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<PinBean> editPin(final String pinId, String boardId, String des) {
        return mServiceManager.getPinService()
                .editPin(pinId, boardId, des)
                .retryWhen(new RetryWithDelay(1, 1))
                .map(new Func1<PinSingleBean, PinBean>() {
                    @Override
                    public PinBean call(PinSingleBean pinSingleBean) {
                        return pinSingleBean.getPin();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Void> deletePin(String pinId) {
        return mServiceManager.getPinService()
                .deletePin(pinId)
                .retryWhen(new RetryWithDelay(1, 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<PinBean>> getUserLikedPins(String userId) {
        return mServiceManager.getUserService()
                .getUserLikedPins(userId, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(1, 1))
                .map(new Func1<PinListBean, List<PinBean>>() {
                    @Override
                    public List<PinBean> call(PinListBean pinListBean) {
                        if (pinListBean.getPins() != null && pinListBean.getPins().size() > 0) {
                            mMaxId = pinListBean.getPins().get(pinListBean.getPins().size() - 1).getSeq();
                        } else {
                            pinListBean.setPins(new ArrayList<PinBean>(0));
                        }
                        return pinListBean.getPins();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<PinBean>> getUserLikedPinsMore(String userId) {
        return mServiceManager.getUserService()
                .getUserLikedPinsMore(userId, mMaxId, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(1, 1))
                .map(new Func1<PinListBean, List<PinBean>>() {
                    @Override
                    public List<PinBean> call(PinListBean pinListBean) {
                        if (pinListBean.getPins() != null && pinListBean.getPins().size() > 0) {
                            mMaxId = pinListBean.getPins().get(pinListBean.getPins().size() - 1).getSeq();
                        } else {
                            pinListBean.setPins(new ArrayList<PinBean>(0));
                        }
                        return pinListBean.getPins();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<UserBean>> getUserFollowing(final String userId) {
        return mServiceManager.getUserService()
                .getUserFollowing(userId, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(1, 1))
                .map(new Func1<UserListBean, List<UserBean>>() {
                    @Override
                    public List<UserBean> call(UserListBean userListBean) {
                        if (userListBean.getUsers() != null && userListBean.getUsers().size() > 0) {
                            mMaxId = userListBean.getUsers().get(userListBean.getUsers().size() - 1).getSeq();
                        } else {
                            userListBean.setUsers(new ArrayList<UserBean>(0));
                        }
                        return userListBean.getUsers();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<UserBean>> getUserFollowingMore(String userId) {
        return mServiceManager.getUserService()
                .getUserFollowingMore(userId, mMaxId, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(1, 1))
                .map(new Func1<UserListBean, List<UserBean>>() {
                    @Override
                    public List<UserBean> call(UserListBean userListBean) {
                        if (userListBean.getUsers() != null && userListBean.getUsers().size() != 0) {
                            mMaxId = userListBean.getUsers().get(userListBean.getUsers().size() - 1).getSeq();
                        } else {
                            userListBean.setUsers(new ArrayList<UserBean>(0));
                        }
                        return userListBean.getUsers();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Void> followUser(String userId, boolean isFollowed) {
        return mServiceManager.getUserService()
                .followUser(userId, isFollowed ? Constants.HTTP_ARGS_UNFOLLOW : Constants.HTTP_ARGS_FOLLOW)
                .retryWhen(new RetryWithDelay(1, 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<UserBean>> getUserFollowers(String userId) {
        return mServiceManager.getUserService()
                .getUserFollowers(userId, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(1, 1))
                .map(new Func1<UserListBean, List<UserBean>>() {
                    @Override
                    public List<UserBean> call(UserListBean userListBean) {
                        if (userListBean.getUsers() != null && userListBean.getUsers().size() != 0) {
                            mMaxId = userListBean.getUsers().get(userListBean.getUsers().size() - 1).getSeq();
                        } else {
                            userListBean.setUsers(new ArrayList<UserBean>(0));
                        }
                        return userListBean.getUsers();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<UserBean>> getUserFollowersMore(String userId) {
        return mServiceManager.getUserService()
                .getUserFollowersMore(userId, mMaxId, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(1, 1))
                .map(new Func1<UserListBean, List<UserBean>>() {
                    @Override
                    public List<UserBean> call(UserListBean userListBean) {
                        if (userListBean.getUsers() != null && userListBean.getUsers().size() != 0) {
                            mMaxId = userListBean.getUsers().get(userListBean.getUsers().size() - 1).getSeq();
                        } else {
                            userListBean.setUsers(new ArrayList<UserBean>(0));
                        }
                        return userListBean.getUsers();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

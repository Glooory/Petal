package com.glooory.petal.mvp.model.entity.user;

/**
 * Created by Glooory on 17/3/27.
 */

public class UserSectionCountBean {

    public static final int BOARD_COUNT = 0;
    public static final int PIN_COUNT = 1;
    public static final int LIKED_COUNT = 2;
    public static final int FOLLOWING_COUNT = 3;
    public static final int FOLLOWER_COUNT = 4;

    private int mCountTypeIndex;
    private boolean increasing;

    public UserSectionCountBean(int countTypeIndex, boolean increasing) {
        mCountTypeIndex = countTypeIndex;
        this.increasing = increasing;
    }

    public int getCountTypeIndex() {
        return mCountTypeIndex;
    }

    public void setCountTypeIndex(int countTypeIndex) {
        mCountTypeIndex = countTypeIndex;
    }

    public boolean isIncreasing() {
        return increasing;
    }

    public void setIncreasing(boolean increasing) {
        this.increasing = increasing;
    }
}

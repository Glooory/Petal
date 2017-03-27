package com.jess.arms.mvp;

/**
 * Created by jess on 16/4/22.
 */
public interface BaseView {

    /**
     * 显示加载
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示信息
     */
    void showMessage(String message);
}

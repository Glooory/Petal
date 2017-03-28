package com.glooory.petal.di.module;

import com.glooory.petal.mvp.model.BoardModel;
import com.glooory.petal.mvp.ui.board.BoardContract;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Glooory on 17/3/28.
 */
@Module
public class BoardModule {

    private BoardContract.View mView;

    public BoardModule(BoardContract.View view) {
        mView = view;
    }

    @ActivityScope
    @Provides
    BoardContract.View provideBoardContractView() {
        return mView;
    }

    @ActivityScope
    @Provides
    BoardContract.Model provideBoardContractModel(BoardModel boardModel) {
        return boardModel;
    }
}

package com.glooory.petal.di.module;

import com.glooory.petal.mvp.model.BoardSectionModel;
import com.glooory.petal.mvp.ui.board.BoardContract;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Glooory on 17/3/28.
 */
@Module
public class BoardSectionModule {

    private BoardContract.SectionView mView;

    public BoardSectionModule(BoardContract.SectionView view) {
        mView = view;
    }

    @FragmentScope
    @Provides
    BoardContract.SectionView provideBoardContractSectionView() {
        return mView;
    }

    @FragmentScope
    @Provides
    BoardContract.SectionModel provideBoardContractSectionModel(BoardSectionModel model) {
        return model;
    }
}

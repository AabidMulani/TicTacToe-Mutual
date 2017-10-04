package com.aabidmulani.tictacmutual.app.modules;

import android.app.Activity;
import android.content.Context;

import com.aabidmulani.tictacmutual.app.presenter.SplashScreenPresenter;
import com.aabidmulani.tictacmutual.app.qualifiers.TicTacPerActivity;
import com.aabidmulani.tictacmutual.app.views.SplashScreenView;

import dagger.Module;
import dagger.Provides;

@Module
public class SplashScreenActivityModule {

    private Activity mActivity;
    private SplashScreenPresenter splashScreenPresenter;

    public SplashScreenActivityModule(Activity mActivity, SplashScreenView splashScreenView) {
        this.mActivity = mActivity;
        this.splashScreenPresenter = new SplashScreenPresenter(mActivity, splashScreenView);
    }


    @Provides
    @TicTacPerActivity
    Context provideContext() {
        return mActivity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    SplashScreenPresenter provideSplashScreenPresenter() {
        return splashScreenPresenter;
    }

}

package com.aabidmulani.tictacmutual.app.modules;

import android.app.Activity;
import android.content.Context;

import com.aabidmulani.tictacmutual.app.qualifiers.TicTacPerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity mActivity) {
        this.mActivity = mActivity;
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

}

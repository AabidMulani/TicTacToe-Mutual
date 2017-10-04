package com.aabidmulani.tictacmutual.app.modules;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.aabidmulani.tictacmutual.app.qualifiers.TicTacAppContext;
import com.aabidmulani.tictacmutual.utils.AppConstants;

import dagger.Module;
import dagger.Provides;

@Module
public class TicTacApplicationModule {

    private final Application mApplication;

    public TicTacApplicationModule(Application app) {
        mApplication = app;
    }

    @Provides
    @TicTacAppContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }


    @Provides
    SharedPreferences provideSharedPrefs() {
        return mApplication.getSharedPreferences(AppConstants.APP_PREF, Context.MODE_PRIVATE);
    }

}

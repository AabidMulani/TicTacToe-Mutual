package com.aabidmulani.tictacmutual.app.components;

import android.app.Application;
import android.content.Context;

import com.aabidmulani.tictacmutual.BaseApplication;
import com.aabidmulani.tictacmutual.app.modules.TicTacApplicationModule;
import com.aabidmulani.tictacmutual.app.qualifiers.TicTacAppContext;
import com.aabidmulani.tictacmutual.sharedpreferences.TicTacAppSharedPreference;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = TicTacApplicationModule.class)
public interface TicTacApplicationComponent {

    void inject(BaseApplication demoApplication);

    @TicTacAppContext
    Context getContext();

    Application getApplication();

    TicTacAppSharedPreference getPreferenceHelper();


}

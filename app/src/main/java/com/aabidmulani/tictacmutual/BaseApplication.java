package com.aabidmulani.tictacmutual;

import android.app.Application;

import com.aabidmulani.tictacmutual.app.components.DaggerTicTacApplicationComponent;
import com.aabidmulani.tictacmutual.app.components.TicTacApplicationComponent;
import com.aabidmulani.tictacmutual.app.modules.TicTacApplicationModule;

import timber.log.Timber;


public class BaseApplication extends Application {

    protected TicTacApplicationComponent applicationComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        Timber.i("-- init --");
        applicationComponent = DaggerTicTacApplicationComponent
                .builder()
                .ticTacApplicationModule(new TicTacApplicationModule(this))
                .build();

        applicationComponent.inject(this);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Timber.i("-- onTerminate --");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Timber.i("-- onLowMemory --");
    }

    public TicTacApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}

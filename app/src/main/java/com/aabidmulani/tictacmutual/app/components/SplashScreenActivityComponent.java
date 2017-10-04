package com.aabidmulani.tictacmutual.app.components;


import com.aabidmulani.tictacmutual.app.activities.SplashScreenActivity;
import com.aabidmulani.tictacmutual.app.modules.SplashScreenActivityModule;
import com.aabidmulani.tictacmutual.app.qualifiers.TicTacPerActivity;

import dagger.Component;

@TicTacPerActivity
@Component(dependencies = TicTacApplicationComponent.class, modules = SplashScreenActivityModule.class)
public interface SplashScreenActivityComponent {

    void inject(SplashScreenActivity splashScreenActivity);

}

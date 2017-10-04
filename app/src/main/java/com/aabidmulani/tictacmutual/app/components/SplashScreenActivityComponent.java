package com.aabidmulani.tictacmutual.app.components;


import com.aabidmulani.tictacmutual.app.modules.ActivityModule;
import com.aabidmulani.tictacmutual.app.qualifiers.TicTacPerActivity;

import dagger.Component;

@TicTacPerActivity
@Component(dependencies = TicTacApplicationComponent.class, modules = ActivityModule.class)
public interface SplashScreenActivityComponent {



}

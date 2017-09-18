package com.aabidmulani.tictacmutual.app.views;


import java.util.HashMap;

public interface NewGameView {

    void showWaitingForOtherPlayer();

    void showPlayNowMsg();

    void updateTheBoard(HashMap<String, String> stringHashMap);

    void showGameDraw();

    void showGameWon();

    void showGameLost();

    void showErrorMsg(int resId);

    void onEndGame();
}

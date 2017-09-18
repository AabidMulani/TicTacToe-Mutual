package com.aabidmulani.tictacmutual.app.activities;


import android.app.Activity;
import android.content.Context;

import com.aabidmulani.tictacmutual.app.presenter.NewGamePresenter;
import com.aabidmulani.tictacmutual.app.services.NewGameService;
import com.aabidmulani.tictacmutual.app.views.NewGameView;
import com.aabidmulani.tictacmutual.utils.AppConstants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NewGameActivityTest {


    @Mock
    NewGameView newGameView;
    @Mock
    NewGameService newGameService;
    @Mock
    Context context;
    @Mock
    Activity activity;

    private NewGamePresenter newGamePresenter;


    @Before
    public void setUp() throws Exception {
        newGamePresenter = new NewGamePresenter(context, newGameService, newGameView, "abc", AppConstants.COIN_TYPE_CIRCLE, true);
    }


    @Test
    public void testWinLossCaseFirstRow() {
        HashMap<String, String> localMap = new HashMap<>();
        localMap.put("00", AppConstants.COIN_TYPE_CIRCLE);
        localMap.put("01", AppConstants.COIN_TYPE_CIRCLE);
        localMap.put("02", AppConstants.COIN_TYPE_CIRCLE);

        newGamePresenter.checkWinLossCases(localMap);

        verify(newGameView, atLeastOnce()).showGameWon();


        localMap = new HashMap<>();
        localMap.put("00", AppConstants.COIN_TYPE_CROSS);
        localMap.put("01", AppConstants.COIN_TYPE_CROSS);
        localMap.put("02", AppConstants.COIN_TYPE_CROSS);

        newGamePresenter.checkWinLossCases(localMap);

        verify(newGameView, atLeastOnce()).showGameLost();

    }


    @Test
    public void testWinLossCaseThirdRow() {
        HashMap<String, String> localMap = new HashMap<>();
        localMap.put("20", AppConstants.COIN_TYPE_CIRCLE);
        localMap.put("21", AppConstants.COIN_TYPE_CIRCLE);
        localMap.put("22", AppConstants.COIN_TYPE_CIRCLE);

        newGamePresenter.checkWinLossCases(localMap);

        verify(newGameView, atLeastOnce()).showGameWon();


        localMap = new HashMap<>();
        localMap.put("20", AppConstants.COIN_TYPE_CROSS);
        localMap.put("21", AppConstants.COIN_TYPE_CROSS);
        localMap.put("22", AppConstants.COIN_TYPE_CROSS);

        newGamePresenter.checkWinLossCases(localMap);

        verify(newGameView, atLeastOnce()).showGameLost();

    }


    @Test
    public void testWinLossCaseSecondRow() {
        HashMap<String, String> localMap = new HashMap<>();
        localMap.put("10", AppConstants.COIN_TYPE_CIRCLE);
        localMap.put("11", AppConstants.COIN_TYPE_CIRCLE);
        localMap.put("12", AppConstants.COIN_TYPE_CIRCLE);

        newGamePresenter.checkWinLossCases(localMap);

        verify(newGameView, atLeastOnce()).showGameWon();


        localMap = new HashMap<>();
        localMap.put("10", AppConstants.COIN_TYPE_CROSS);
        localMap.put("11", AppConstants.COIN_TYPE_CROSS);
        localMap.put("12", AppConstants.COIN_TYPE_CROSS);

        newGamePresenter.checkWinLossCases(localMap);

        verify(newGameView, atLeastOnce()).showGameLost();

    }


    @Test
    public void testWinLossCaseFirstColoumn() {
        HashMap<String, String> localMap = new HashMap<>();
        localMap.put("00", AppConstants.COIN_TYPE_CIRCLE);
        localMap.put("10", AppConstants.COIN_TYPE_CIRCLE);
        localMap.put("20", AppConstants.COIN_TYPE_CIRCLE);

        newGamePresenter.checkWinLossCases(localMap);

        verify(newGameView, atLeastOnce()).showGameWon();


        localMap = new HashMap<>();
        localMap.put("00", AppConstants.COIN_TYPE_CROSS);
        localMap.put("10", AppConstants.COIN_TYPE_CROSS);
        localMap.put("20", AppConstants.COIN_TYPE_CROSS);

        newGamePresenter.checkWinLossCases(localMap);

        verify(newGameView, atLeastOnce()).showGameLost();

    }


    @Test
    public void testWinLossCaseSecondColoumn() {
        HashMap<String, String> localMap = new HashMap<>();
        localMap.put("01", AppConstants.COIN_TYPE_CIRCLE);
        localMap.put("11", AppConstants.COIN_TYPE_CIRCLE);
        localMap.put("21", AppConstants.COIN_TYPE_CIRCLE);

        newGamePresenter.checkWinLossCases(localMap);

        verify(newGameView, atLeastOnce()).showGameWon();


        localMap = new HashMap<>();
        localMap.put("01", AppConstants.COIN_TYPE_CROSS);
        localMap.put("11", AppConstants.COIN_TYPE_CROSS);
        localMap.put("21", AppConstants.COIN_TYPE_CROSS);

        newGamePresenter.checkWinLossCases(localMap);

        verify(newGameView, atLeastOnce()).showGameLost();

    }


    @Test
    public void testWinLossCaseThirdColoumn() {
        HashMap<String, String> localMap = new HashMap<>();
        localMap.put("02", AppConstants.COIN_TYPE_CIRCLE);
        localMap.put("12", AppConstants.COIN_TYPE_CIRCLE);
        localMap.put("22", AppConstants.COIN_TYPE_CIRCLE);

        newGamePresenter.checkWinLossCases(localMap);

        verify(newGameView, atLeastOnce()).showGameWon();


        localMap = new HashMap<>();
        localMap.put("02", AppConstants.COIN_TYPE_CROSS);
        localMap.put("12", AppConstants.COIN_TYPE_CROSS);
        localMap.put("22", AppConstants.COIN_TYPE_CROSS);

        newGamePresenter.checkWinLossCases(localMap);

        verify(newGameView, atLeastOnce()).showGameLost();

    }


    @Test
    public void testWinLossCaseDiagonals() {
        HashMap<String, String> localMap = new HashMap<>();
        localMap.put("00", AppConstants.COIN_TYPE_CIRCLE);
        localMap.put("11", AppConstants.COIN_TYPE_CIRCLE);
        localMap.put("22", AppConstants.COIN_TYPE_CIRCLE);

        newGamePresenter.checkWinLossCases(localMap);

        verify(newGameView, atLeastOnce()).showGameWon();


        localMap = new HashMap<>();
        localMap.put("00", AppConstants.COIN_TYPE_CROSS);
        localMap.put("11", AppConstants.COIN_TYPE_CROSS);
        localMap.put("22", AppConstants.COIN_TYPE_CROSS);

        newGamePresenter.checkWinLossCases(localMap);

        verify(newGameView, atLeastOnce()).showGameLost();

    }


    @Test
    public void testWinLossCaseDiagonalsOpposit() {
        HashMap<String, String> localMap = new HashMap<>();
        localMap.put("20", AppConstants.COIN_TYPE_CIRCLE);
        localMap.put("11", AppConstants.COIN_TYPE_CIRCLE);
        localMap.put("02", AppConstants.COIN_TYPE_CIRCLE);

        newGamePresenter.checkWinLossCases(localMap);

        verify(newGameView, atLeastOnce()).showGameWon();

        localMap = new HashMap<>();
        localMap.put("20", AppConstants.COIN_TYPE_CROSS);
        localMap.put("11", AppConstants.COIN_TYPE_CROSS);
        localMap.put("02", AppConstants.COIN_TYPE_CROSS);

        newGamePresenter.checkWinLossCases(localMap);

        verify(newGameView, atLeastOnce()).showGameLost();

    }

    @Test
    public void testSwitchPlayerCursor() {
        HashMap<String, String> localMap = new HashMap<>();
        localMap.put("20", AppConstants.COIN_TYPE_CIRCLE);
        localMap.put("11", AppConstants.COIN_TYPE_CROSS);
        localMap.put("22", AppConstants.COIN_TYPE_CIRCLE);
        newGamePresenter.checkWinLossCases(localMap);
        verify(newGameView, atLeastOnce()).showPlayNowMsg();

    }


}
package com.aabidmulani.tictacmutual.app.views;

import com.aabidmulani.tictacmutual.firebaseModels.CurrentGameDetailsModel;
import com.aabidmulani.tictacmutual.firebaseModels.WaitingGameDetailsModel;

import java.util.List;

public interface DashboardFragmentView {

    void setUpUserDashboardValues(long total, long winCount, long looseCount);

    void showNewGameButton();

    void showWaitingGameLoader();

    void showNewGameLoader();

    void showError(int resId);

    void populateWaitingGames(List<WaitingGameDetailsModel> detailsModelList);

    void startNewGameWithMatchId(CurrentGameDetailsModel newGameDetailsModel, String newMatchNodeId, String coinType, boolean isUserOne);
}

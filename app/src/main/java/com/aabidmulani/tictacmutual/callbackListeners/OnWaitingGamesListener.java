package com.aabidmulani.tictacmutual.callbackListeners;

import com.aabidmulani.tictacmutual.firebaseModels.WaitingGameDetailsModel;

import java.util.List;

public interface OnWaitingGamesListener {

    void onWaitingGameListSuccess(List<WaitingGameDetailsModel> waitingGameDetailsModels);

    void onWaitingGameListError(int resId);

}

package com.aabidmulani.tictacmutual.callbackListeners;

import com.aabidmulani.tictacmutual.firebaseModels.WaitingGameDetailsModel;

public interface OnWaitingGameClickedListener {

    void onGameClicked(WaitingGameDetailsModel currentObject);

}

package com.aabidmulani.tictacmutual.callbackListeners;

import com.aabidmulani.tictacmutual.firebaseModels.CurrentGameDetailsModel;

public interface OnCurrentGameDataListener {

    void onGameDataSuccess(CurrentGameDetailsModel currentGameDetailsModel);

    void onGameDataError(int resId);

}

package com.aabidmulani.tictacmutual.callbackListeners;

import com.aabidmulani.tictacmutual.firebaseModels.UserDetailsModel;

public interface OnUserDetailsListener {

    void onUserDetailsSuccess(UserDetailsModel userDetailsModel);

    void onUserDetailsError(int resId);

}

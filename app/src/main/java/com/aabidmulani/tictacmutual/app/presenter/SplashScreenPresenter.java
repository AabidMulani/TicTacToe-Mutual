package com.aabidmulani.tictacmutual.app.presenter;

import android.content.Context;
import android.os.Handler;

import com.aabidmulani.tictacmutual.R;
import com.aabidmulani.tictacmutual.app.views.SplashScreenView;
import com.aabidmulani.tictacmutual.sharedpreferences.AppSharedPreference;

public class SplashScreenPresenter {

    private Context context;
    private SplashScreenView splashScreenView;
    private AppSharedPreference sharedPreference;

    public SplashScreenPresenter(Context context, SplashScreenView splashScreenView) {
        this.context = context;
        this.splashScreenView = splashScreenView;
        sharedPreference = new AppSharedPreference(context);
        processNextStep();
    }

    private void processNextStep() {

        if (sharedPreference.getUserEmail() == null) {
            splashScreenView.showEmailTextView();
        } else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    splashScreenView.goToMainScreen();
                }
            }, 2000);

        }

    }


    public void onEmailSubmitClicked() {
        String email = splashScreenView.getEmail();

        if (email == null || email.isEmpty()) {
            splashScreenView.showErrorMsg(R.string.enter_email);
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            splashScreenView.showErrorMsg(R.string.enter_valid_email);
        } else {
            sharedPreference.setUserEmail(email);
            splashScreenView.goToMainScreen();

        }
    }


}

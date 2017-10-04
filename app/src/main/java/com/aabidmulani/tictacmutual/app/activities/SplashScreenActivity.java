package com.aabidmulani.tictacmutual.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.aabidmulani.tictacmutual.BaseActivity;
import com.aabidmulani.tictacmutual.BaseApplication;
import com.aabidmulani.tictacmutual.R;
import com.aabidmulani.tictacmutual.app.components.DaggerSplashScreenActivityComponent;
import com.aabidmulani.tictacmutual.app.components.SplashScreenActivityComponent;
import com.aabidmulani.tictacmutual.app.modules.SplashScreenActivityModule;
import com.aabidmulani.tictacmutual.app.presenter.SplashScreenPresenter;
import com.aabidmulani.tictacmutual.app.views.SplashScreenView;
import com.aabidmulani.tictacmutual.utils.Utils;
import com.aabidmulani.tictacmutual.widgets.RobotoButton;
import com.aabidmulani.tictacmutual.widgets.RobotoEditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SplashScreenActivity extends BaseActivity implements SplashScreenView {

    @BindView(R.id.emailEditText)
    RobotoEditText emailEditText;
    @BindView(R.id.proceedButton)
    RobotoButton proceedButton;

    private SplashScreenActivityComponent activityComponent;

    @Inject
    SplashScreenPresenter splashScreenPresenter;

    @Inject
    Context context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        initDagger();
    }

    private void initDagger() {
        if (activityComponent == null) {
            activityComponent = DaggerSplashScreenActivityComponent
                    .builder()
                    .splashScreenActivityModule(new SplashScreenActivityModule(this, this))
                    .ticTacApplicationComponent(((BaseApplication) getApplication()).getApplicationComponent())
                    .build();
        }
        activityComponent.inject(this);
    }

    @OnClick(R.id.proceedButton)
    public void onClick() {
        splashScreenPresenter.onEmailSubmitClicked();
    }

    @Override
    public String getEmail() {
        return emailEditText.getText().toString();
    }

    @Override
    public void showErrorMsg(int resId) {
        Utils.showShortToast(activity, getString(resId));
    }

    @Override
    public void goToMainScreen() {
        startActivity(new Intent(activity, DashboardActivity.class));
        finish();
    }

    @Override
    public void showEmailTextView() {
        emailEditText.setVisibility(View.VISIBLE);
        proceedButton.setVisibility(View.VISIBLE);
    }
}

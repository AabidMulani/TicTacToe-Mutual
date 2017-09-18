package com.aabidmulani.tictacmutual.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.aabidmulani.tictacmutual.BaseActivity;
import com.aabidmulani.tictacmutual.R;
import com.aabidmulani.tictacmutual.app.presenter.NewGamePresenter;
import com.aabidmulani.tictacmutual.app.services.NewGameService;
import com.aabidmulani.tictacmutual.app.views.NewGameView;
import com.aabidmulani.tictacmutual.utils.Utils;
import com.aabidmulani.tictacmutual.widgets.RobotoTextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class NewGameActivity extends BaseActivity implements NewGameView {

    String currentMatchNodeId;
    String coinTypeValue;
    boolean isUserOne;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.boxA)
    RobotoTextView boxA;
    @BindView(R.id.boxB)
    RobotoTextView boxB;
    @BindView(R.id.boxC)
    RobotoTextView boxC;
    @BindView(R.id.boxD)
    RobotoTextView boxD;
    @BindView(R.id.boxE)
    RobotoTextView boxE;
    @BindView(R.id.boxF)
    RobotoTextView boxF;
    @BindView(R.id.boxG)
    RobotoTextView boxG;
    @BindView(R.id.boxH)
    RobotoTextView boxH;
    @BindView(R.id.boxI)
    RobotoTextView boxI;
    @BindView(R.id.displayMsg)
    RobotoTextView displayMsg;
    @BindView(R.id.displayStatusMsg)
    RobotoTextView displayStatusMsg;
    @BindView(R.id.gameStatusLayout)
    LinearLayout gameStatusLayout;
    private int moveCount;

    private NewGamePresenter newGamePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        ButterKnife.bind(this);
        currentMatchNodeId = getIntent().getStringExtra("matchId");
        coinTypeValue = getIntent().getStringExtra("coinType");
        isUserOne = getIntent().getBooleanExtra("userOne", false);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Game time");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Timber.e("currentMatchNodeId: " + currentMatchNodeId);

        newGamePresenter = new NewGamePresenter(activity, new NewGameService(), this, currentMatchNodeId, coinTypeValue, isUserOne);

    }

    @Override
    protected void onResume() {
        super.onResume();
        newGamePresenter.onAddListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        newGamePresenter.onRemoveAllListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick({R.id.boxA, R.id.boxB, R.id.boxC, R.id.boxD, R.id.boxE, R.id.boxF, R.id.boxG, R.id.boxH, R.id.boxI})
    public void onClick(View view) {
        newGamePresenter.onBoxClicked(view.getId());
    }


    @OnClick(R.id.gameOverButton)
    public void onGameOverButtonClick() {
        newGamePresenter.onExitClicked();
    }


    @Override
    public void showWaitingForOtherPlayer() {
        displayMsg.setText("Waiting for other players move..");
    }

    @Override
    public void showPlayNowMsg() {
        displayMsg.setText("Your turn");
    }

    @Override
    public void updateTheBoard(HashMap<String, String> stringHashMap) {
        moveCount = 0;
        setThisBoxValue(stringHashMap.get("00"), boxA);
        setThisBoxValue(stringHashMap.get("01"), boxB);
        setThisBoxValue(stringHashMap.get("02"), boxC);
        setThisBoxValue(stringHashMap.get("10"), boxD);
        setThisBoxValue(stringHashMap.get("11"), boxE);
        setThisBoxValue(stringHashMap.get("12"), boxF);
        setThisBoxValue(stringHashMap.get("20"), boxG);
        setThisBoxValue(stringHashMap.get("21"), boxH);
        setThisBoxValue(stringHashMap.get("22"), boxI);
        newGamePresenter.updateWinLossCase(moveCount);
    }

    private void setThisBoxValue(String value, RobotoTextView box) {
        Timber.e("setThisBoxValue:" + value);
        if (value == null) {
            box.setText("");
        } else {
            box.setText(value);
            moveCount++;
        }
    }

    @Override
    public void showGameDraw() {
        gameStatusLayout.setVisibility(View.VISIBLE);
        displayStatusMsg.setText(getString(R.string.draw));
    }

    @Override
    public void showGameWon() {
        gameStatusLayout.setVisibility(View.VISIBLE);
        displayStatusMsg.setText(getString(R.string.won));
    }

    @Override
    public void showGameLost() {
        gameStatusLayout.setVisibility(View.VISIBLE);
        displayStatusMsg.setText(getString(R.string.lost));
    }

    @Override
    public void showErrorMsg(int resId) {
        Utils.showShortToast(activity, getString(resId));
    }

    @Override
    public void onEndGame() {
        Intent intent = new Intent(activity, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

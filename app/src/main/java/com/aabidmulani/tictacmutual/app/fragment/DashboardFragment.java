package com.aabidmulani.tictacmutual.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.aabidmulani.tictacmutual.BaseFragment;
import com.aabidmulani.tictacmutual.R;
import com.aabidmulani.tictacmutual.app.activities.NewGameActivity;
import com.aabidmulani.tictacmutual.app.adapters.WaitingGameListingAdapter;
import com.aabidmulani.tictacmutual.app.presenter.DashboardFragmentPresenter;
import com.aabidmulani.tictacmutual.app.services.DashboardFragmentService;
import com.aabidmulani.tictacmutual.app.views.DashboardFragmentView;
import com.aabidmulani.tictacmutual.callbackListeners.OnWaitingGameClickedListener;
import com.aabidmulani.tictacmutual.firebaseModels.CurrentGameDetailsModel;
import com.aabidmulani.tictacmutual.firebaseModels.WaitingGameDetailsModel;
import com.aabidmulani.tictacmutual.utils.Utils;
import com.aabidmulani.tictacmutual.widgets.RobotoButton;
import com.aabidmulani.tictacmutual.widgets.RobotoTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class DashboardFragment extends BaseFragment implements DashboardFragmentView, OnWaitingGameClickedListener {

    @BindView(R.id.totalMatchCount)
    RobotoTextView totalMatchCount;
    @BindView(R.id.wonMatchCount)
    RobotoTextView wonMatchCount;
    @BindView(R.id.lostMatchCount)
    RobotoTextView lostMatchCount;
    @BindView(R.id.newGameButton)
    RobotoButton newGameButton;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.cancelButton)
    RobotoButton cancelButton;

    private DashboardFragmentPresenter dashboardFragmentPresenter;
    private WaitingGameListingAdapter waitingGameListingAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        dashboardFragmentPresenter = new DashboardFragmentPresenter(activity, new DashboardFragmentService(), this);

        return view;
    }

    @Override
    public void onResume() {
        Timber.i("-- onResume --");
        super.onResume();
        dashboardFragmentPresenter.addListeners();
    }

    @Override
    public void onStop() {
        Timber.i("-- onStop --");
        super.onStop();
        dashboardFragmentPresenter.removeAllCallBacks();
    }


    @Override
    public boolean isBackKeyConsumed() {
        return false;
    }

    @Override
    public String getFragmentTitle() {
        return "TicTacToe-Mutual";
    }

    @OnClick(R.id.newGameButton)
    public void onNewGameButtonClick() {
        Timber.i("-- onNewGameButtonClick --");
        dashboardFragmentPresenter.startNewGame();
    }

    @OnClick(R.id.cancelButton)
    public void onCancelButtonClick() {
        Timber.i("-- onCancelButtonClick --");
        dashboardFragmentPresenter.onNewGameCancelClicked();
    }

    @Override
    public void setUpUserDashboardValues(long total, long winCount, long looseCount) {
        Timber.i("-- setUpUserDashboardValues --");
        totalMatchCount.setText(String.valueOf(total));
        wonMatchCount.setText(String.valueOf(winCount));
        lostMatchCount.setText(String.valueOf(looseCount));
    }

    @Override
    public void showNewGameButton() {
        Timber.i("-- showNewGameButton --");
        newGameButton.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        cancelButton.setVisibility(View.GONE);
    }

    @Override
    public void showWaitingGameLoader() {
        Timber.i("-- showWaitingGameLoader --");
        newGameButton.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.GONE);
    }

    @Override
    public void showNewGameLoader() {
        Timber.i("-- showNewGameLoader --");
        newGameButton.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(int resId) {
        Timber.i("-- showError --");
        Utils.showShortToast(activity, getString(resId));
    }

    @Override
    public void populateWaitingGames(List<WaitingGameDetailsModel> detailsModelList) {
        Timber.i("-- populateWaitingGames --");
        Timber.i("-- size: =" + detailsModelList.size());
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        cancelButton.setVisibility(View.GONE);
        if (waitingGameListingAdapter == null) {
            waitingGameListingAdapter = new WaitingGameListingAdapter(activity, detailsModelList, this);
            recyclerView.setAdapter(waitingGameListingAdapter);
        } else {
            waitingGameListingAdapter.setWaitinGameDetailsModelsList(detailsModelList);
            waitingGameListingAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void startNewGameWithMatchId(CurrentGameDetailsModel newGameDetailsModel, String newMatchNodeId, String coinType, boolean isUserOne) {
        Timber.i("-- startNewGameWithMatchId --");
        Timber.i("-- matchId::" + newMatchNodeId);
        Utils.showShortToast(activity, "Start game");
        Intent intent = new Intent(activity, NewGameActivity.class);
        intent.putExtra("matchId", newMatchNodeId);
        intent.putExtra("userOne", isUserOne);
        intent.putExtra("coinType", coinType);
        getActivity().startActivity(intent);
    }

    @Override
    public void onGameClicked(WaitingGameDetailsModel currentObject) {
        Timber.i("-- onGameClicked --");
        dashboardFragmentPresenter.onJoinGameClicked(currentObject);
    }
}

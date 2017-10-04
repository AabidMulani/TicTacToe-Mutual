package com.aabidmulani.tictacmutual.app.presenter;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.aabidmulani.tictacmutual.app.services.DashboardFragmentService;
import com.aabidmulani.tictacmutual.app.views.DashboardFragmentView;
import com.aabidmulani.tictacmutual.callbackListeners.OnUserDetailsListener;
import com.aabidmulani.tictacmutual.callbackListeners.OnWaitingGamesListener;
import com.aabidmulani.tictacmutual.firebaseModels.CurrentGameDetailsModel;
import com.aabidmulani.tictacmutual.firebaseModels.UserDetailsModel;
import com.aabidmulani.tictacmutual.firebaseModels.WaitingGameDetailsModel;
import com.aabidmulani.tictacmutual.sharedpreferences.AppSharedPreference;
import com.aabidmulani.tictacmutual.utils.AppConstants;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class DashboardFragmentPresenter {

    private Context context;
    private DashboardFragmentService dashboardFragmentService;
    private DashboardFragmentView dashboardFragmentView;
    private String currentNewGameRequestId = null;
    List<WaitingGameDetailsModel> waitingGameDetailsModelsList;

    private ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Timber.e("Calling from: onChildAdded()");

            if (dataSnapshot != null) {
                WaitingGameDetailsModel waitingGameDetailsModel = dataSnapshot.getValue(WaitingGameDetailsModel.class);
                if (waitingGameDetailsModel != null) {
                    if (waitingGameDetailsModel.getNodeKeyName() != null && !waitingGameDetailsModel.getNodeKeyName().equals(currentNewGameRequestId)) {
                        if (waitingGameDetailsModelsList == null) {
                            waitingGameDetailsModelsList = new ArrayList<>();
                        }
                        boolean found = false;
                        for (WaitingGameDetailsModel model : waitingGameDetailsModelsList) {
                            if (model.getNewMatchNodeId() != null && model.getNewMatchNodeId().equals(waitingGameDetailsModel.getNodeKeyName())) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            waitingGameDetailsModelsList.add(waitingGameDetailsModel);
                            Timber.i("-- calling populate from onChildAdded --");
                            dashboardFragmentView.populateWaitingGames(waitingGameDetailsModelsList);
                        }
                    }
                }
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Timber.e("Calling from: onChildChanged()");
            String currentUserEmail = new AppSharedPreference(context).getUserEmail();
            if (dataSnapshot != null) {
                WaitingGameDetailsModel waitingGameDetailsModel = dataSnapshot.getValue(WaitingGameDetailsModel.class);
                if (waitingGameDetailsModel != null && waitingGameDetailsModel.getUserOne() != null && waitingGameDetailsModel.getUserTwo() != null) {
                    if (waitingGameDetailsModel.getUserOne().equals(currentUserEmail) || waitingGameDetailsModel.getUserTwo().equals(currentUserEmail)) {
                        if (waitingGameDetailsModel.getNewMatchNodeId() != null && !waitingGameDetailsModel.getNewMatchNodeId().isEmpty()) {
                            String coinType = waitingGameDetailsModel.getUserTwoCoinType();
                            boolean isUserOne = false;
                            if (waitingGameDetailsModel.getUserOne().equals(currentUserEmail)) {
                                dashboardFragmentService.removeNewGame(waitingGameDetailsModel.getNodeKeyName());
                                coinType = waitingGameDetailsModel.getUserOneCoinType();
                                isUserOne = true;
                            }
                            CurrentGameDetailsModel newGameDetailsModel = new CurrentGameDetailsModel(waitingGameDetailsModel.getUserOne(), waitingGameDetailsModel.getUserOneCoinType(), waitingGameDetailsModel.getUserTwo(), waitingGameDetailsModel.getUserTwoCoinType(), waitingGameDetailsModel.getCurrentState());
                            dashboardFragmentView.showNewGameButton();
                            dashboardFragmentView.startNewGameWithMatchId(newGameDetailsModel, waitingGameDetailsModel.getNewMatchNodeId(), coinType, isUserOne);
                            currentNewGameRequestId = null;
                        }
                    }
                }
            }

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Timber.e("Calling from: onChildRemoved()");
            try {
                if (dataSnapshot != null) {
                    WaitingGameDetailsModel waitingGameDetailsModel = dataSnapshot.getValue(WaitingGameDetailsModel.class);
                    if (waitingGameDetailsModel != null) {
                        if (!waitingGameDetailsModel.getNodeKeyName().equals(currentNewGameRequestId)) {
                            int position = 0;
                            boolean found = false;
                            for (WaitingGameDetailsModel model : waitingGameDetailsModelsList) {
                                if (model.getNewMatchNodeId().equals(waitingGameDetailsModel.getNodeKeyName())) {
                                    found = true;
                                    break;
                                }
                                position++;
                            }
                            if (found) {
                                waitingGameDetailsModelsList.remove(position);
                                Timber.i("-- calling populate from onChildRemoved --");
                                dashboardFragmentView.populateWaitingGames(waitingGameDetailsModelsList);
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                Timber.e(Log.getStackTraceString(ex));
            }
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            Timber.e("Calling from: onChildMoved()");
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Timber.e("Calling from: onCancelled()");
        }
    };

    public DashboardFragmentPresenter(Context context, DashboardFragmentService dashboardFragmentService, DashboardFragmentView dashboardFragmentView) {
        this.context = context;
        this.dashboardFragmentService = dashboardFragmentService;
        this.dashboardFragmentView = dashboardFragmentView;
        makeUserDetailsRequest();
        makeWaitingGameRequest();
    }

    private void makeWaitingGameRequest() {
        Timber.e("-- makeWaitingGameRequest --");
        dashboardFragmentView.showWaitingGameLoader();

        dashboardFragmentService.getCurrentWaitingGames(context, new OnWaitingGamesListener() {
            @Override
            public void onWaitingGameListSuccess(List<WaitingGameDetailsModel> waitingGameDetailsModels) {
                waitingGameDetailsModelsList = waitingGameDetailsModels;
                Timber.i("-- calling populate from makeWaitingGameRequest --");
                dashboardFragmentView.populateWaitingGames(waitingGameDetailsModels);
            }

            @Override
            public void onWaitingGameListError(int resId) {
                dashboardFragmentView.showError(resId);
                dashboardFragmentView.showNewGameButton();
            }
        });
    }

    private void makeUserDetailsRequest() {
        Timber.e("-- makeUserDetailsRequest --");

        dashboardFragmentService.getUserDetails(context, new OnUserDetailsListener() {
            @Override
            public void onUserDetailsSuccess(UserDetailsModel userDetailsModel) {
                if (userDetailsModel == null) {
                    dashboardFragmentView.setUpUserDashboardValues(0, 0, 0);
                } else {
                    dashboardFragmentView.setUpUserDashboardValues(userDetailsModel.getTotalGames(), userDetailsModel.getWinCount(), userDetailsModel.getLossCount());
                }
            }

            @Override
            public void onUserDetailsError(int resId) {
                dashboardFragmentView.setUpUserDashboardValues(0, 0, 0);
                dashboardFragmentView.showError(resId);
            }
        });
    }

    public void addListeners() {
        Timber.e("-- addListeners --");
        dashboardFragmentService.startListeningToWaitingTable(childEventListener);
    }

    public void removeAllCallBacks() {
        Timber.e("-- removeAllCallBacks --");
        dashboardFragmentService.removeWaitingTableListener();
        if (currentNewGameRequestId != null) {
            dashboardFragmentService.removeNewGame(currentNewGameRequestId);
        }
    }

    public void startNewGame() {
        Timber.e("-- startNewGame --");
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("play as \'X\'");
        arrayAdapter.add("play as \'O\'");

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dashboardFragmentView.showNewGameLoader();
                if (which == 0) {
                    currentNewGameRequestId = dashboardFragmentService.startNewGame(new AppSharedPreference(context).getUserEmail(), AppConstants.COIN_TYPE_CROSS);
                } else {
                    currentNewGameRequestId = dashboardFragmentService.startNewGame(new AppSharedPreference(context).getUserEmail(), AppConstants.COIN_TYPE_CIRCLE);
                }
                Timber.e("-- set currentNewGameRequestId: " + currentNewGameRequestId);
            }
        });
        builderSingle.show();
    }

    public void onNewGameCancelClicked() {
        Timber.e("-- onNewGameCancelClicked --");
        if (currentNewGameRequestId != null) {
            dashboardFragmentService.removeNewGame(currentNewGameRequestId);
            currentNewGameRequestId = null;
        }
        dashboardFragmentView.showNewGameButton();
    }

    public void onJoinGameClicked(WaitingGameDetailsModel gameDetailsModel) {
        Timber.e("-- onJoinGameClicked --");
        currentNewGameRequestId = gameDetailsModel.getNodeKeyName();
        String coinType = gameDetailsModel.getUserOneCoinType().equals(AppConstants.COIN_TYPE_CROSS) ? AppConstants.COIN_TYPE_CIRCLE : AppConstants.COIN_TYPE_CROSS;
        gameDetailsModel.setUserTwo(new AppSharedPreference(context).getUserEmail());
        gameDetailsModel.setUserTwoCoinType(coinType);
        gameDetailsModel.setCurrentState("STARTED");
        dashboardFragmentView.showWaitingGameLoader();
        dashboardFragmentService.startANewMatchFromWaitingGame(gameDetailsModel);
        Timber.e("-- set currentNewGameRequestId: " + currentNewGameRequestId);
    }

}

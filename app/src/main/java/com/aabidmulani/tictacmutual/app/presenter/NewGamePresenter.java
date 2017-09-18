package com.aabidmulani.tictacmutual.app.presenter;

import android.content.Context;

import com.aabidmulani.tictacmutual.R;
import com.aabidmulani.tictacmutual.app.services.NewGameService;
import com.aabidmulani.tictacmutual.app.views.NewGameView;
import com.aabidmulani.tictacmutual.callbackListeners.OnCurrentGameDataListener;
import com.aabidmulani.tictacmutual.firebaseModels.CurrentGameDetailsModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.HashMap;

import timber.log.Timber;

public class NewGamePresenter {

    private Context context;
    private NewGameService newGameService;
    private NewGameView newGameView;
    private String currentMatchNodeId;
    private String coinTypeValue;
    private boolean isUserOne;
    private boolean isUserOnesTurn;
    private boolean gameOver;
    private boolean isUsersTurn;
    CurrentGameDetailsModel currentGameDetailsModel;
    private int matchCountMove;

    private ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Timber.e("Calling from: onChildAdded()");
            if (dataSnapshot != null) {
                Timber.e(dataSnapshot.toString());
                if (dataSnapshot.getKey().equals(currentMatchNodeId)) {
                    CurrentGameDetailsModel gameDetailsModel = dataSnapshot.getValue(CurrentGameDetailsModel.class);
                    if (currentGameDetailsModel != null && gameDetailsModel != null) {
                        currentGameDetailsModel = gameDetailsModel;
                        newGameView.updateTheBoard(currentGameDetailsModel.getMovesList());
                    }
                }
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Timber.e("Calling from: onChildChanged()");
            if (dataSnapshot != null) {
                CurrentGameDetailsModel gameDetailsModel = dataSnapshot.getValue(CurrentGameDetailsModel.class);
                if (currentGameDetailsModel != null && gameDetailsModel != null) {
                    currentGameDetailsModel = gameDetailsModel;
                    newGameView.updateTheBoard(currentGameDetailsModel.getMovesList());
                }
            }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Timber.e("Calling from: onChildRemoved()");

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


    public NewGamePresenter(Context context, NewGameService newGameService, NewGameView newGameView, String currentMatchNodeId, String coinTypeValue, boolean isUserOne) {
        this.context = context;
        this.newGameService = newGameService;
        this.newGameView = newGameView;
        Timber.d("currentMatchNodeId:" + currentMatchNodeId);
        this.currentMatchNodeId = currentMatchNodeId;
        this.coinTypeValue = coinTypeValue;
        this.isUserOne = isUserOne;
        gameOver = false;
        isUserOnesTurn = true;
        isUsersTurn = isUserOne;
        matchCountMove = 0;
        getCurrentGameData();
        newGameService.updateUsersGameCount(context);
        if (isUserOne) {
            newGameView.showPlayNowMsg();
        } else {
            newGameView.showWaitingForOtherPlayer();
        }
    }

    private void getCurrentGameData() {
        newGameService.getCurrentGameData(currentMatchNodeId, new OnCurrentGameDataListener() {
            @Override
            public void onGameDataSuccess(CurrentGameDetailsModel gameDetailsModel) {
                currentGameDetailsModel = gameDetailsModel;
                newGameView.updateTheBoard(currentGameDetailsModel.getMovesList());
            }

            @Override
            public void onGameDataError(int resId) {
                newGameView.showErrorMsg(resId);
            }
        });
    }


    public void onBoxClicked(int resId) {
        if (isUsersTurn) {
            String indexKey = null;
            switch (resId) {
                case R.id.boxA:
                    indexKey = "00";
                    break;
                case R.id.boxB:
                    indexKey = "01";
                    break;
                case R.id.boxC:
                    indexKey = "02";
                    break;
                case R.id.boxD:
                    indexKey = "10";
                    break;
                case R.id.boxE:
                    indexKey = "11";
                    break;
                case R.id.boxF:
                    indexKey = "12";
                    break;
                case R.id.boxG:
                    indexKey = "20";
                    break;
                case R.id.boxH:
                    indexKey = "21";
                    break;
                case R.id.boxI:
                    indexKey = "22";
                    break;
            }

            if (indexKey != null) {
                if (currentGameDetailsModel == null) {
                    currentGameDetailsModel = new CurrentGameDetailsModel();
                }
                if (currentGameDetailsModel.getMovesList().get(indexKey) == null) {
                    currentGameDetailsModel.getMovesList().put(indexKey, coinTypeValue);
                    newGameService.updateMove(currentMatchNodeId, currentGameDetailsModel.getMovesList());
                    isUserOnesTurn = !isUserOnesTurn;
                }
            }

        } else {
            newGameView.showErrorMsg(R.string.wait_for_other_players_move);
        }
    }

    public void onAddListener() {
        newGameService.startListeningToGameMove(currentMatchNodeId, childEventListener);
    }

    public void onRemoveAllListener() {
        if (!gameOver) {
            newGameService.updateGameStatus(currentMatchNodeId, "INTERRUPTED");
        }
        newGameService.removeListener();
    }

    public void updateMatchMoveCount(int count) {
        matchCountMove = count;
    }

    public void onExitClicked() {
        newGameView.onEndGame();
    }

    public void updateWinLossCase(int count) {
        updateMatchMoveCount(count);
        if (currentGameDetailsModel != null) {
            HashMap<String, String> localMap = currentGameDetailsModel.getMovesList();
            checkWinLossCases(localMap);
        }
    }

    public void checkWinLossCases(HashMap<String, String> localMap) {
        if (isThisSame(localMap.get("00"), localMap.get("01"), localMap.get("02"))) {
            thisPlayerWon(localMap.get("00"));
        } else if (isThisSame(localMap.get("10"), localMap.get("11"), localMap.get("12"))) {
            thisPlayerWon(localMap.get("10"));
        } else if (isThisSame(localMap.get("20"), localMap.get("21"), localMap.get("22"))) {
            thisPlayerWon(localMap.get("20"));
        } else if (isThisSame(localMap.get("00"), localMap.get("10"), localMap.get("20"))) {
            thisPlayerWon(localMap.get("00"));
        } else if (isThisSame(localMap.get("01"), localMap.get("11"), localMap.get("21"))) {
            thisPlayerWon(localMap.get("01"));
        } else if (isThisSame(localMap.get("02"), localMap.get("12"), localMap.get("12"))) {
            thisPlayerWon(localMap.get("02"));
        } else if (isThisSame(localMap.get("00"), localMap.get("11"), localMap.get("22"))) {
            thisPlayerWon(localMap.get("00"));
        } else if (isThisSame(localMap.get("20"), localMap.get("11"), localMap.get("02"))) {
            thisPlayerWon(localMap.get("20"));
        } else if (matchCountMove == 9) {
            gameOver = true;
            newGameView.showGameDraw();
        } else {
            if (isUserOne) {
                if (matchCountMove % 2 == 0) {
                    newGameView.showPlayNowMsg();
                    isUsersTurn = true;
                } else {
                    newGameView.showWaitingForOtherPlayer();
                    isUsersTurn = false;
                }
            } else {
                if (matchCountMove % 2 == 0) {
                    isUsersTurn = false;
                    newGameView.showWaitingForOtherPlayer();
                } else {
                    isUsersTurn = true;
                    newGameView.showPlayNowMsg();
                }
            }
        }
    }

    private void thisPlayerWon(String s) {
        gameOver = true;
        if (s.equals(coinTypeValue)) {
            newGameService.updateUsersWinCount(context);
            newGameView.showGameWon();
        } else {
            newGameService.updateUsersLossCount(context);
            newGameView.showGameLost();
        }
        newGameService.removeListener();
    }

    private boolean isThisSame(String one, String two, String three) {
        if (one != null && two != null && three != null) {
            return (one.equals(two) && two.equals(three));
        } else {
            return false;
        }
    }

}

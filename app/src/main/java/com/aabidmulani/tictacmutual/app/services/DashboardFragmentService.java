package com.aabidmulani.tictacmutual.app.services;

import android.content.Context;
import android.util.Log;

import com.aabidmulani.tictacmutual.R;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class DashboardFragmentService {

    private DatabaseReference mPostReference;
    private ChildEventListener mPostListener;

    public void getUserDetails(Context context, final OnUserDetailsListener userDetailsListener) {
        Timber.i("-- getUserDetails :" + new AppSharedPreference(context).getUserEmailPath());

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mDatabaseReference.child(AppConstants.TABLE_USER_DATA).child(new AppSharedPreference(context).getUserEmailPath())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            Timber.e(dataSnapshot.toString());
                            if (dataSnapshot != null) {
                                UserDetailsModel userDetailsModel = dataSnapshot.getValue(UserDetailsModel.class);
                                userDetailsListener.onUserDetailsSuccess(userDetailsModel);
                            } else {
                                userDetailsListener.onUserDetailsSuccess(null);
                            }
                        } catch (Exception ex) {
                            Timber.e(Log.getStackTraceString(ex));
                            userDetailsListener.onUserDetailsError(R.string.error_while_reading_user_details);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Timber.i("-- onCancelled --");
                        Timber.e("Error:" + databaseError);
                        userDetailsListener.onUserDetailsError(R.string.error_while_loading_user_details);
                    }
                });

    }


    public void getCurrentWaitingGames(Context context, final OnWaitingGamesListener onWaitingGamesListener) {
        Timber.i("-- getCurrentWaitingGames --");

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mDatabaseReference.child(AppConstants.TABLE_CURRENT_WAITING_GAMES).child(new AppSharedPreference(context).getUserEmailPath())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Timber.i("-- onDataChange --");
                        try {
                            if (dataSnapshot != null) {
                                List<WaitingGameDetailsModel> waitingGameDetailsModels = new ArrayList<WaitingGameDetailsModel>();
                                for (DataSnapshot singleData : dataSnapshot.getChildren()) {
                                    Timber.e(singleData.toString());
                                    try {
                                        WaitingGameDetailsModel table = singleData.getValue(WaitingGameDetailsModel.class);
                                        table.setNodeKeyName(singleData.getKey());
                                        waitingGameDetailsModels.add(table);
                                    } catch (Exception ex) {
                                        Timber.e(Log.getStackTraceString(ex));
                                    }
                                }
                                onWaitingGamesListener.onWaitingGameListSuccess(waitingGameDetailsModels);
                            } else {
                                onWaitingGamesListener.onWaitingGameListSuccess(null);
                            }
                        } catch (Exception ex) {
                            Timber.e(Log.getStackTraceString(ex));
                            onWaitingGamesListener.onWaitingGameListError(R.string.error_while_reading_waiting_game);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Timber.i("-- onCancelled --");
                        Timber.e("Error:" + databaseError);
                        onWaitingGamesListener.onWaitingGameListError(R.string.error_while_loading_waiting_game);
                    }
                });

    }

    public void startListeningToWaitingTable(ChildEventListener childEventListener) {
        Timber.i("-- startListeningToWaitingTable --");
        mPostReference = FirebaseDatabase.getInstance().getReference().child(AppConstants.TABLE_CURRENT_WAITING_GAMES);
        mPostListener = childEventListener;
        mPostReference.addChildEventListener(childEventListener);
    }

    public void removeWaitingTableListener() {
        Timber.i("-- removeWaitingTableListener --");
        if (mPostListener != null && mPostReference != null) {
            mPostReference.removeEventListener(mPostListener);
        }
    }

    public String startNewGame(String userEmail, String coinType) {
        Timber.i("-- startNewGame --");
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference().child(AppConstants.TABLE_CURRENT_WAITING_GAMES);
        mDatabaseReference = mDatabaseReference.push();
        WaitingGameDetailsModel waitingGameDetailsModel = new WaitingGameDetailsModel(userEmail, coinType);
        waitingGameDetailsModel.setNodeKeyName(mDatabaseReference.getKey());
        mDatabaseReference.setValue(waitingGameDetailsModel);
        return mDatabaseReference.getKey();
    }

    public void removeNewGame(String currentNewGameId) {
        Timber.i("-- removeNewGame --");
        FirebaseDatabase.getInstance().getReference().child(AppConstants.TABLE_CURRENT_WAITING_GAMES).child(currentNewGameId).removeValue();
    }

    public String startANewMatchFromWaitingGame(WaitingGameDetailsModel gameDetailsModel) {
        Timber.i("-- startANewMatchFromWaitingGame --");
        DatabaseReference waitingGameTable = FirebaseDatabase.getInstance().getReference().child(AppConstants.TABLE_CURRENT_WAITING_GAMES).child(gameDetailsModel.getNodeKeyName());
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference().child(AppConstants.TABLE_CURRENT_GAME);
        mDatabaseReference = mDatabaseReference.push();
        mDatabaseReference.setValue(new CurrentGameDetailsModel(gameDetailsModel.getUserOne(), gameDetailsModel.getUserOneCoinType(), gameDetailsModel.getUserTwo(), gameDetailsModel.getUserTwoCoinType(), gameDetailsModel.getCurrentState()));
        gameDetailsModel.setNewMatchNodeId(mDatabaseReference.getKey());
        waitingGameTable.updateChildren(gameDetailsModel.getUpdateKey());
        return mDatabaseReference.getKey();
    }
}

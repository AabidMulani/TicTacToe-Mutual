package com.aabidmulani.tictacmutual.app.services;

import android.content.Context;
import android.util.Log;

import com.aabidmulani.tictacmutual.R;
import com.aabidmulani.tictacmutual.callbackListeners.OnCurrentGameDataListener;
import com.aabidmulani.tictacmutual.firebaseModels.CurrentGameDetailsModel;
import com.aabidmulani.tictacmutual.firebaseModels.UserDetailsModel;
import com.aabidmulani.tictacmutual.sharedpreferences.AppSharedPreference;
import com.aabidmulani.tictacmutual.utils.AppConstants;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import timber.log.Timber;

public class NewGameService {


    private DatabaseReference mPostReference;
    private ChildEventListener mPostListener;

    public void startListeningToGameMove(String matchId, ChildEventListener childEventListener) {
        Timber.i("-- startListeningToWaitingTable --");
        Timber.d("matchId:" + matchId);
        mPostReference = FirebaseDatabase.getInstance().getReference().child(AppConstants.TABLE_CURRENT_GAME);
        mPostListener = childEventListener;
        mPostReference.addChildEventListener(childEventListener);
    }

    public void removeListener() {
        Timber.i("-- removeWaitingTableListener --");
        if (mPostListener != null && mPostReference != null) {
            mPostReference.removeEventListener(mPostListener);
        }
    }

    public void updateUsersGameCount(final Context context) {
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mDatabaseReference.child(AppConstants.TABLE_USER_DATA).child(new AppSharedPreference(context).getUserEmailPath())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Timber.i("-- onDataChange --");
                        try {
                            if (dataSnapshot != null) {
                                UserDetailsModel userDetailsModel = dataSnapshot.getValue(UserDetailsModel.class);
                                DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference().child(AppConstants.TABLE_USER_DATA).child(new AppSharedPreference(context).getUserEmailPath()).child(AppConstants.COUNT_TOTAL);
                                if (userDetailsModel != null && userDetailsModel.getTotalGames() != null) {
                                    mDatabaseReference.setValue(userDetailsModel.getTotalGames() + 1);
                                } else {
                                    mDatabaseReference.setValue(1);
                                }
                            }
                        } catch (Exception ex) {
                            Timber.e(Log.getStackTraceString(ex));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Timber.i("-- onCancelled --");
                        Timber.e("Error:" + databaseError);
                    }
                });
    }

    public void updateUsersWinCount(final Context context) {
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mDatabaseReference.child(AppConstants.TABLE_USER_DATA).child(new AppSharedPreference(context).getUserEmailPath())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Timber.i("-- onDataChange --");
                        try {
                            if (dataSnapshot != null) {
                                UserDetailsModel userDetailsModel = dataSnapshot.getValue(UserDetailsModel.class);
                                DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference().child(AppConstants.TABLE_USER_DATA).child(new AppSharedPreference(context).getUserEmailPath()).child(AppConstants.COUNT_WIN);
                                if (userDetailsModel != null && userDetailsModel.getWinCount() != null) {
                                    mDatabaseReference.setValue(userDetailsModel.getWinCount() + 1);
                                } else {
                                    mDatabaseReference.setValue(1);
                                }
                            }
                        } catch (Exception ex) {
                            Timber.e(Log.getStackTraceString(ex));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Timber.i("-- onCancelled --");
                        Timber.e("Error:" + databaseError);
                    }
                });
    }


    public void updateUsersLossCount(final Context context) {
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mDatabaseReference.child(AppConstants.TABLE_USER_DATA).child(new AppSharedPreference(context).getUserEmailPath())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Timber.i("-- onDataChange --");
                        try {
                            if (dataSnapshot != null) {
                                UserDetailsModel userDetailsModel = dataSnapshot.getValue(UserDetailsModel.class);
                                DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference().child(AppConstants.TABLE_USER_DATA).child(new AppSharedPreference(context).getUserEmailPath()).child(AppConstants.COUNT_LOSS);
                                if (userDetailsModel != null && userDetailsModel.getLossCount() != null) {
                                    mDatabaseReference.setValue(userDetailsModel.getLossCount() + 1);
                                } else {
                                    mDatabaseReference.setValue(1);
                                }
                            }
                        } catch (Exception ex) {
                            Timber.e(Log.getStackTraceString(ex));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Timber.i("-- onCancelled --");
                        Timber.e("Error:" + databaseError);
                    }
                });
    }

    public void updateMove(String matchId, HashMap<String, String> movesList) {
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference().child(AppConstants.TABLE_CURRENT_GAME).child(matchId).child(AppConstants.MOVES_FIELD);
        mDatabaseReference.setValue(movesList);
    }

    public void updateGameStatus(String matchId, String status) {
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference().child(AppConstants.TABLE_CURRENT_GAME).child(matchId).child(AppConstants.GAME_STATUS);
        mDatabaseReference.setValue(status);
    }


    public void getCurrentGameData(String currentMatchNodeId, final OnCurrentGameDataListener onCurrentGameDataListener) {

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mDatabaseReference.child(AppConstants.TABLE_CURRENT_GAME).child(currentMatchNodeId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Timber.i("-- onDataChange --");
                        try {
                            if (dataSnapshot != null) {
                                CurrentGameDetailsModel detailsModel = dataSnapshot.getValue(CurrentGameDetailsModel.class);
                                onCurrentGameDataListener.onGameDataSuccess(detailsModel);

                            }
                        } catch (Exception ex) {
                            Timber.e(Log.getStackTraceString(ex));
                            onCurrentGameDataListener.onGameDataError(R.string.error_while_reading_game_data);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Timber.i("-- onCancelled --");
                        Timber.e("Error:" + databaseError);
                        onCurrentGameDataListener.onGameDataError(R.string.error_while_loading_game_data);
                    }
                });

    }
}

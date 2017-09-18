package com.aabidmulani.tictacmutual.app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aabidmulani.tictacmutual.R;
import com.aabidmulani.tictacmutual.callbackListeners.OnWaitingGameClickedListener;
import com.aabidmulani.tictacmutual.firebaseModels.WaitingGameDetailsModel;
import com.aabidmulani.tictacmutual.utils.AppConstants;
import com.aabidmulani.tictacmutual.widgets.RobotoTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WaitingGameListingAdapter extends RecyclerView.Adapter<WaitingGameListingAdapter.RepoDetailsViewHolder> {

    private Context context;
    private List<WaitingGameDetailsModel> waitinGameDetailsModelsList;
    private OnWaitingGameClickedListener onWaitingGameClickedListener;

    public WaitingGameListingAdapter(Context context, List<WaitingGameDetailsModel> waitinGameDetailsModelsList, OnWaitingGameClickedListener onWaitingGameClickedListener) {
        this.context = context;
        this.waitinGameDetailsModelsList = waitinGameDetailsModelsList;
        this.onWaitingGameClickedListener = onWaitingGameClickedListener;
    }

    public void setWaitinGameDetailsModelsList(List<WaitingGameDetailsModel> waitinGameDetailsModelsList) {
        this.waitinGameDetailsModelsList = waitinGameDetailsModelsList;
    }

    @Override
    public RepoDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_waiting_game, parent, false);
        return new RepoDetailsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RepoDetailsViewHolder holder, int position) {

        final WaitingGameDetailsModel currentObject = waitinGameDetailsModelsList.get(position);

        holder.gameUserEmail.setText(currentObject.getUserOne());
        if (currentObject.getUserOneCoinType().equals(AppConstants.COIN_TYPE_CIRCLE)) {
            holder.otherPlayerSelection.setText("you will play as " + AppConstants.COIN_TYPE_CROSS);
        } else {
            holder.otherPlayerSelection.setText("you will play as " + AppConstants.COIN_TYPE_CIRCLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onWaitingGameClickedListener.onGameClicked(currentObject);
            }
        });
    }

    @Override
    public int getItemCount() {
        return waitinGameDetailsModelsList.size();
    }


    class RepoDetailsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.creatorUserEmail)
        RobotoTextView gameUserEmail;

        @BindView(R.id.optionSelection)
        RobotoTextView otherPlayerSelection;


        RepoDetailsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}

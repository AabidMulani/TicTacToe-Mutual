package com.aabidmulani.tictacmutual.firebaseModels;

public class UserDetailsModel {

    Integer totalGames;
    Integer winCount;
    Integer lossCount;

    public UserDetailsModel() {
    }

    public Integer getTotalGames() {
        return totalGames == null ? 0 : totalGames;
    }

    public void setTotalGames(Integer totalGames) {
        this.totalGames = totalGames;
    }

    public Integer getWinCount() {
        return winCount == null ? 0 : winCount;
    }

    public void setWinCount(Integer winCount) {
        this.winCount = winCount;
    }

    public Integer getLossCount() {
        return lossCount == null ? 0 : lossCount;
    }

    public void setLossCount(Integer lossCount) {
        this.lossCount = lossCount;
    }
}

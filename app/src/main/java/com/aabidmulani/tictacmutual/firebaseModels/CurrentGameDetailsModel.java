package com.aabidmulani.tictacmutual.firebaseModels;


import java.util.HashMap;

public class CurrentGameDetailsModel {

    String userOne;
    String userOneCoinType;
    String userTwo;
    String userTwoCoinType;
    String currentState;
    HashMap<String, String> movesList;

    public CurrentGameDetailsModel() {
    }

    public CurrentGameDetailsModel(String userOne, String userOneCoinType, String userTwo, String userTwoCoinType, String currentState) {
        this.userOne = userOne;
        this.userOneCoinType = userOneCoinType;
        this.userTwo = userTwo;
        this.userTwoCoinType = userTwoCoinType;
        this.currentState = currentState;
        movesList = new HashMap<>();
    }

    public String getUserOne() {
        return userOne;
    }

    public void setUserOne(String userOne) {
        this.userOne = userOne;
    }

    public String getUserOneCoinType() {
        return userOneCoinType;
    }

    public void setUserOneCoinType(String userOneCoinType) {
        this.userOneCoinType = userOneCoinType;
    }

    public String getUserTwo() {
        return userTwo;
    }

    public void setUserTwo(String userTwo) {
        this.userTwo = userTwo;
    }

    public String getUserTwoCoinType() {
        return userTwoCoinType;
    }

    public void setUserTwoCoinType(String userTwoCoinType) {
        this.userTwoCoinType = userTwoCoinType;
    }

    public HashMap<String, String> getMovesList() {
        if (movesList == null) {
            movesList = new HashMap<>();
        }
        return movesList;
    }

    public void setMovesList(HashMap<String, String> movesList) {
        this.movesList = movesList;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }
}

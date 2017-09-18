package com.aabidmulani.tictacmutual.firebaseModels;

import java.util.HashMap;
import java.util.Map;

public class WaitingGameDetailsModel {

    String nodeKeyName;
    String userOne;
    String userOneCoinType;
    String userTwo;
    String userTwoCoinType;
    String currentState;
    String newMatchNodeId;

    public WaitingGameDetailsModel() {
    }

    public WaitingGameDetailsModel(String userOne, String userOneCoinType) {
        this.userOne = userOne;
        this.userOneCoinType = userOneCoinType;
        this.currentState = "WAITING";
    }

    public String getNewMatchNodeId() {
        return newMatchNodeId;
    }

    public void setNewMatchNodeId(String newMatchNodeId) {
        this.newMatchNodeId = newMatchNodeId;
    }

    public String getNodeKeyName() {
        return nodeKeyName;
    }

    public void setNodeKeyName(String nodeKeyName) {
        this.nodeKeyName = nodeKeyName;
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

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public Map<String, Object> getUpdateKey() {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("userTwo", userTwo);
        objectMap.put("userTwoCoinType", userTwoCoinType);
        objectMap.put("currentState", currentState);
        objectMap.put("newMatchNodeId", newMatchNodeId);
        return objectMap;
    }
}

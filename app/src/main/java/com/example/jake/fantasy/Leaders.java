package com.example.jake.fantasy;

/**
 * Created by jake on 1/13/18.
 */

public class Leaders {
    String teamName,owner,userId;
    int points;

    public Leaders() {
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getOwner() {
        return owner;
    }

    public String getUserId() {
        return userId;
    }

    public int getPoints() {
        return points;
    }
}

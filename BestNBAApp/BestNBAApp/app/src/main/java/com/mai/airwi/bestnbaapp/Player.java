package com.mai.airwi.bestnbaapp;

/**
 * Created by hans on 4/18/20.
 */
import java.util.*;

public class Player {
    //["00","1610612747","1948","2019","LAL","Lakers","1948","Los Angeles","Staples Center",
    // "19060","Jerry Buss Family Trust","Rob Pelinka","Frank Vogel","South Bay Lakers"]

    int teamID, playerID, season;
    String playerName;

    public Player(String playerName, int teamID, int playerID, int season) {
        this.playerName = playerName;
        this.teamID = teamID;
        this.playerID = playerID;
        this.season = season;
    }

    public Player(List<String> set){
        this.playerName = set.get(0);
        this.teamID = Integer.parseInt(set.get(1));
        this.playerID = Integer.parseInt(set.get(2));
        this.season = Integer.parseInt(set.get(3));
    }

    public String getPlayerName(){
        return this.playerName;
    }

    public int getTeamID(){
        return this.teamID;
    }

    public int getPlayerID(){
        return this.playerID;
    }

    public int getSeason(){
        return this.season;
    }
}

package com.mai.airwi.bestnbaapp;

/**
 * Created by hans on 4/18/20.
 */

public class Player {
    //["00","1610612747","1948","2019","LAL","Lakers","1948","Los Angeles","Staples Center",
    // "19060","Jerry Buss Family Trust","Rob Pelinka","Frank Vogel","South Bay Lakers"]

    int leagueID, teamID, minYear, maxYear, yearFounded, arenaCapacity;
    String abbr, nickname, city, arena, owner, headCoach, DLeagueAffiliate;

    public Player(String leagueID, String teamID, String minYear, String maxYear, String abbr,
                  String nickname, String yearFounded, String city, String arena,
                  String arenaCapacity, String owner, String headCoach, String DLeagueAffiliate) {

        this.leagueID = Integer.parseInt(leagueID);
        this.teamID = Integer.parseInt(teamID);
        this.minYear = Integer.parseInt(minYear);
        this.maxYear = Integer.parseInt(maxYear);
        this.yearFounded = Integer.parseInt(yearFounded);
        this.arenaCapacity = Integer.parseInt(arenaCapacity);

        this.abbr = abbr;
        this.nickname = nickname;
        this.city = city;
        this.arena = arena;
        this.owner = owner;
        this.headCoach = headCoach;
        this.DLeagueAffiliate = DLeagueAffiliate;
    }

    public int getLeagueID() {
        return this.leagueID;
    }

    public int getTeamID() {
        return this.teamID;
    }

    public int getMinYear() {
        return this.minYear;
    }

    public int getMaxYear() {
        return this.maxYear;
    }

    public int getYearFounded() {
        return this.yearFounded;
    }

    public int getArenaCapacity() {
        return this.arenaCapacity;
    }

    public String getAbbr() {
        return this.abbr;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getCity() {
        return this.city;
    }

    public String getArena() {
        return this.arena;
    }

    public String getOwner() {
        return this.owner;
    }

    public String getHeadCoach() {
        return this.headCoach;
    }

    public String getDLeagueAffiliate() {
        return this.DLeagueAffiliate;
    }
}

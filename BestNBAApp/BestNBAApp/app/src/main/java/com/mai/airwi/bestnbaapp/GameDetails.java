package com.mai.airwi.bestnbaapp;
import android.widget.TextView;

import java.util.*;

/**
 * Created by airwi on 4/24/2020.
 */

public class GameDetails {

    int game_id, team_id, player_id;
    String team_abbr, team_city, player_name, start_pos, comment;
    float min, fgm, fga, fg_pct, fg3m, fg3a, fg3_pct, ftm,
        fta, ft_pct, oreb, dreb;

    public GameDetails(List<String> set){

        this.game_id = Integer.parseInt(set.get(0));
        this.team_id = Integer.parseInt(set.get(1));

        this.team_abbr = set.get(2);
        this.team_city = set.get(3);

        this.player_id = Integer.parseInt(set.get(4));

        this.player_name = set.get(5);
        this.start_pos = set.get(6);
        this.comment = set.get(7);

        this.min = Float.parseFloat(set.get(8));
        this.fgm = Float.parseFloat(set.get(9));
        this.fga = Float.parseFloat(set.get(10));
        this.fg_pct = Float.parseFloat(set.get(11));

        this.fg3m = Float.parseFloat(set.get(12));
        this.fg3a = Float.parseFloat(set.get(13));
        this.fg3_pct = Float.parseFloat(set.get(14));

        this.ftm = Float.parseFloat(set.get(15));
        this.fta = Float.parseFloat(set.get(16));
        this.ft_pct = Float.parseFloat(set.get(17));

        this.oreb = Float.parseFloat(set.get(18));
        this.dreb = Float.parseFloat(set.get(19));
    }

    public GameDetails(List<List<String>> set, int index){
        this.game_id = Integer.parseInt(set.get(index).get(1));
        this.team_id = Integer.parseInt(set.get(index).get(2));

        this.team_abbr = set.get(index).get(3);
        this.team_city = set.get(index).get(4);

        this.player_id = Integer.parseInt(set.get(index).get(5));

        this.player_name = set.get(index).get(6);
        this.start_pos = set.get(index).get(7);
        this.comment = set.get(index).get(8);

        this.min = Float.parseFloat(set.get(index).get(9));
        this.fgm = Float.parseFloat(set.get(index).get(10));
        this.fga = Float.parseFloat(set.get(index).get(11));
        this.fg_pct = Float.parseFloat(set.get(index).get(12));

        this.fg3m = Float.parseFloat(set.get(index).get(13));
        this.fg3a = Float.parseFloat(set.get(index).get(14));
        this.fg3_pct = Float.parseFloat(set.get(index).get(15));

        this.ftm = Float.parseFloat(set.get(index).get(16));
        this.fta = Float.parseFloat(set.get(index).get(17));
        this.ft_pct = Float.parseFloat(set.get(index).get(18));

        this.oreb = Float.parseFloat(set.get(index).get(19));
        this.dreb = Float.parseFloat(set.get(index).get(20));
    }

    public int getGame_id(){
        return this.game_id;
    }

    public int getTeam_id(){
        return this.team_id;
    }

    public String getTeam_abbr(){
        return this.team_abbr;
    }

    public String getTeam_city(){
        return this.team_city;
    }

    public int getPlayer_id(){
        return this.player_id;
    }

    public String getPlayer_name(){
        return this.player_name;
    }

    public String getStart_pos(){
        return this.start_pos;
    }

    public String getComment(){
        return this.comment;
    }

    public float getMin(){
        return this.min;
    }

    public float getFgm(){
        return this.fgm;
    }

    public float getFga(){
        return this.fga;
    }

    public float getFg_pct(){
        return this.fg_pct;
    }

    public float getFg3m(){
        return this.fg3m;
    }

    public float getFg3a(){
        return this.fg3a;
    }

    public float getFg3_pct(){
        return this.fg3_pct;
    }

    public float getFtm(){
        return this.ftm;
    }

    public float getFta(){
        return this.fta;
    }

    public float getFt_pct(){
        return this.ft_pct;
    }

    public float getOreb(){
        return this.oreb;
    }

    public float getDreb(){
        return this.dreb;
    }

    //////////////////////////
    // modifiers
    //////////////////////////
    public void setGame_id(int game_id){
        this.game_id = game_id;
    }

    public void setTeam_id(int team_id){
        this.team_id = team_id;
    }

    public void setTeam_abbr(String team_abbr){
        this.team_abbr = team_abbr;
    }

    public void setTeam_city(String team_city){
        this.team_city = team_city;
    }

    public void setPlayer_id(int player_id){
        this.player_id = player_id;
    }

    public void setPlayer_name(String player_name){
        this.player_name = player_name;
    }

    public void setStart_pos(String start_pos){
        this.start_pos = start_pos;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public void setMin(float min){
        this.min = min;
    }

    public void setFgm(float fgm){
        this.fgm = fgm;
    }

    public void setFga(float fga){
        this.fga = fga;
    }

    public void setFg_pct(float fg_pct){
        this.fg_pct = fg_pct;
    }

    public void setFg3m(float fg3m){
        this.fg3m = fg3m;
    }

    public void setFg3a(float fg3a){
        this.fg3a = fg3a;
    }

    public void setFg3_pct(float fg3_pct){
        this.fg3_pct = fg3_pct;
    }

    public void setFtm(float ftm){
        this.ftm = ftm;
    }

    public void setFta(float fta){
        this.fta = fta;
    }

    public void setFt_pct(float ft_pct){
        this.ft_pct = ft_pct;
    }

    public void setOreb(float oreb){
        this.oreb = oreb;
    }

    public void setDreb(float dreb){
        this.dreb = dreb;
    }

    public void print(TextView display, String existing) {
        String toDisplay;

        toDisplay = "Game ID: " + this.getGame_id();

        display.setText(toDisplay);
    }
}

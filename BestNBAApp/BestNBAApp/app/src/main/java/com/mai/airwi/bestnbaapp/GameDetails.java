package com.mai.airwi.bestnbaapp;
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
}

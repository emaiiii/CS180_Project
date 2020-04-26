package com.mai.airwi.bestnbaapp;
import android.widget.TextView;

import java.util.*;

/**
 * Created by airwi on 4/24/2020.
 */

public class Games {

   String game_date_est, game_status;
   int game_id, home_team_id, visitor_team_id, season, team_id_home,
           pts_home, team_id_away, pts_away, ast_home, reb_home,
            ast_away, reb_away;
   float fg_pct_home, ft_pct_home, fg3_pct_home, fg_pct_away,
           ft_pct_away, fg3_pct_away;

   public Games(List<String> set){

       this.game_date_est = set.get(0);

       this.game_id = Integer.parseInt(set.get(1));

       this.game_status = set.get(2);

       this.home_team_id = Integer.parseInt(set.get(3));
       this.visitor_team_id = Integer.parseInt(set.get(4));
       this.season = Integer.parseInt(set.get(5));
       this.team_id_home = Integer.parseInt(set.get(6));
       this.pts_home = Integer.parseInt(set.get(7));

       this.fg_pct_home = Float.parseFloat(set.get(8));
       this.ft_pct_home = Float.parseFloat(set.get(9));
       this.fg3_pct_home = Float.parseFloat(set.get(10));

       this.ast_home = Integer.parseInt(set.get(11));
       this.reb_home = Integer.parseInt(set.get(12));
       this.team_id_away = Integer.parseInt(set.get(13));
       this.pts_away = Integer.parseInt(set.get(14));

       this.fg_pct_away = Float.parseFloat(set.get(15));
       this.ft_pct_away = Float.parseFloat(set.get(16));
       this.fg3_pct_away = Float.parseFloat(set.get(17));

       this.ast_away = Integer.parseInt(set.get(18));
       this.reb_away = Integer.parseInt(set.get(19));
   }

   public String getGame_date_est(){
       return this.game_date_est;
   }

   public int getGame_id(){
       return this.game_id;
   }

   public String getGame_status(){
       return this.game_status;
   }

   public int getHome_team_id(){
       return this.home_team_id;
   }

   public int getVisitor_team_id(){
       return this.visitor_team_id;
   }

   public int getSeason(){
       return this.season;
   }

   public int getTeam_id_home(){
       return this.team_id_home;
   }

   public int getPts_home(){
       return this.pts_home;
   }

   public float getFg_pct_home(){
       return this.fg_pct_home;
   }

   public float getFt_pct_home(){
       return this.ft_pct_home;
   }

   public float getFg3_pct_home(){
       return this.fg3_pct_home;
   }

   public int getAst_home(){
       return this.ast_home;
   }
   public int getReb_home(){
       return this.reb_home;
   }

   public int getTeam_id_away(){
       return this.team_id_away;
   }

   public int getPts_away(){
       return this.pts_away;
   }

   public float getFg_pct_away (){
       return this.fg_pct_away;
   }

   public float getFt_pct_away(){
       return this.ft_pct_away;
   }

   public float getFg3_pct_away(){
       return this.fg3_pct_away;
   }

   public int getAst_away(){
       return this.ast_away;
   }

   public int getReb_away(){
       return this.reb_away;
   }

   public void print(TextView display, String existing) {
       String toDisplay;

       toDisplay = "Game ID: " + this.getGame_id();
       toDisplay = toDisplay + "\nSeason: " + this.getSeason();
       toDisplay = toDisplay + "\n" + this.getTeam_id_away() + " @ " + this.getTeam_id_home();

       toDisplay = existing + "\n\n" + toDisplay;

       display.setText(toDisplay);
   }
}

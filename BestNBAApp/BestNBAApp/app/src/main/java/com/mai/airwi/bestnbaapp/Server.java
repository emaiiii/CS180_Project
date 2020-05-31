package com.mai.airwi.bestnbaapp;

/**
 * Created by airwi on 4/25/2020.
 */

public class Server {

    String url;

    public Server(){
        this.url = "http://6975d954af81.ngrok.io/";
    }

    public String getUrl(){
        return this.url;
    }

    public void setURL(String url){
        this.url = url;
    }
}

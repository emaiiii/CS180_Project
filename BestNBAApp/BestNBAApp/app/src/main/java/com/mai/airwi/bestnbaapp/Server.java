package com.mai.airwi.bestnbaapp;

/**
 * Created by airwi on 4/25/2020.
 */

public class Server {

    String url;

    public Server(String newURL){
        this.url = newURL;
    }

    public String getUrl(){
        return this.url;
    }

    public void setUrl(String newURL){
        this.url = newURL;
    }
}

package com.mai.airwi.bestnbaapp;

public class Server {

    String url;

    public Server() {
        this.url = "http://dd85d2512d53.ngrok.io/";
    }

    public String getUrl() {
        return this.url;
    }

    public void setURL(String url){
        this.url = url;
    }

}

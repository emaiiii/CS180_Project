package com.mai.airwi.bestnbaapp;

import java.util.List;

/**
 * Created by airwi on 5/2/2020.
 */

public class UserAccount {

    String userName;
    String firstName;
    String lastName;
    String email;
    String password;

    public UserAccount(String userName, String firstName, String lastName, String email, String password){
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public UserAccount(List<String> set){
        this.userName = set.get(0);
        this.firstName = set.get(1);
        this.lastName = set.get(2);
        this.email = set.get(3);
        this.password = set.get(4);
    }

    // accessors
    public String getUserName(){
        return this.userName;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    // mutators
    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setAccount(List<String> set){
        this.userName = set.get(0);
        this.firstName = set.get(1);
        this.lastName = set.get(2);
        this.email = set.get(3);
        this.password = set.get(4);
    }

    // other functions
    public boolean correctPass(String input){
        if(this.password == input){
            return true;
        }
        else{
            return false;
        }
    }
}

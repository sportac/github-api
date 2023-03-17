package com.example.githubapi.models.repository;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Owner implements Serializable {

    /***********************************************************************************************
     *                                     ATTRIBUTES
     **********************************************************************************************/
    @SerializedName("login")
    @Expose
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

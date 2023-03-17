package com.example.githubapi.models.commit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Committer {
    /***********************************************************************************************
     *                                     ATTRIBUTES
     **********************************************************************************************/
    @SerializedName("login")
    @Expose
    private String username;

    @SerializedName("avatar_url")
    @Expose
    private String avatar;

    public Committer(String username, String avatar) {
        this.username = username;
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

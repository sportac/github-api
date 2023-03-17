package com.example.githubapi.models.commit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommitDate {

    /***********************************************************************************************
     *                                     ATTRIBUTES
     **********************************************************************************************/
    @SerializedName("date")
    @Expose
    private String date;

    public CommitDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

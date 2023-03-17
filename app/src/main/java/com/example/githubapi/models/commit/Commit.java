package com.example.githubapi.models.commit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Commit {

    /***********************************************************************************************
     *                                     ATTRIBUTES
     **********************************************************************************************/
    @SerializedName("committer")
    @Expose
    private CommitDate commitDate;

    @SerializedName("message")
    @Expose
    private String message;

    public Commit(CommitDate commitDate, String message) {
        this.commitDate = commitDate;
        this.message = message;
    }

    public CommitDate getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(CommitDate commitDate) {
        this.commitDate = commitDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

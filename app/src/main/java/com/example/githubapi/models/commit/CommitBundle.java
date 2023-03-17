package com.example.githubapi.models.commit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommitBundle {

    /***********************************************************************************************
     *                                     ATTRIBUTES
     **********************************************************************************************/
    @SerializedName("commit")
    @Expose
    private Commit commit;

    @SerializedName("committer")
    @Expose
    private Committer committer;

    public CommitBundle(Commit commit, Committer committer) {
        this.commit = commit;
        this.committer = committer;
    }

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    public Committer getCommitter() {
        return committer;
    }

    public void setCommitter(Committer committer) {
        this.committer = committer;
    }
}

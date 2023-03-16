package com.example.githubapi.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Repository {
    /***********************************************************************************************
     *                                     ATTRIBUTES
     **********************************************************************************************/
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("language")
    @Expose
    private String language;

    @SerializedName("updated_at")
    @Expose
    private String lastUpdated;

    @SerializedName("visibility")
    @Expose
    private String visibility;

    public Repository(String name, String description, String language) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
}

package com.example.githubapi.repositories;

import android.app.Application;

import com.example.githubapi.models.Repository;
import com.example.githubapi.network.GithubApi;
import com.example.githubapi.network.RetrofitFactory;

import java.util.List;

import io.reactivex.Observable;

public class GithubRepository {

    /***********************************************************************************************
     *                                   CONSTANTS
     **********************************************************************************************/
    private final static String AUTHORIZATION_TOKEN_LABEL = "Bearer ";
    private final static String AUTHORIZATION_TOKEN = "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";
    private final static String HEADER_ACCEPT = "application/vnd.github+json";
    private final static String HEADER_API_VERSION = "2022-11-28";

    /***********************************************************************************************
     *                                     ATTRIBUTES
     **********************************************************************************************/
    /** Make repository singleton */
    private static GithubRepository INSTANCE;
    /** Instance of Github API*/
    private final GithubApi mGithubApi;

    /***********************************************************************************************
     *                                     CONSTRUCTOR
     **********************************************************************************************/
    /**
     * @brief Private class constructor. Initializes the Github API.
     */
    private GithubRepository(Application application) {
        mGithubApi = RetrofitFactory.getGithubApi();
    }

    /***********************************************************************************************
     *                                   PUBLIC METHODS
     **********************************************************************************************/
    /**
     * @brief Make class singleton. Only initialized if there is no previous instance.
     */
    public static GithubRepository getInstance (Application application) {
        if (INSTANCE == null) {
            synchronized (GithubRepository.class) {
                if (INSTANCE == null) {
                    // Create the repository for first time
                    INSTANCE = new GithubRepository(application);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * @brief Get Github repositories.
     */
    public Observable<List<Repository>> executeGetRepositories() {

        String authorizationToken = AUTHORIZATION_TOKEN_LABEL + AUTHORIZATION_TOKEN;

        return mGithubApi.getRepositories(authorizationToken);
    }

}

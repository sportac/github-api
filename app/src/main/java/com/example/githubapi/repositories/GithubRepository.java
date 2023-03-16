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

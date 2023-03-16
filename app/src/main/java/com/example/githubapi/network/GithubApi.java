package com.example.githubapi.network;

import com.example.githubapi.models.Repository;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;


/**
 * @brief Class containing the http requests to be sent to the Github API.
 */
public interface GithubApi {

    String HEADER_AUTHORIZATION = "Authorization";

    /***********************************************************************************************
     *                                   GET
     **********************************************************************************************/

    /** Get repositories */
    @GET("/user/repos")
    Observable<List<Repository>> getRepositories(
            @Header(HEADER_AUTHORIZATION) String token);

}

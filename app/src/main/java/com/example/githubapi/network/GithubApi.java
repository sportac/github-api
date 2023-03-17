package com.example.githubapi.network;

import com.example.githubapi.models.commit.CommitBundle;
import com.example.githubapi.models.repository.Repository;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;


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

    /** Get commits */
    @GET("/repos/{userName}/{repoName}/commits")
    Observable<List<CommitBundle>> getCommits(
            @Header(HEADER_AUTHORIZATION) String token,
            @Path(value="userName") String userName,
            @Path(value="repoName") String repoName);

}

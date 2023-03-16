package com.example.githubapi.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @brief Class used to initialize the API.
 */
public class RetrofitFactory {


    /***********************************************************************************************
     *                                     CONSTANTS
     **********************************************************************************************/
    private static final String API_URL= "https://api.github.com";

    /***********************************************************************************************
     *                                     PUBLIC METHODS
     **********************************************************************************************/
    /**
     * @brief Initializes the Github Api.
     */
    public static GithubApi getGithubApi(){
        // Create the Http client to increase the timeout
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(15, TimeUnit.SECONDS);
        client.readTimeout(15, TimeUnit.SECONDS);
        client.writeTimeout(15, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();

        return retrofit.create(GithubApi.class);
    }
}

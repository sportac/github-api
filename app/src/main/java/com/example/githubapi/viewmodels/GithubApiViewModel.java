package com.example.githubapi.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.wearelupa.network.ApiResponse;

import com.example.githubapi.repositories.GithubRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class GithubApiViewModel extends AndroidViewModel {

    /***********************************************************************************************
     *                                     CONSTANTS
     **********************************************************************************************/
    private static final String TAG = GithubApiViewModel.class.getName();

    /***********************************************************************************************
     *                                     ATTRIBUTES
     **********************************************************************************************/
    private GithubRepository mGithubRepository;

    private final CompositeDisposable mGetRepositoriesDisposable;
    private final CompositeDisposable mGetCommitsDisposable;

    private final MutableLiveData<ApiResponse> mGetRepositoriesResponseLiveData;
    private final MutableLiveData<ApiResponse> mGetCommitsResponseLiveData;


    /***********************************************************************************************
     *                                     CONSTRUCTOR
     **********************************************************************************************/
    /**
     * @brief Initialize Composite Disposables and Mutable Live Data.
     */
    public GithubApiViewModel(@NonNull Application application) {
        super(application);

        mGithubRepository = GithubRepository.getInstance(application);

        //Init Disposables
        mGetRepositoriesDisposable = new CompositeDisposable();
        mGetCommitsDisposable = new CompositeDisposable();

        //Init Response Live Data
        mGetRepositoriesResponseLiveData = new MutableLiveData<>();
        mGetCommitsResponseLiveData = new MutableLiveData<>();
    }

    /***********************************************************************************************
     *                                   OVERRIDE METHODS
     **********************************************************************************************/
    @Override
    protected void onCleared() {
        super.onCleared();
        mGetRepositoriesDisposable.clear();
        mGetCommitsDisposable.clear();
    }

    /***********************************************************************************************
     *                                   PUBLIC METHODS
     **********************************************************************************************/
    public LiveData<ApiResponse> getRepositoriesResponse() {
        return mGetRepositoriesResponseLiveData;
    }

    public LiveData<ApiResponse> getCommitsResponse() {
        return mGetCommitsResponseLiveData;
    }

    /**
     * @brief Get Repositories.
     */
    public void getRepositories() {
        mGetRepositoriesDisposable.add(mGithubRepository.executeGetRepositories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mGetRepositoriesResponseLiveData.setValue(ApiResponse.loading(0)))
                .subscribe(
                        repositoriesData -> {
                            mGetRepositoriesResponseLiveData.setValue(ApiResponse.success(repositoriesData));
                        },
                        throwable -> {
                            mGetRepositoriesResponseLiveData.setValue(ApiResponse.error(throwable));
                        })
        );
    }

    /**
     * @brief Get Repositories.
     */
    public void getCommits(int pageNumber, String repositoryName, String userName) {
        mGetRepositoriesDisposable.add(mGithubRepository.executeGetCommits(pageNumber, repositoryName, userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mGetCommitsResponseLiveData.setValue(ApiResponse.loading(0)))
                .subscribe(
                        commitsData -> {
                            mGetCommitsResponseLiveData.setValue(ApiResponse.success(commitsData));
                        },
                        throwable -> {
                            mGetCommitsResponseLiveData.setValue(ApiResponse.error(throwable));
                        })
        );
    }

}

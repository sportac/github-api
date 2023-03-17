package com.example.githubapi.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.githubapi.ui.adapters.RepositoriesRecyclerViewAdapter;
import com.example.githubapi.util.LogUtil;
import com.wearelupa.network.ApiResponse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.githubapi.R;
import com.example.githubapi.databinding.ActivityMainBinding;
import com.example.githubapi.models.Repository;
import com.example.githubapi.viewmodels.GithubApiViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    /***********************************************************************************************
     *                                     CONSTANTS
     **********************************************************************************************/
    /** Class name to be displayed in LogUtils */
    private static final String TAG = MainActivity.class.getName();

    /***********************************************************************************************
     *                                     ATTRIBUTES
     **********************************************************************************************/
    /** View binding object to retrieve the objects from the class */
    private ActivityMainBinding mBinding;
    /**Repositories Adapter*/
    private RepositoriesRecyclerViewAdapter mRepositoriesAdapter;
    /** ViewModel to retrieve data from Github API.*/
    private GithubApiViewModel mGithubApiViewModel;

    /***********************************************************************************************
     *                                   OVERRIDE METHODS
     **********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        initGithubApiViewModel();
        initRecyclerView();
    }

    /***********************************************************************************************
     *                                  PRIVATE METHODS
     **********************************************************************************************/
    /**
     * @brief Init Github View Model and observe the responses.
     */
    private void initGithubApiViewModel() {
        mGithubApiViewModel = ViewModelProviders.of(this).get(GithubApiViewModel.class);

        //Make it observable of the Get User History response
        mGithubApiViewModel
                .getRepositoriesResponse()
                .observe(this,
                        apiResponse -> checkGetRepositoriesResponse(apiResponse));
    }

    /**
     * @brief Initialize the recycler view
     */
    private void initRecyclerView() {
        mBinding.recyclerviewRepositories.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRepositoriesAdapter = new RepositoriesRecyclerViewAdapter(getApplicationContext());

        mBinding.recyclerviewRepositories.setVerticalScrollBarEnabled(true);
        mBinding.recyclerviewRepositories.setAdapter(mRepositoriesAdapter);

        // On click event
        mRepositoriesAdapter.getRepositoryClicked().observe(this, new Observer<Repository>() {
            @Override
            public void onChanged(Repository repository) {
                LogUtil.debug(TAG, "Repository Clicked: " + repository.getName());
                launchCommitsActivity(repository);
            }
        });

        //Set divider
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this.getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.recycler_divider));
        mBinding.recyclerviewRepositories.addItemDecoration(itemDecorator);

        // Initialize the pull to refresh
        mBinding.swiperefreshlayoutRepositories.setOnRefreshListener(this.pullToRefreshListener);
        mBinding.swiperefreshlayoutRepositories.setColorSchemeResources(R.color.github_red);
        mBinding.swiperefreshlayoutRepositories.setRefreshing(true);

        //mRepositoriesAdapter.setRepositories(dummyRepoList);
        mGithubApiViewModel.getRepositories();
    }

    /**
     * @brief Check the GetRepositories response. If successful, it displays the list of
     * repositories in the recycler view.
     */
    private void checkGetRepositoriesResponse(ApiResponse apiResponse) {
        switch (apiResponse.getStatus()) {
            case SUCCESS:
                List<Repository> repositories = (List<Repository>) apiResponse.getData();
                mRepositoriesAdapter.setRepositories(repositories);
                mRepositoriesAdapter.notifyDataSetChanged();
                mBinding.swiperefreshlayoutRepositories.setRefreshing(false);
                break;

            case LOADING:
                LogUtil.debug(TAG, "LOADING");
                break;

            case ERROR:
                LogUtil.debug(TAG, "ERROR: " + apiResponse.getError().getMessage());
                mBinding.swiperefreshlayoutRepositories.setRefreshing(false);
                break;
        }
    }

    /**
     * @brief Listener triggered when there is a pull to refresh.
     */
    private SwipeRefreshLayout.OnRefreshListener pullToRefreshListener
            = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            LogUtil.debug(TAG, "onRefresh");
            mGithubApiViewModel.getRepositories();
        }
    };

    /**
     * @brief Launch User Experience Activity passing experience object as extra.
     */
    private void launchCommitsActivity(Repository repository){
        Intent intent = new Intent(this, CommitsActivity.class);
        intent.putExtra("repository_id", repository.getName());
        startActivity(intent);
    }
}
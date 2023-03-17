package com.example.githubapi.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;

import com.example.githubapi.R;
import com.example.githubapi.databinding.ActivityCommitsBinding;
import com.example.githubapi.models.repository.Repository;
import com.example.githubapi.models.commit.CommitBundle;
import com.example.githubapi.ui.adapters.CommitsRecyclerViewAdapter;
import com.example.githubapi.util.LogUtil;
import com.example.githubapi.viewmodels.GithubApiViewModel;

import java.util.Collections;
import java.util.List;

public class CommitsActivity extends AppCompatActivity {

    /***********************************************************************************************
     *                                     CONSTANTS
     **********************************************************************************************/
    /** Class name to be displayed in LogUtils */
    private static final String TAG = CommitsActivity.class.getName();

    /***********************************************************************************************
     *                                     ATTRIBUTES
     **********************************************************************************************/
    /** View binding object to retrieve the objects from the class */
    private ActivityCommitsBinding mBinding;
    /**Commits Adapter*/
    private CommitsRecyclerViewAdapter mCommitsAdapter;
    /** ViewModel to retrieve data from Github API.*/
    private GithubApiViewModel mGithubApiViewModel;
    /**Repository name used to retrieve the commits*/
    private Repository mRepository;
    /**Index to track the page number to be fetched.*/
    private int mPageNumber = 1;
    /**Boolean to track if the end of the list has been reached.*/
    private Boolean mCommitsEndReached = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();

        mBinding = ActivityCommitsBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        mRepository = (Repository) getIntent().getSerializableExtra(Repository.REPOSITORY_INTENT_TAG);

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
                .getCommitsResponse()
                .observe(this,
                        apiResponse -> checkGetCommitsResponse(apiResponse));
    }

    /**
     * @brief Initialize the recycler view
     */
    private void initRecyclerView() {
        mBinding.recyclerviewCommits.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mCommitsAdapter = new CommitsRecyclerViewAdapter(getApplicationContext());

        mBinding.recyclerviewCommits.setVerticalScrollBarEnabled(true);
        mBinding.recyclerviewCommits.setAdapter(mCommitsAdapter);
        mBinding.recyclerviewCommits.addOnScrollListener(scrollListener);

        //Set divider
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this.getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.recycler_divider));
        mBinding.recyclerviewCommits.addItemDecoration(itemDecorator);

        // Initialize the pull to refresh
        mBinding.swiperefreshlayoutCommits.setOnRefreshListener(this.pullToRefreshListener);
        mBinding.swiperefreshlayoutCommits.setColorSchemeResources(R.color.github_red);
        mBinding.swiperefreshlayoutCommits.setRefreshing(true);

        mGithubApiViewModel.getCommits(
                mPageNumber,
                mRepository.getName(),
                mRepository.getOwner().getUsername()
        );
    }

    /**
     * @brief Check the getCommits response. If successful, it displays the list of
     * commits in the recycler view.
     */
    private void checkGetCommitsResponse(com.wearelupa.network.ApiResponse apiResponse) {
        switch (apiResponse.getStatus()) {
            case SUCCESS:
                List<CommitBundle> commits = (List<CommitBundle>) apiResponse.getData();
                this.populateCommitsAdapter(commits);
                break;

            case LOADING:
                LogUtil.debug(TAG, "LOADING");
                break;

            case ERROR:
                LogUtil.debug(TAG, "ERROR: " + apiResponse.getError().getMessage());
                mBinding.swiperefreshlayoutCommits.setRefreshing(false);
                break;
        }
    }

    /**
     * @brief Populate commits Adapter.
     */
    private void populateCommitsAdapter(List<CommitBundle> commits) {
        LogUtil.debug(TAG, "Commits Page " + mPageNumber);
        if (mPageNumber == 1){
            mCommitsAdapter.setCommits(commits);
            mCommitsAdapter.notifyDataSetChanged();
            mBinding.swiperefreshlayoutCommits.setRefreshing(false);
            mPageNumber++;
        }else{
            if (commits.size() > 0){
                mCommitsAdapter.appendCommitsList(commits);
                mCommitsAdapter.notifyDataSetChanged();
                mPageNumber++;
            }else{
                mCommitsEndReached = true;
            }
            mBinding.swiperefreshlayoutCommits.setRefreshing(false);
        }
    }

    /**
     * @brief Listener triggered when there is a pull to refresh.
     */
    private SwipeRefreshLayout.OnRefreshListener pullToRefreshListener
            = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mPageNumber = 1;
            mCommitsEndReached = false;
            LogUtil.debug(TAG, "onRefresh");
            mGithubApiViewModel.getCommits(
                    mPageNumber,
                    mRepository.getName(),
                    mRepository.getOwner().getUsername()
                    );
        }
    };

    /**
     * @brief Listener triggered when the scroll state changes. Used to detect when the bottom
     * is reached.
     */
    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                LogUtil.debug(TAG, "Bottom reached. Page Number: " + mPageNumber);
                if (mPageNumber > 1 && !mCommitsEndReached){
                    mGithubApiViewModel.getCommits(
                            mPageNumber,
                            mRepository.getName(),
                            mRepository.getOwner().getUsername()
                    );
                    mBinding.swiperefreshlayoutCommits.setRefreshing(true);
                }
            }
        }
    };
}
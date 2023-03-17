package com.example.githubapi.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;

import com.example.githubapi.R;
import com.example.githubapi.databinding.ActivityCommitsBinding;
import com.example.githubapi.models.commit.Commit;
import com.example.githubapi.models.commit.CommitBundle;
import com.example.githubapi.models.commit.CommitDate;
import com.example.githubapi.models.commit.Committer;
import com.example.githubapi.ui.adapters.CommitsRecyclerViewAdapter;
import com.example.githubapi.util.LogUtil;
import com.example.githubapi.viewmodels.GithubApiViewModel;

import java.util.ArrayList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();

        mBinding = ActivityCommitsBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        //initGithubApiViewModel();
        initRecyclerView();
    }

    /***********************************************************************************************
     *                                  PRIVATE METHODS
     **********************************************************************************************/
    /**
     * @brief Initialize the recycler view
     */
    private void initRecyclerView() {
        mBinding.recyclerviewCommits.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mCommitsAdapter = new CommitsRecyclerViewAdapter(getApplicationContext());

        mBinding.recyclerviewCommits.setVerticalScrollBarEnabled(true);
        mBinding.recyclerviewCommits.setAdapter(mCommitsAdapter);

        //Set divider
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this.getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.recycler_divider));
        mBinding.recyclerviewCommits.addItemDecoration(itemDecorator);

        // Initialize the pull to refresh
        mBinding.swiperefreshlayoutCommits.setOnRefreshListener(this.pullToRefreshListener);
        mBinding.swiperefreshlayoutCommits.setColorSchemeResources(R.color.github_red);
        mBinding.swiperefreshlayoutCommits.setRefreshing(true);

        List<CommitBundle> commitBundles = new ArrayList<>();
        Committer committer = new Committer("sportac", "https://avatars.githubusercontent.com/u/63201105?v=4");
        CommitDate commitDate = new CommitDate("2023-03-16T17:43:42Z");
        Commit commit = new Commit(commitDate, "Add divider between recycler items");

        commitBundles.add(new CommitBundle(commit, committer));
        commitBundles.add(new CommitBundle(commit, committer));
        commitBundles.add(new CommitBundle(commit, committer));
        commitBundles.add(new CommitBundle(commit, committer));
        commitBundles.add(new CommitBundle(commit, committer));

        mCommitsAdapter.setCommits(commitBundles);

        mBinding.swiperefreshlayoutCommits.setRefreshing(false);

    }

    /**
     * @brief Listener triggered when there is a pull to refresh.
     */
    private SwipeRefreshLayout.OnRefreshListener pullToRefreshListener
            = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            LogUtil.debug(TAG, "onRefresh");
            //mGithubApiViewModel.getRepositories();
        }
    };
}
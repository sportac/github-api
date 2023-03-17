package com.example.githubapi.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.githubapi.R;
import com.example.githubapi.models.commit.CommitBundle;
import com.example.githubapi.util.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CommitsRecyclerViewAdapter
        extends RecyclerView.Adapter<CommitsRecyclerViewAdapter.ViewHolder>{
    /***********************************************************************************************
     *                                     ATTRIBUTES
     **********************************************************************************************/

    /** List to store the commits list*/
    private List<CommitBundle> mCommits;
    /** To have a reference of the view context*/
    private final Context mContext;


    /***********************************************************************************************
     *                                     CONSTRUCTOR
     **********************************************************************************************/
    /**
     * @brief Class constructor.
     */
    public CommitsRecyclerViewAdapter(Context context) {
        mCommits = new ArrayList<>(0);
        mContext = context;
        Picasso.get().setIndicatorsEnabled(true);
    }

    /***********************************************************************************************
     *                                     PUBLIC METHODS
     **********************************************************************************************/
    public void setCommits(List<CommitBundle> mCommits) {
        this.mCommits = mCommits;
    }

    /***********************************************************************************************
     *                                     OVERRIDE METHODS
     **********************************************************************************************/

    @NonNull
    @Override
    public CommitsRecyclerViewAdapter.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.github_commit_card,
                parent,
                false
        );

        return new CommitsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommitsRecyclerViewAdapter.ViewHolder holder, int position) {
        // Get the user history
        holder.mCommitBundle = mCommits.get(position);

        // Commit Message
        holder.mCommitMessage.setText(holder.mCommitBundle.getCommit().getMessage());

        // Avatar image
        String avatarUrl = holder.mCommitBundle.getCommitter().getAvatar();
        Glide
                .with(mContext)
                .load(avatarUrl)
                .placeholder(R.drawable.avatar_placeholder)
                .centerCrop()
                .into(holder.mImage);

        // Username
        holder.mCommitUsername.setText(holder.mCommitBundle.getCommitter().getUsername());

        // Commit Date
        holder.mCommitDate.setText(Utils.getCommitFormattedDateString(
                holder.mCommitBundle.getCommit().getCommitDate().getDate()));
    }

    @Override
    public int getItemCount() {
        return mCommits.size();
    }

    /***********************************************************************************************
     *                                     PUBLIC CLASSES
     **********************************************************************************************/
    public class ViewHolder extends RecyclerView.ViewHolder{

        public CommitBundle mCommitBundle;
        public final AppCompatTextView mCommitMessage, mCommitDate, mCommitUsername;
        public ImageView mImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mCommitMessage = itemView.findViewById(R.id.text_commit_message);
            mCommitDate = itemView.findViewById(R.id.text_commit_date);
            mCommitUsername = itemView.findViewById(R.id.text_commit_username);
            mImage = itemView.findViewById(R.id.imageview_avatar);
        }
    }


}

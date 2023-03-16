package com.example.githubapi.ui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubapi.R;
import com.example.githubapi.models.Repository;
import com.example.githubapi.util.Utils;

import java.util.ArrayList;
import java.util.List;


public class RepositoriesRecyclerViewAdapter
        extends RecyclerView.Adapter<RepositoriesRecyclerViewAdapter.ViewHolder> {

    /***********************************************************************************************
     *                                     ATTRIBUTES
     **********************************************************************************************/

    /** List to store the user experiences*/
    private List<Repository> mRepositories;
    /** To have a reference of the view context*/
    private final Context mContext;

    /***********************************************************************************************
     *                                     CONSTRUCTOR
     **********************************************************************************************/
    /**
     * @brief Class constructor.
     */
    public RepositoriesRecyclerViewAdapter(Context context) {
        mRepositories = new ArrayList<>(0);
        mContext = context;
    }

    /***********************************************************************************************
     *                                     PUBLIC METHODS
     **********************************************************************************************/
    public void setRepositories(List<Repository> mRepositories) {
        this.mRepositories = mRepositories;
    }

    /***********************************************************************************************
     *                                     OVERRIDE METHODS
     **********************************************************************************************/

    @NonNull
    @Override
    public RepositoriesRecyclerViewAdapter.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.github_repository_card,
                parent,
                false
        );

        return new RepositoriesRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the user history
        holder.mRepository = mRepositories.get(position);

        // Set experience title
        holder.mRepositoryName.setText(holder.mRepository.getName());
        holder.mRepositoryDescription.setText(holder.mRepository.getDescription());

        // Visibility
        String visibility = holder.mRepository.getVisibility();
        visibility = visibility.substring(0, 1).toUpperCase() + visibility.substring(1);
        holder.mVisibility.setText(visibility);

        // Language
        String language = holder.mRepository.getLanguage();
        holder.mLanguage.setText(language);
        holder.mLanguageColor.setBackgroundDrawable(
                Utils.getLanguageColorTagDrawable(mContext, language));

        // Last updated
        holder.mLastUpdated.setText(Utils.getLastUpdatedString(holder.mRepository.getLastUpdated()));
    }

    @Override
    public int getItemCount() {
        return mRepositories.size();
    }

    /***********************************************************************************************
     *                                     PUBLIC CLASSES
     **********************************************************************************************/
    public class ViewHolder extends RecyclerView.ViewHolder{

        public Repository mRepository;
        public final AppCompatTextView mRepositoryName,
                mRepositoryDescription,
                mVisibility,
                mLanguage,
                mLanguageColor,
                mLastUpdated;

        public ViewHolder(View itemView) {
            super(itemView);
            mRepositoryName = itemView.findViewById(R.id.text_repository_name);
            mRepositoryDescription = itemView.findViewById(R.id.text_repository_description);
            mVisibility = itemView.findViewById(R.id.label_privacy);
            mLanguage = itemView.findViewById(R.id.label_language);
            mLanguageColor = itemView.findViewById(R.id.label_language_color);
            mLastUpdated = itemView.findViewById(R.id.label_last_updated);
        }
    }

}

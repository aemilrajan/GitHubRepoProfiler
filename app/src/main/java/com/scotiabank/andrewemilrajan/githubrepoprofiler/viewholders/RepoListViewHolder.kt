package com.scotiabank.andrewemilrajan.githubrepoprofiler.viewholders

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.scotiabank.andrewemilrajan.githubrepoprofiler.R
import com.scotiabank.andrewemilrajan.githubrepoprofiler.models.Repo

/**
 * Created by andrewemilrajan on 2019-09-22.
 */

class RepoListViewHolder(itemView: View, private val listener: Listener?) : RecyclerView.ViewHolder(itemView) {

    private val title: TextView = itemView.findViewById(R.id.repo_title)
    private val description: TextView = itemView.findViewById(R.id.repo_description)
    private val cardView: CardView = itemView.findViewById(R.id.repo_card)

    fun bindData(repoViewModel: Repo) {
        title.text = repoViewModel.name
        description.text = repoViewModel.description
        cardView.setOnClickListener {
            listener?.onRepoItemClicked(repoViewModel)
        }
    }

    interface Listener {
        /**
         * Handle the action of clicking a repo item.
         * @param repoViewModel to be utilized.
         */
        fun onRepoItemClicked(repoViewModel: Repo)
    }
}

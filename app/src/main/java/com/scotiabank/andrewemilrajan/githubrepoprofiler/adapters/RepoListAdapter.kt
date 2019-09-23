package com.scotiabank.andrewemilrajan.githubrepoprofiler.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.scotiabank.andrewemilrajan.githubrepoprofiler.R
import com.scotiabank.andrewemilrajan.githubrepoprofiler.models.Repo
import com.scotiabank.andrewemilrajan.githubrepoprofiler.viewholders.RepoListViewHolder
import java.util.*

/**
 * Created by andrewemilrajan on 2019-09-22.
 */

class RepoListAdapter(private val repoViewModels: ArrayList<Repo>, private val repoItemClickListener: RepoListViewHolder.Listener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return RepoListViewHolder(view, repoItemClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RepoListViewHolder).bindData(repoViewModels[position])
    }

    override fun getItemCount(): Int {
        return repoViewModels.size
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.repo_item_cardview
    }
}

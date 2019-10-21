package com.repo.list.ui.search_repo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.repo.list.R
import com.repo.list.inflater
import com.repo.list.listener.RecyclerItemClickListener
import com.repo.list.model.response.GithubUserRepos
import com.repo.list.visible
import kotlinx.android.synthetic.main.cell_search_repo.view.*

class SearchRepoAdapter(
    private val context: Context,
    private val clickListener: RecyclerItemClickListener<GithubUserRepos>,
    private val list: MutableList<GithubUserRepos> = mutableListOf()
) : RecyclerView.Adapter<SearchRepoAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.itemView.apply {
            ivStar.visible(item.isFavourite)
            tvRepoName.text = item.name
            setOnClickListener { clickListener.onItemClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(context.inflater(R.layout.cell_search_repo, parent))

    override fun getItemCount(): Int = list.size

    fun notifySetData(list: MutableList<GithubUserRepos>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun updateData(item: GithubUserRepos) {
        list.forEachIndexed { index, value ->
            if (value.id == item.id) {
                if (list[index] != item) {
                    list[index] = item
                    notifyItemChanged(index)
                }
                return@forEachIndexed
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
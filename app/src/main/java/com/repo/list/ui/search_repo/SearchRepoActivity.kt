package com.repo.list.ui.search_repo


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.repo.list.R
import com.repo.list.listener.RecyclerItemClickListener
import com.repo.list.model.response.GithubUserRepos
import com.repo.list.showToast
import com.repo.list.ui.base.BaseActivity
import com.repo.list.ui.repo_detail.RepoDetailActivity
import com.repo.list.util.Constants.IntentResult
import com.repo.list.util.Constants.Intent.GITHUB_USER_REPO
import kotlinx.android.synthetic.main.activity_search_repo.*

class SearchRepoActivity :
    BaseActivity<SearchRepoContract.View, SearchRepoContract.Presenter>(), SearchRepoContract.View,
    RecyclerItemClickListener<GithubUserRepos> {

    override val layoutId = R.layout.activity_search_repo
    override val presenter: SearchRepoContract.Presenter = SearchRepoPresenter()
    override val view: SearchRepoContract.View = this

    private lateinit var adapter: SearchRepoAdapter

    override fun onActivityReady(savedInstanceState: Bundle?) {
        super.onActivityReady(savedInstanceState)
        adapter = SearchRepoAdapter(this, this)
        rvUserRepo.apply {
            adapter = this@SearchRepoActivity.adapter
            layoutManager = LinearLayoutManager(this@SearchRepoActivity)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    override fun onUserRepoList(list: MutableList<GithubUserRepos>) {
        adapter.notifySetData(list)
    }

    override fun onEmptyRequest() {
        showToast(getString(R.string.fill_in_the_blanks))
    }

    override fun onNoResultFound() {
        showToast(getString(R.string.no_result_found))
    }

    fun searchUserRepoClick(view: View) {
        presenter.searchUserRepo(etUsername.text.toString().trim())
    }

    override fun onItemClick(item: GithubUserRepos) {
        RepoDetailActivity.startActivity(this, item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IntentResult.STAR_CONTROL_RESULT) {
            data?.extras?.getParcelable<GithubUserRepos>(GITHUB_USER_REPO)?.let {
                adapter.updateData(it)
            }
        }
    }
}

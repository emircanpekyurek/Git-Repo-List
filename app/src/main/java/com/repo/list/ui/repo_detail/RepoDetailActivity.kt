package com.repo.list.ui.repo_detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.repo.list.R
import com.repo.list.loadImageFromUrl
import com.repo.list.model.response.GithubUserRepos
import com.repo.list.ui.base.BaseActivity
import com.repo.list.util.Constants.Intent.GITHUB_USER_REPO
import com.repo.list.util.Constants.IntentResult
import kotlinx.android.synthetic.main.activity_repo_detail.*


class RepoDetailActivity :
    BaseActivity<RepoDetailContract.View, RepoDetailContract.Presenter>(), RepoDetailContract.View {

    override val layoutId = R.layout.activity_repo_detail
    override val presenter: RepoDetailContract.Presenter = RepoDetailPresenter()
    override val view: RepoDetailContract.View = this

    private var githubUserRepos: GithubUserRepos? = null

    companion object {
        fun startActivity(activity: Activity, githubUserRepos: GithubUserRepos) {
            val intent = Intent(activity, RepoDetailActivity::class.java).apply {
                putExtra(GITHUB_USER_REPO, githubUserRepos)
            }
            activity.startActivityForResult(intent, IntentResult.STAR_CONTROL_RESULT)
        }
    }

    override fun onActivityReady(savedInstanceState: Bundle?) {
        super.onActivityReady(savedInstanceState)
        githubUserRepos = intent.extras?.getParcelable(GITHUB_USER_REPO)
        initUi()
        initActionBar()
    }

    private fun initUi() {
        githubUserRepos?.run {
            owner?.run {
                avatarUrl?.let { ivUserImage.loadImageFromUrl(it) }
                tvUserName.text = login
            }
            tvRepoName.text = name
            tvOpenIssuesCount.text = openIssuesCount.toString()
            setStartCountText()
        }
    }

    private fun initActionBar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = githubUserRepos?.name
        }
    }

    private fun setStartCountText() {
        githubUserRepos?.run {
            tvStarCount.text = if (isFavourite) {
                (stargazersCount?.plus(1)).toString()
            } else {
                stargazersCount.toString()
            }
        }
    }

    override fun onToggleFavoriteStatus(newRepo: GithubUserRepos) {
        this.githubUserRepos = newRepo
        invalidateOptionsMenu()
        setStartCountText()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_repo_detail, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val itemStar = menu?.findItem(R.id.item_star)
        githubUserRepos?.isFavourite?.let {
            itemStar?.setIcon(
                if (it) {
                    R.drawable.ic_star_enabled
                } else {
                    R.drawable.ic_star_disabled
                }
            )
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_star) {
            githubUserRepos?.let { presenter.toggleFavoriteStatus(it) }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val resultIntent = Intent().apply {
            putExtra(GITHUB_USER_REPO, githubUserRepos)
        }
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

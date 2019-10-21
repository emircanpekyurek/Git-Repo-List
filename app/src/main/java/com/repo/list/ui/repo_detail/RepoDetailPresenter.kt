package com.repo.list.ui.repo_detail

import com.repo.list.database.DatabaseHelper
import com.repo.list.model.response.GithubUserRepos
import com.repo.list.ui.base.BasePresenter

class RepoDetailPresenter : BasePresenter<RepoDetailContract.View>(), RepoDetailContract.Presenter {
    override fun toggleFavoriteStatus(githubUserRepos: GithubUserRepos) {
        githubUserRepos.id?.also {
            if (DatabaseHelper.toggleFavorite(it, githubUserRepos.isFavourite)) {
                githubUserRepos.isFavourite = !githubUserRepos.isFavourite
                view?.onToggleFavoriteStatus(githubUserRepos)
            } else {
                view?.onDatabaseError()
            }
        }
    }
}
package com.repo.list.ui.repo_detail

import com.repo.list.model.response.GithubUserRepos
import com.repo.list.ui.base.BaseContract

class RepoDetailContract {
    interface View : BaseContract.View {
        fun onToggleFavoriteStatus(newRepo: GithubUserRepos)

    }

    interface Presenter : BaseContract.Presenter<View> {
        fun toggleFavoriteStatus(githubUserRepos: GithubUserRepos)
    }
}
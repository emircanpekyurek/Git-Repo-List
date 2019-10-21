package com.repo.list.ui.search_repo

import com.repo.list.model.response.GithubUserRepos
import com.repo.list.ui.base.BaseContract

class SearchRepoContract {
    interface View : BaseContract.View {
        fun onUserRepoList(list: MutableList<GithubUserRepos>)
        fun onEmptyRequest()
        fun onNoResultFound()
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun searchUserRepo(username: String)
    }
}
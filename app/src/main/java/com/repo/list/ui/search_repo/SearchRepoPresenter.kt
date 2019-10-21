package com.repo.list.ui.search_repo

import android.text.TextUtils
import com.repo.list.database.DatabaseHelper
import com.repo.list.model.response.GithubUserRepos
import com.repo.list.network.ApiResponse
import com.repo.list.network.NetworkManager
import com.repo.list.network.Request
import com.repo.list.ui.base.BasePresenter

class SearchRepoPresenter : BasePresenter<SearchRepoContract.View>(), SearchRepoContract.Presenter {

    override fun searchUserRepo(username: String) {
        if (TextUtils.isEmpty(username)) {
            view?.onEmptyRequest()
        } else {
            view?.showLoading()
            addDisposable(
                Request.disposable(NetworkManager.api.getData(username),
                    object : ApiResponse<MutableList<GithubUserRepos>> {
                        override fun onSuccessResponse(response: MutableList<GithubUserRepos>) {
                            if (response.isEmpty()) {
                                view?.onNoResultFound()
                            } else {
                                for (repo in response) {
                                    repo.id?.let {
                                        repo.isFavourite = DatabaseHelper.isFavorite(it)
                                    }
                                }
                                view?.onUserRepoList(response)
                            }
                            view?.hideLoading()
                        }

                        override fun onFailResponse(errorMessage: String) {
                            view?.hideLoading()
                            view?.onError(errorMessage)
                        }

                    })
            )

        }

    }

}
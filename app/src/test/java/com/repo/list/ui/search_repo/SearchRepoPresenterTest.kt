package com.repo.list.ui.search_repo

import com.repo.list.network.NetworkManager
import org.junit.Test

import org.junit.Assert.*

class SearchRepoPresenterTest {

    @Test
    fun searchUserRepo() {

        val api = NetworkManager.api
        api.getData("test").subscribe {
            assert(it.isSuccessful)
            assertEquals(it.code(), 200)
            assert(it.body() != null)
        }

    }
}
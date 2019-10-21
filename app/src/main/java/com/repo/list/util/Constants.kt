package com.repo.list.util

object Constants {

    object Intent {
        const val GITHUB_USER_REPO = "github_user_repo"
    }

    object IntentResult {
        const val STAR_CONTROL_RESULT = 99
    }

    object Sql {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "favorite_db"

        object FavoriteTable {
            const val TABLE_NAME = "favorite_table"
            const val REPO_ID = "repo_id"
        }

    }

}
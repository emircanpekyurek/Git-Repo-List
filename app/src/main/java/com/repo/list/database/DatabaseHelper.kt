package com.repo.list.database

import android.content.ContentValues
import com.repo.list.GitApplication
import com.repo.list.util.Constants.Sql.FavoriteTable

object DatabaseHelper : DatabaseCreator(GitApplication.instance) {

    private fun addFavorite(id: Int): Boolean {
        return writableDatabase.insert(
            FavoriteTable.TABLE_NAME,
            null,
            ContentValues().apply {
                put(FavoriteTable.REPO_ID, id)
            }) > 0
    }

    private fun removeFavorite(id: Int): Boolean {
        return writableDatabase.delete(
            FavoriteTable.TABLE_NAME,
            "${FavoriteTable.REPO_ID}=?",
            arrayOf(id.toString())
        ) > 0
    }

    fun toggleFavorite(id: Int, isFavorite: Boolean): Boolean {
        return if (isFavorite) {
            removeFavorite(id)
        } else {
            addFavorite(id)
        }
    }

    fun isFavorite(id: Int): Boolean {
        val usersSelectQuery = String.format(
            "SELECT * FROM %s WHERE %s = ?",
            FavoriteTable.TABLE_NAME,
            FavoriteTable.REPO_ID
        )
        val cursor = writableDatabase.rawQuery(usersSelectQuery, arrayOf(id.toString()))
        val isFavorite = cursor.moveToFirst()
        cursor.close()
        return isFavorite
    }
}
package com.repo.list.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.repo.list.util.Constants.Sql.DATABASE_NAME
import com.repo.list.util.Constants.Sql.DATABASE_VERSION
import com.repo.list.util.Constants.Sql.FavoriteTable

open class DatabaseCreator(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase?) {
        createFavoriteTable(db)
    }

    private fun createFavoriteTable(db: SQLiteDatabase?) {
        db?.execSQL("Create Table if not exists ${FavoriteTable.TABLE_NAME}(${FavoriteTable.REPO_ID} INTEGER);");
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            dropTable(db, FavoriteTable.TABLE_NAME)
        }
        onCreate(db)
    }

    private fun dropTable(db: SQLiteDatabase?, tableName: String) {
        db?.execSQL("DROP TABLE IF EXISTS $tableName")
    }
}
package com.gb.advanced2.externals.repo.history

import android.content.Context
import androidx.room.Room
import com.gb.advanced2.app.Contract
import com.gb.advanced2.entities.SearchHistoryRecord
import com.gb.advanced2.entities.SearchHistoryRecords
import com.gb.advanced2.externals.repo.history.db.AppDatabase
import timber.log.Timber

class LocalRepository(context: Context) : Contract.HistoryModel {
    override suspend fun getHistory(): SearchHistoryRecords {
        val list = db.historyDao().getHistory(historyRecentLimit).map {
            SearchHistoryRecord(
                it.searchQuery,
                it.resultsCount,
            )
        }
        Timber.d("=== from room: $list")
        return SearchHistoryRecords(list)
    }

    private val db = Room.databaseBuilder(context, AppDatabase::class.java, "db").build()
    private val historyRecentLimit = 10
}
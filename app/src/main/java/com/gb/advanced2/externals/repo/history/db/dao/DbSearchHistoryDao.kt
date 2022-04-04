package com.gb.advanced2.externals.repo.history.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gb.advanced2.externals.repo.history.db.entity.DbSearchHistoryRecord

@Dao
interface DbSearchHistoryDao {
    @Query(value = "SELECT * FROM search_history ORDER BY timestamp ASC LIMIT :limit")
    fun getHistory(limit: Int): List<DbSearchHistoryRecord>

    @Insert
    fun addHistory(record: DbSearchHistoryRecord)

    @Query(value = "DELETE FROM search_history WHERE search_query IN (:ids)")
    fun removeHistory(vararg ids: String)
}
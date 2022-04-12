package com.gb.advanced2.externals.repo.history.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meaning")
data class DbMeaningRecord (
    @PrimaryKey @ColumnInfo(name = "term_name") val termName : String
    @ColumnInfo(name = "term_name") val termName : String
)
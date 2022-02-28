package com.amar.peoplelistapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "likes")
data class LikeData (

    @PrimaryKey(autoGenerate = false)
    val id: Int,

    @ColumnInfo(name = "is_liked",  defaultValue = "")
    var isLiked: Int? = 0

)

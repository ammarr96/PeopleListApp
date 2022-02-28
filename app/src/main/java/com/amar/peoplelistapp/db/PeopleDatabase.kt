package com.amar.peoplelistapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amar.peoplelistapp.model.LikeData
import com.amar.peoplelistapp.model.PersonData

@Database(entities = [LikeData::class], version = 1)
abstract class PeopleDatabase : RoomDatabase() {

    abstract fun likeDao(): LikeDAO

}
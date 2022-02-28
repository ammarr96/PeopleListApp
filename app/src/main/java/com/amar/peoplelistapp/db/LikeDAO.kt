package com.amar.peoplelistapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amar.peoplelistapp.model.LikeData

@Dao
interface LikeDAO {

    @Query("SELECT * from likes")
    fun getAllLikes(): List<LikeData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLike(like: LikeData)

    @Query("DELETE FROM likes")
    fun deleteAll()

    @Query("UPDATE likes SET is_liked=:liked WHERE id = :id")
    fun updateLikeStatus(id: Int, liked: Int)

    @Query("DELETE from likes WHERE id = :id")
    fun deleteLikeById(id: Int)

    @Query("SELECT * from likes WHERE id = :id")
    fun getLikeById(id: Int) : LikeData?

}
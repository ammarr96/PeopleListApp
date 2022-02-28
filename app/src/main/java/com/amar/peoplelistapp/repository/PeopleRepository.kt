package com.amar.peoplelistapp.repository

import com.amar.peoplelistapp.api.ApiService
import com.amar.peoplelistapp.db.LikeDAO
import com.amar.peoplelistapp.model.LikeData
import com.amar.peoplelistapp.model.PersonData
import java.lang.Exception
import javax.inject.Inject

class PeopleRepository @Inject constructor(
    private val likeDAO: LikeDAO,
    private val apiService: ApiService
) {

    suspend fun getPeople(page: Int) : List<PersonData> {
        var list = listOf<PersonData>()
        try {
            list = apiService.getPeople(page).response
            list.forEach {
                val like = likeDAO.getLikeById(it.id)
                it.isLiked = if (like?.isLiked ?: 0 == 1) true else false
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
        return list;
    }

    fun setLiked(id: Int, liked: Boolean) {
        if (liked) {
            likeDAO.insertLike(LikeData(id, 1))
        }
        else {
            likeDAO.deleteLikeById(id)
        }
    }


}
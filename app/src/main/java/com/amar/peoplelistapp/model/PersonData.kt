package com.amar.peoplelistapp.model

data class PersonData (
    val id: Int,
    val firstName: String?,
    val learns: List<String>?,
    val natives: List<String>?,
    val pictureUrl: String?,
    val referenceCnt: Int?,
    val topic: String?,
    var isLiked: Boolean = false
)
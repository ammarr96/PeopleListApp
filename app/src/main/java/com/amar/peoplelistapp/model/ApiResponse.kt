package com.amar.peoplelistapp.model

data class ApiResponse(
    val errorCode: Any,
    val response: List<PersonData>,
    val type: String
)
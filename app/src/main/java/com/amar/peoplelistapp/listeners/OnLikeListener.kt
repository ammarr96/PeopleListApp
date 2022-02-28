package com.amar.peoplelistapp.listeners

import com.amar.peoplelistapp.model.PersonData

interface OnLikeListener {
    fun onLiked(person: PersonData)
}
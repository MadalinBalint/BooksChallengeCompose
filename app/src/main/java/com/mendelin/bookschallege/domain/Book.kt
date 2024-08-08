package com.mendelin.bookschallege.domain

import androidx.annotation.Keep

@Keep
data class Book(
    val title: String,
    val author: String,
    val wiki: String,
    val url: String
)

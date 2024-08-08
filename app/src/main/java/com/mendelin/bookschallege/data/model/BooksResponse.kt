package com.mendelin.bookschallege.data.model

import androidx.annotation.Keep

@Keep
data class BooksResponse(
    val books: List<BookModel>
)

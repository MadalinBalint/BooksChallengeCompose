package com.mendelin.bookschallege.data.repository

import com.mendelin.bookschallege.data.remote.BooksApi
import javax.inject.Inject


class BooksRepository @Inject constructor(val api: BooksApi) {
    suspend fun getBooks() =
        api.getBooks()
}

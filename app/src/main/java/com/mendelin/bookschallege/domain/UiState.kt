package com.mendelin.bookschallege.domain

data class UiState(
    val isLoading: Boolean = false,
    val booksList: List<Book> = listOf(),
    val errorMessage: String = "",
)

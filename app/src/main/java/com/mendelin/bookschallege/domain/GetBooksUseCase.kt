package com.mendelin.bookschallege.domain

import com.mendelin.bookschallege.data.repository.BooksRepository
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(private val repository: BooksRepository) {
    operator fun invoke() = flow {
        try {
            emit(Resource.Loading())
            val apiResponse = repository.getBooks()
            val body = apiResponse.body()

            Timber.d("Response = ${apiResponse.code()}")

            when {
                apiResponse.isSuccessful && body != null ->
                    emit(Resource.Success(body.books))

                body == null -> emit(Resource.Error("Null body response"))

                else ->
                    emit(Resource.Error(apiResponse.message()))
            }
        } catch (ex: Exception) {
            emit(Resource.Error(ex.localizedMessage ?: "Unknown exception"))
        }
    }
}

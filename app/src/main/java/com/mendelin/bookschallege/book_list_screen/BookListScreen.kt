package com.mendelin.bookschallege.book_list_screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.mendelin.bookschallege.domain.Book

@Composable
fun BooksListScreen(
    viewmodel: BooksViewmodel
) {
    val uiState by viewmodel.uiState.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            when {
                uiState.isLoading -> {
                    ProgressBarContent()
                }

                uiState.booksList.isNotEmpty() -> {
                    ListContent(uiState.booksList)
                }

                uiState.errorMessage.isNotEmpty() -> {
                    ErrorContent(uiState.errorMessage)
                }
            }
        }
    }
}

@Composable
fun ProgressBarContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(modifier = Modifier.size(96.dp))
    }
}

@Composable
fun ListContent(books: List<Book>) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        items(books, key = { book -> book.title }) { book ->
            BookContent(book)
        }
    }
}

@Composable
fun BookContent(book: Book) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val context = LocalContext.current
        Column(modifier = Modifier.padding(8.dp)) {
            Text("Title: ${book.title}")
            Text("Author: ${book.author}")
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(book.url)
                    .crossfade(true)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
            ClickableText(text = AnnotatedString(book.wiki)) {
                val uri = Uri.parse(book.wiki)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context.startActivity(intent)
            }
        }
    }
}

@Composable
fun ErrorContent(errorMessage: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = errorMessage, modifier = Modifier.padding(16.dp))
    }
}

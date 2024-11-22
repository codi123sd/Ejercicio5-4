package com.example.ejercicio5.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.ejercicio5.Book.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.ejercicio5.Repositorio.BookRepository

class BookViewModel(private val Repositorio: BookRepository) : ViewModel(){

    // Estado que contiene la lista de libros
    private val list_books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = list_books

    val allBooks: LiveData<List<Book>> = Repositorio.allBooks

    init {
        loadBooks()
    }

    private fun loadBooks() {
        viewModelScope.launch {
            Repositorio.allBooks.asFlow().collect { booksList ->
                list_books.value = booksList
            }
        }
    }

    fun addBook(book: Book) {
        viewModelScope.launch {
            Repositorio.insertBook(book)
            loadBooks()
        }
    }

    fun updateBook(book: Book) {
        viewModelScope.launch {
            Repositorio.updateBook(book)
            loadBooks()
        }
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch {
            Repositorio.deleteBook(book)
            loadBooks()
        }
    }
}
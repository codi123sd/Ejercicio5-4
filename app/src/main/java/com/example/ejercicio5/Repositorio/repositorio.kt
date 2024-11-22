package com.example.ejercicio5.Repositorio

import androidx.lifecycle.LiveData
import com.example.ejercicio5.Book.Book
import com.example.ejercicio5.Book.BookDao

class BookRepository(private val bookDao: BookDao) {

    val allBooks: LiveData<List<Book>> = bookDao.getAllBooks()

    suspend fun insertBook(book: Book) {
        bookDao.insertBook(book)
    }

    suspend fun updateBook(book: Book) {
        bookDao.updateBook(book)
    }

    suspend fun deleteBook(book: Book) {
        bookDao.deleteBook(book)
    }
}
package com.example.ejercicio5.Book

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ejercicio5.Book.Book
import com.example.ejercicio5.Book.BookDao

@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}
package com.example.ejercicio5.Book

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.ejercicio5.Book.Book

@Dao
interface BookDao {
    // Obtener todos los libros de la base de datos
    @Query("SELECT * FROM books")
    fun getAllBooks(): LiveData<List<Book>>

    // Insertar un libro en la base de datos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    // Actualizar un libro existente en la base de datos
    @Update
    suspend fun updateBook(book: Book)

    // Eliminar un libro de la base de datos
    @Delete
    suspend fun deleteBook(book: Book)
}
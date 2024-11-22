package com.example.ejercicio5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.ejercicio5.Book.Book
import com.example.ejercicio5.ViewModel.BookViewModel
import com.example.ejercicio5.ui.theme.Ejercicio5Theme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.ejercicio5.Book.BookDatabase
import com.example.ejercicio5.Repositorio.BookRepository
import com.example.ejercicio5.ViewModel.BookViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializa la base de datos, el DAO y el repositorio
        val db = Room.databaseBuilder(this, BookDatabase::class.java, "book_db").build()
        val dao = db.bookDao()
        val repository = BookRepository(dao)

        // Crea la fábrica para el ViewModel
        val factory = BookViewModelFactory(repository)
        val bookViewModel = ViewModelProvider(this, factory).get(BookViewModel::class.java)

        // Inserta datos iniciales en la base de datos
        /*GlobalScope.launch {
            val initialBooks = listOf(
                Book(title = "1984", author = "George Orwell", genre = "Dystopian", price = 9.99, pages = 328),
                Book(title = "Pride and Prejudice", author = "Jane Austen", genre = "Romance", price = 14.99, pages = 279),
                Book(title = "The Great Gatsby", author = "F. Scott Fitzgerald", genre = "Classic", price = 10.99, pages = 180)
            )
            initialBooks.forEach { book ->
                repository.insertBook(book) // Inserta los libros usando el repositorio
            }
        }*/

        setContent {
            Ejercicio5Theme {

                Navegacion(navController = rememberNavController(),  bookViewModel)
                /*Home(
                    bookViewModel = bookViewModel,
                    onAddBook = {},
                    onEditBook = {}
                )*/
            }
        }
    }
}



@Composable
fun Editar_Book(
    book: Book? = null,
    onSave: (Book) -> Unit,
    onCancel: () -> Unit
) {

    var title by remember { mutableStateOf(book?.title ?: "") }
    var author by remember { mutableStateOf(book?.author ?: "") }
    var genre by remember { mutableStateOf(book?.genre ?: "") }
    var price by remember { mutableStateOf(book?.price?.toString() ?: "") }
    var pages by remember { mutableStateOf(book?.pages?.toString() ?: "") }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                //.align(Alignment.Center)
                .fillMaxWidth()
                .background(Color.Blue)
                .padding(16.dp)
        ) {
            Text(
                text = "Editar Book",
                style = MaterialTheme.typography.h5,
                color = Color.White,
                modifier = Modifier.padding(bottom = 25.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title", fontSize = 16.sp)  },
                isError = title.isEmpty(),
                textStyle = TextStyle(fontSize = 16.sp), // Aumentar tamaño de la fuente
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = author,
                onValueChange = { author = it },
                label = { Text("Author", fontSize = 16.sp) },
                isError = author.isEmpty(),
                textStyle = TextStyle(fontSize = 16.sp), // Aumentar tamaño de la fuente
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = genre,
                onValueChange = { genre = it },
                label = { Text("Genre", fontSize = 16.sp) },
                isError = genre.isEmpty(),
                textStyle = TextStyle(fontSize = 16.sp), // Aumentar tamaño de la fuente
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Price", fontSize = 16.sp) },
                isError = price.isEmpty() || price.toDoubleOrNull() == null,
                textStyle = TextStyle(fontSize = 16.sp), // Aumentar tamaño de la fuente
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = pages,
                onValueChange = { pages = it },
                label = { Text("Pages", fontSize = 16.sp) },
                isError = pages.isEmpty() || pages.toIntOrNull() == null,
                textStyle = TextStyle(fontSize = 16.sp), // Aumentar tamaño de la fuente
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botones alineados en los extremos
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { onCancel() },
                    modifier = Modifier.fillMaxWidth(0.45f)
                ) {
                    Text("Cancel")
                }
                Button(
                    onClick = {
                        val newBook = Book(
                            id = book?.id ?: (0..Int.MAX_VALUE).random(),
                            title = title,
                            author = author,
                            genre = genre,
                            price = price.toDouble(),
                            pages = pages.toInt()
                        )
                        onSave(newBook)
                    },
                    modifier = Modifier.fillMaxWidth(0.45f)
                ) {
                    Text("Save")
                }
            }
        }
    }
}

@Composable
fun Home(bookViewModel: BookViewModel,
         onAddBook: () -> Unit,
         onEditBook: (Book) -> Unit){

    val bookList = bookViewModel.allBooks.observeAsState()
    // val bookList = bookViewModel.books.collectAsState().value

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddBook) {
                Icon(Icons.Filled.Add, contentDescription = "addNewBook")
            }
        },

        floatingActionButtonPosition = FabPosition.End,

        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)

                ) {

                items(bookList .value ?: emptyList()) { book ->
                    Item_Book(
                        book = book,
                        onEdit = { onEditBook(book) },
                        onDelete = { bookViewModel.deleteBook(book) }
                    )
                }






            }


        }

    )

}










@Composable
fun Item_Book(book: Book, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.primary // Fondo azul
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Información del libro
            listOf(
                "ID:" to book.id.toString(),
                "Title:" to book.title,
                "Author:" to book.author,
                "Genre:" to book.genre,
                "Price:" to "$${book.price}",
                "Pages:" to book.pages.toString()
            ).forEach { (label, value) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onPrimary
                    )
                    Text(
                        text = value,
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onDelete) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
                IconButton(onClick = onEdit) {
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = "Edit",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }

            }
        }
    }
}


@Composable
fun AgregarBook(
    onSave: (Book) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var pages by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // Función de validación
    fun validateAndSave() {
        if (title.isEmpty() || author.isEmpty() || genre.isEmpty() || price.isEmpty() || pages.isEmpty()) {
            errorMessage = "Todos los datos son requeridos"
        } else {
            errorMessage = ""
            val newBook = Book(
                id = 0,
                title = title,
                author = author,
                genre = genre,
                price = price.toDoubleOrNull() ?: 0.0,
                pages = pages.toIntOrNull() ?: 0
            )
            onSave(newBook)
        }
    }

    Card(
        modifier = Modifier
            .padding(22.dp)
            .fillMaxWidth()
            .border(2.dp, Color(0xFF1E88E5), shape = RoundedCornerShape(12.dp)),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFFBBDEFB)) // Fondo azul claro
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Agregar Nuevo Libro",
                style = MaterialTheme.typography.h5.copy(color = Color(0xFF1565C0)), // Azul oscuro
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Función para crear campos de texto con bordes personalizados
            @Composable
            fun BlueTextField(
                value: String,
                label: String,
                onValueChange: (String) -> Unit,
                isError: Boolean
            ) {
                Column(modifier = Modifier.padding(bottom = 8.dp)) {
                    Text(
                        text = label, fontSize = 16.sp,
                        color = Color(0xFF1565C0),
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    TextField(
                        value = value,
                        onValueChange = onValueChange,
                        isError = isError,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .border(
                                width = 2.dp,
                                color = if (isError) Color.Red else Color(0xFF42A5F5),
                                shape = RoundedCornerShape(8.dp)
                            )
                    )
                }
            }

            // Crear cada campo de texto usando la función
            BlueTextField(title, "Título", { title = it }, title.isEmpty())
            BlueTextField(author, "Autor", { author = it }, author.isEmpty())
            BlueTextField(genre, "Género", { genre = it }, genre.isEmpty())
            BlueTextField(price, "Precio", { price = it }, price.isEmpty() || price.toDoubleOrNull() == null)
            BlueTextField(pages, "Páginas", { pages = it }, pages.isEmpty() || pages.toIntOrNull() == null)

            // Mensaje de error
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Botones personalizados
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { onCancel() },
                    modifier = Modifier
                        .fillMaxWidth(0.45f)
                        .background(Color(0xFF1E88E5), shape = RoundedCornerShape(8.dp))
                ) {
                    Text("Canceñ", color = Color.White)
                }
                Button(
                    onClick = { validateAndSave() },
                    modifier = Modifier
                        .fillMaxWidth(0.45f)
                        .background(Color(0xFF42A5F5), shape = RoundedCornerShape(8.dp))
                ) {
                    Text("Save", color = Color.White)
                }
            }
        }
    }
}






@Composable
fun Navegacion(navController: NavHostController, bookViewModel: BookViewModel) {

    val books = bookViewModel.books.collectAsState().value

    NavHost(navController, startDestination = "home") {
        composable("home") {
            Home(
                bookViewModel = bookViewModel,
                onAddBook = { navController.navigate("addNewBook") },
                onEditBook = { book -> navController.navigate("addEdit/${book.id}") }
            )
        }

        composable("addNewBook") {
            AgregarBook(
                onSave = { newBook ->
                    bookViewModel.addBook(newBook) // Llamada para agregar el nuevo libro
                    navController.popBackStack() // Regresar a la pantalla anterior
                },
                onCancel = { navController.popBackStack() } // Regresar sin guardar
            )
        }


        composable("addEdit/{bookId}") { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId")?.toIntOrNull()
            val book = books.find { it.id == bookId }
            Editar_Book(
                book = book,
                onSave = { newBook ->
                    if (bookId != null) {
                        bookViewModel.updateBook(newBook)
                    } else {
                        bookViewModel.addBook(newBook)
                    }
                    navController.popBackStack()
                },
                onCancel = { navController.popBackStack() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    Ejercicio5Theme{
        //val bookViewModel: BookViewModel = viewModel()
        //Navegacion(navController = rememberNavController(),  bookViewModel)
        //Home(bookViewModel, onAddBook={}, onEditBook={})

        //val dummyRepository = BookRepository(dao = DummyBookDao()) // Implementa un dao ficticio si es necesario
        //val dummyViewModel = BookViewModel(dummyRepository)
        //Home(dummyViewModel, onAddBook = {}, onEditBook = {})
    }
}
package com.example.scaffold

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scaffold.ui.theme.ScaffoldTheme
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }
        super.onCreate(savedInstanceState)

        setContent {
            val scaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()
            ScaffoldTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ScaffoldApp()

                    }
                }
            }
        }
    }


@Composable
fun ScaffoldApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable(route = "home") {
            MainApp(navController = navController)
        }
        composable(route = "info") {
            InfoScreen(navController = navController)
        }
        composable(route="settings"){
            SettingScreen(navController = navController)
        }
    }
}

@Composable
fun MainApp(navController: NavController) {

    val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }
    val context = LocalContext.current
    val Downloader = AndroidDownloader(context)
    val WRITE_EXTERNAL_STORAGE_PERMISSION = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    val PERMISSION_REQUEST_CODE = 123
    Scaffold(
        topBar = { MyTopBar(title = "Downloader", navController = navController) },
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var text by rememberSaveable { mutableStateOf("") }
                Spacer(modifier = Modifier.size(200.dp))
                Text(text = "Paste link to download")
                Spacer(modifier = Modifier.size(18.dp))
                TextField(

                    value = text,
                    onValueChange = {
                        text = it
                    },
                    label = { Text("Url") }
                )

                Spacer(modifier = Modifier.size(18.dp))

                Button(onClick = {


                    val scope = CoroutineScope(Dispatchers.Default + coroutineExceptionHandler)



                    // Launch a new coroutine in the scope
                    scope.launch {
                        val URL = text.toString()
                        Downloader.DownloadFile(URL)
                    }
                    Toast.makeText(context, "Loading ended", LENGTH_LONG).show()


                }) {
                    Text(text = "Download")
                }
            }

        }
    )
}

fun writeToLogFile(context: Context, field1: String, field2: String, fileName: String) {
    val externalDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val file = File(externalDir, fileName)

    try {
        val fileOutputStream = FileOutputStream(file, true)
        val line = "$field1, $field2\n"
        fileOutputStream.write(line.toByteArray())
        fileOutputStream.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun moveCharactersBy6(field1: String): String {
    val shiftedChars = CharArray(field1.length)

    for (i in field1.indices) {
        val originalChar = field1[i]
        val shiftedChar = (originalChar.toInt() + 6).toChar()
        shiftedChars[i] = shiftedChar
    }

    return String(shiftedChars)
}

@Composable
fun InfoScreen(navController: NavController) {
    Scaffold(
        topBar = { ScreenTopBar(title = "Password Manager", navController = navController) },
        content = { Text(text = "")
            val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
                throwable.printStackTrace()
            }
            val context = LocalContext.current
            val Downloader = AndroidDownloader(context)
            val WRITE_EXTERNAL_STORAGE_PERMISSION = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            val PERMISSION_REQUEST_CODE = 123
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                var text1 by rememberSaveable { mutableStateOf("") }
                Spacer(modifier = Modifier.size(200.dp))
                Text(text = "Paste login and password")
                Spacer(modifier = Modifier.size(18.dp))
                TextField(

                    value = text1,
                    onValueChange = {
                        text1 = it
                    },
                    label = { Text("Login") }
                )
                var text2 by rememberSaveable { mutableStateOf("") }
                Spacer(modifier = Modifier.size(18.dp))
                TextField(

                    value = text2,
                    onValueChange = {
                        text2 = it
                    },
                    label = { Text("Password") }
                )

                Spacer(modifier = Modifier.size(18.dp))

                Button(onClick = {


                    writeToLogFile(context  , text1, moveCharactersBy6(text2), "test.txt")

                },
                    modifier = Modifier.size(width = 200.dp, height = 40.dp)) {
                    Text(text = "ADD")


                }
            }




        }
    )
}

@Composable
fun SettingScreen(navController: NavController) {
    Scaffold(
        topBar = { ScreenTopBar(title = "Settings", navController = navController) },
        content = { Text(text = "Settings content") }
    )
}


@Composable
fun MyTopBar(title: String, navController: NavController) {
    var expanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text(text = "Downloader") },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Menu, contentDescription = "Three horizontal lines")
            }
        },
        actions = {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "Three vertical dots")
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(onClick = { navController.navigate("info") }) {
                    Text(text = "Info")
                }
                DropdownMenuItem(onClick = { navController.navigate("settings") }) {
                    Text(text = "Settings")
                }
            }
        }
    )
}

@Composable
fun ScreenTopBar(title: String, navController: NavController) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back Arrow")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ScaffoldTheme {
        ScaffoldApp()
    }
}
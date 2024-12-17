package bg.tu_varna.sit.si.pmiu_lab10

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import bg.tu_varna.sit.si.pmiu_lab10.ui.theme.PMIU_LAB10Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      PMIU_LAB10Theme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          CoroutinesApp()
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoroutinesApp() {
  val navController = rememberNavController()
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Примерен Action Bar") },
        actions = {
          IconButton(onClick = { navController.navigate("one") }) {
            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "1")
          }
          IconButton(onClick = { navController.navigate("two") }) {
            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "2")
          }
          IconButton(onClick = { navController.navigate("three") }) {
            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "3")
          }
          IconButton(onClick = { navController.navigate("four") }) {
            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "4")
          }
        }
      )
    }
  ) { paddingValues ->
    NavigationGraph(navController = navController, paddingValues = paddingValues)
  }
}

@Composable
fun NavigationGraph(navController: NavHostController, paddingValues: PaddingValues) {
  NavHost(
    navController = navController,
    startDestination = "one",
    modifier = Modifier.padding(paddingValues)
  ) {
    composable("one") {
      CoroutinesDemoOne()
    }
    composable("two") {
      CoroutinesDemoTwo()
    }
    composable("three") {
      CoroutinesDemoThree()
    }
    composable("four") {
      CoroutinesDemoFour()
    }
  }
}

@Composable
fun CoroutinesDemoOne() {
  Column(
    Modifier.fillMaxSize(),
    Arrangement.Center,
    Alignment.CenterHorizontally
  ) {
    Increment()
    DataLoader()
  }
}

@Composable
fun CoroutinesDemoTwo() {
  Column(
    Modifier.fillMaxSize(),
    Arrangement.Center,
    Alignment.CenterHorizontally
  ) {
    IncrementLoopTwo()
    DataLoaderThread()
  }
}

@Composable
fun CoroutinesDemoThree() {
  Column(
    Modifier.fillMaxSize(),
    Arrangement.Center,
    Alignment.CenterHorizontally
  ) {
    IncrementLoop()
    DataLoaderException()
  }
}

@Composable
fun CoroutinesDemoFour() {
  Column(
    Modifier.fillMaxSize(),
    Arrangement.Center,
    Alignment.CenterHorizontally
  ) {
    Increment()
    DataLoaderAsync()
  }
}

@Composable
fun Increment(modifier: Modifier = Modifier) {
  var count by remember { mutableIntStateOf(0) }
  Column(modifier = modifier) {
    Text("Count: $count")
    Button(onClick = {
      count++
    }) {
      Text("Increment")
    }
  }
}

@Composable
fun IncrementLoop(modifier: Modifier = Modifier) {
  var count by remember { mutableIntStateOf(0) }

  Column(modifier = modifier) {
    Text("Count: $count")
    Button(onClick = {
      do {
        count++
      } while (count > 0)
    }) {
      Text("Increment")
    }
  }
}

@Composable
fun IncrementLoopTwo(modifier: Modifier = Modifier) {
  var count by remember { mutableIntStateOf(0) }

  for (i in 1..5000000) {
    count++
  }

  Column(modifier = modifier) {
    Text("Count: $count")
  }
}

@Composable
fun DataLoaderThread() {
  var data by remember { mutableStateOf("") }

  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    CircularProgressIndicator()

    Button(onClick = {
      data = fetchDataThread(1)
    }) {
      Text("Increment")
    }
  }
}

@Composable
fun DataLoader() {
  var isLoading by remember { mutableStateOf(true) }
  var data by remember { mutableStateOf("") }

  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    if (isLoading) {
      CircularProgressIndicator()
    } else {
      Text(text = data, style = MaterialTheme.typography.titleLarge)
    }

    Button(onClick = {
      isLoading = true
      CoroutineScope(Dispatchers.Default).launch {
        data = fetchData(1)
        isLoading = false
      }
    }) {
      Text("Increment")
    }
  }
}

@Composable
fun DataLoaderException() {
  var isLoading by remember { mutableStateOf(true) }
  var data by remember { mutableStateOf("") }

  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    if (isLoading) {
      CircularProgressIndicator()
    } else {
      Text(text = data, style = MaterialTheme.typography.titleLarge)
    }

    Button(onClick = {
      isLoading = true
      CoroutineScope(Dispatchers.Default).launch {
        data = try {
          fetchDataException(1)
        } catch (e: Exception) {
          e.message.toString()
        }
        isLoading = false
      }
    }) {
      Text("Increment")
    }
  }
}

@Composable
fun DataLoaderAsync() {
  var isLoading by remember { mutableStateOf(true) }
  var data by remember { mutableStateOf("") }
  var newData by remember { mutableStateOf("") }

  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    if (isLoading) {
      // Show a progress indicator while loading data
      CircularProgressIndicator()
    } else {
      // Show the loaded data
      Text(text = data, style = MaterialTheme.typography.titleLarge)
      Text(text = newData, style = MaterialTheme.typography.titleLarge)
    }

    Button(onClick = {
      isLoading = true

      CoroutineScope(Dispatchers.Main).launch {
        isLoading = false
        val job1 = async { fetchData(1) }
        val job2 = async { fetchData(2) }

        data += job2.await()
        newData += job1.await()
      }

    }) {
      Text("Increment")
    }
  }
}

fun fetchDataThread(number: Int): String {
//    Thread(Runnable {
//        var count = 0
//            do{
//            count++
//            } while(count > 0)
//
//    }).start()
  Thread.sleep(2000) // Симулиране на мрежово забавяне
  return "Данните са заредени успешно! - $number"
}

suspend fun fetchData(number: Int): String {
  delay(2000)
  return "Данните са заредени успешно! - $number"
}

suspend fun fetchData1000(number: Int): String {
  delay(1000)
  return "Данните са заредени успешно! - $number"
}

suspend fun fetchDataException(number: Int): String {
  delay(2000)
  if ((0..1).random() == 0) {
    throw Exception("Появи се грешка!!! - $number")
  }
  return "Данните са заредени успешно! - $number"
}

@Preview(showBackground = true)
@Composable
fun IncrementPreview() {
  PMIU_LAB10Theme {
    Increment()
  }
}

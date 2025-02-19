package bg.tu_varna.sit.test03

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import bg.tu_varna.sit.test03.ui.theme.Test03Theme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      Test03Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          Greeting(
            modifier = Modifier.padding(innerPadding)
          )
        }
      }
    }
  }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {

  val context = LocalContext.current
  var isLoadActivityFailed by remember { mutableStateOf(false) }

  val rightNow = Calendar.getInstance()
  val hour: Int =rightNow.get(Calendar.HOUR_OF_DAY)

  var greetingText: String = ""
  var color:Color = Color.Yellow

  if (hour in 0..11) {
    greetingText = "Добро утро"
  } else if (hour in 12..15) {
    greetingText = "Добър ден"
    color = Color.Cyan
  } else {
    greetingText = "Добър вечер"
    color = Color.Gray
  }

  Column(modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center) {
    Text(
      text = stringResource(R.string.greeting),
      modifier = modifier,
      color = color,
      fontSize = 36.sp
    )
    if (!isLoadActivityFailed) {
      CircularProgressIndicator()
    } else {
      Text(
        text = stringResource(R.string.errorLoading),
        modifier = modifier,
        color = Color.Red
      )
    }
  }

  LaunchedEffect(Unit) {
    while (true) {
      delay(3000)
      if ((0..1).random() == 0) {
        val intent = Intent(context, GalleryActivity::class.java)
        context.startActivity(intent)
        break
      } else {
        isLoadActivityFailed = true
        delay(1000)
        isLoadActivityFailed = false
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  Test03Theme {
    Greeting()
  }
}


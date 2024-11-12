package bg.tu_varna.sit.lab_05

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bg.tu_varna.sit.lab_05.ui.theme.Lab05Theme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      Lab05Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          Greeting(
            stringResource(R.string.message),
            stringResource(R.string.from),
            modifier = Modifier.padding(innerPadding)
          )
        }
      }
    }
  }
}

@Composable
fun Greeting(message: String, from: String, modifier: Modifier = Modifier) {
  Column(
    verticalArrangement = Arrangement.Center,
    modifier = modifier.padding(8.dp)
  ) {
    Text(
      text = message,
      fontSize = 96.sp,
      lineHeight = 110.sp,
      textAlign = TextAlign.Center,
      modifier = modifier
    )
    Text(
      text = from,
      fontSize = 36.sp,
      modifier = modifier
        .padding(16.dp)
        .align(alignment = Alignment.End)
    )
  }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
  Lab05Theme {
    Greeting(
      stringResource(R.string.message),
      stringResource(R.string.from),
    )
  }
}
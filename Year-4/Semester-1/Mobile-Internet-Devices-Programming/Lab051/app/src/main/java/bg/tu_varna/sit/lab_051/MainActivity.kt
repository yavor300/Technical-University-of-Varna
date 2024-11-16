package bg.tu_varna.sit.lab_051

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import bg.tu_varna.sit.lab_051.ui.theme.Lab051Theme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      Lab051Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          DiceWithImageAndButton(
            modifier = Modifier
              .padding(innerPadding)
              .fillMaxSize()
              .wrapContentSize(Alignment.Center)
          )
        }
      }
    }
  }
}

@Preview
@Composable
fun DiceWithImageAndButton(modifier: Modifier = Modifier) {
  var result by remember { mutableStateOf(1) }
  val imageResource = when (result) {
    1 -> R.drawable.dice_1
    2 -> R.drawable.dice_2
    3 -> R.drawable.dice_3
    4 -> R.drawable.dice_4
    5 -> R.drawable.dice_5
    else -> R.drawable.dice_6

  }
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Image(
      painter = painterResource(imageResource),
      contentDescription = "1"
    )
    Button(onClick = { result = (1..6).random() }) {
      Text(stringResource(R.string.roll))
    }
  }
}

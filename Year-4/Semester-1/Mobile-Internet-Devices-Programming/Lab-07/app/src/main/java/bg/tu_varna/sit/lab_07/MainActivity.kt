package bg.tu_varna.sit.lab_07

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import bg.tu_varna.sit.lab_07.data.Person
import bg.tu_varna.sit.lab_07.ui.theme.Lab07Theme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      Lab07Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          MainComponent(
            modifier = Modifier.padding(innerPadding)
          )
        }
      }
    }
  }
}

@Composable
fun MainComponent(modifier: Modifier = Modifier) {
  val context = LocalContext.current;
  val intent = Intent(context, InsertActivity::class.java)
  var text by remember { mutableStateOf("") }
  Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Text(
      text = stringResource(R.string.app_name),
      modifier = modifier
    )
    Row {
      TextField(
        value = text,
        onValueChange = { text = it },
        placeholder = {
          Text(
            text = stringResource(R.string.enter)
          )
        },
        keyboardOptions = KeyboardOptions(
          keyboardType = KeyboardType.Text
        )
      )
      Button(onClick = {
        intent.putExtra(EXTRA_PERSON, Person(text))
        context.startActivity(intent)
      }) {
        Text(stringResource(R.string.next), fontSize = 12.sp)
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  Lab07Theme {
    MainComponent()
  }
}
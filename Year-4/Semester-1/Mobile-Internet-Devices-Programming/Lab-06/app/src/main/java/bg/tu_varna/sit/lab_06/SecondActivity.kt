package bg.tu_varna.sit.lab_06

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.magnifier
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bg.tu_varna.sit.lab_06.ui.theme.Lab06Theme

class SecondActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      Lab06Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          TextFieldUI(modifier = Modifier.padding(innerPadding))
        }
      }
    }
  }
}

@Composable
fun TextFieldUI(modifier: Modifier) {
  var text by remember { mutableStateOf("") }
  val context = LocalContext.current
  val intent = Intent(context, MainActivity::class.java)
  Column(
    modifier =
    Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    TextField(
      value = text,
      label = { Text(text = "Enter...") },
      onValueChange = { text = it },
      modifier = Modifier.fillMaxWidth(),
      placeholder = { Text(text="Enter name")},
      textStyle = TextStyle(color = Color.Blue),
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        autoCorrect = true,
        capitalization = KeyboardCapitalization.Characters
      )
    )
    Text(text = "You entered $text")
    Spacer(modifier = Modifier.height(20.dp))
    Button(
      onClick = {
        context.startActivity(intent)
      }
    ) {
      Text("Back")
    }
  }
}

  @Preview(showBackground = true)
  @Composable
  fun GreetingPreview2() {
    Lab06Theme {
      TextFieldUI(modifier = Modifier)
    }
  }
package bg.tu_varna.sit.lab_06

import android.content.Intent
import android.os.Bundle
import android.provider.Telephony.Mms.Intents
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

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      Lab06Theme {
        NextActivity()
      }
    }
  }
}

@Composable
fun NextActivity() {
  val context = LocalContext.current
  val intent = Intent(context, SecondActivity::class.java)
  val intentActionSend = Intent(Intent.ACTION_SEND)
  intentActionSend.type = "text/plain"
  intentActionSend.putExtra(Intent.EXTRA_EMAIL, arrayOf("s21621577@onlinedu.tu-varna.bg"))
  intentActionSend.putExtra(Intent.EXTRA_SUBJECT, "This is subject")
  intentActionSend.putExtra(Intent.EXTRA_TEXT, "This is text")
  Column(modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally) {
    Button(onClick = {
      context.startActivity(intent)
    }) {
      Text(text = "Next Activity")
    }
    Spacer(modifier = Modifier.height(20.dp))
    Button(onClick = {
      context.startActivity(intentActionSend)
    }) {
      Text("Action send")
    }
  }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  Lab06Theme {
    NextActivity()
  }
}
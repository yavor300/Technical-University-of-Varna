package bg.tu_varna.sit.lab_07

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import bg.tu_varna.sit.lab_07.data.Person
import bg.tu_varna.sit.lab_07.ui.theme.Lab07Theme

class DisplayActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val person = intent.getParcelableExtra(EXTRA_PERSON, Person::class.java)
    enableEdgeToEdge()
    setContent {
      Lab07Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          DisplayCompose(
            name = person!!.name,
            email = person.email,
            phone = person.phone,
            modifier = Modifier.padding(innerPadding)
          )
        }
      }
    }
  }
}

@Composable
fun DisplayCompose(name: String, email:String, phone:String, modifier: Modifier = Modifier) {
  Column(
    modifier = modifier.background(Color.LightGray).fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Text(
      text = "Hello $name!",
      modifier = modifier,
      color = Color.White
    )
    Text(
      text = "Your email is $email",
      modifier = modifier,
      color = Color.Green
    )
    Text(
      text = "Your phone is $phone",
      modifier = modifier,
      color = Color.Red
    )
  }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
  Lab07Theme {
    DisplayCompose("Android", "email", "0878888888")
  }
}
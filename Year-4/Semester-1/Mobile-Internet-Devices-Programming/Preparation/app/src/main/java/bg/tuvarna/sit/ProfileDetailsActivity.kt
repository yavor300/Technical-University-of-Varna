package bg.tuvarna.sit

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bg.tuvarna.sit.data.Profile
import bg.tuvarna.sit.ui.theme.PreparationTheme

class ProfileDetailsActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val profile: Profile = intent.getParcelableExtra("profile", Profile::class.java)!!
    enableEdgeToEdge()
    setContent {
      PreparationTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          DisplayProfile(
            profile,
            Modifier.padding(innerPadding)
          )
        }
      }
    }
  }
}

@Composable
fun DisplayProfile(profile: Profile, modifier: Modifier = Modifier) {

  val context = LocalContext.current

  Column(
    modifier = Modifier.padding(16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    ReadOnlyTextField(value = profile.name, label = "Name")
    Spacer(modifier = Modifier.height(8.dp))
    ReadOnlyTextField(value = profile.email, label = "Email")
    Spacer(modifier = Modifier.height(8.dp))
    ReadOnlyTextField(value = profile.phone, label = "Phone")

    Button(
      onClick = {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
          type = "message/rfc822"
          putExtra(Intent.EXTRA_EMAIL, arrayOf(profile.email))
          putExtra(Intent.EXTRA_SUBJECT, "This is subject")
          putExtra(Intent.EXTRA_TEXT, "This is text")
        }
        context.startActivity(Intent.createChooser(emailIntent, "Choose an email client"))
      }
    ) {
      Text("Send Email")
    }
  }
}

@Preview(showBackground = true)
@Composable
fun PreviewDisplayProfile() {

  PreparationTheme {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
      DisplayProfile(
        Profile("Jane Doe", "jane.doe@example.com", "555-1234"),
        Modifier.padding(innerPadding)
      )
    }
  }
}

package bg.tuvarna.sit

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bg.tuvarna.sit.data.Profile
import bg.tuvarna.sit.ui.theme.PreparationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      PreparationTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          MainScreen(Modifier.padding(innerPadding))
        }
      }
    }
  }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {

  val context = LocalContext.current
  var email by remember { mutableStateOf("") }
  var isNotValidEmail by remember { mutableStateOf(false) }
  var phone by remember { mutableStateOf("") }
  var isNotValidPhone by remember { mutableStateOf(false) }
  var name by remember { mutableStateOf("") }
  var isNameEmpty by remember { mutableStateOf(false) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Image(
      painter = painterResource(R.drawable.ic_launcher_background),
      contentDescription = "Example image"
    )
    Spacer(Modifier.height(20.dp))

    CustomTextField(
      value = name,
      label = "Enter name",
      onValueChange = { name = it },
      errorMessage = "Name cannot be empty",
      errorCheck = isNameEmpty,
      keyboardType = KeyboardType.Text
    )
    Spacer(Modifier.height(10.dp))

    CustomTextField(
      value = email,
      label = "Enter email",
      onValueChange = { email = it },
      errorMessage = "Invalid email format",
      errorCheck = isNotValidEmail,
      keyboardType = KeyboardType.Email
    )
    Spacer(Modifier.height(10.dp))

    CustomTextField(
      value = phone,
      label = "Enter phone number",
      onValueChange = { phone = it },
      errorMessage = "Invalid phone number",
      errorCheck = isNotValidPhone,
      keyboardType = KeyboardType.Phone
    )
    Spacer(Modifier.height(10.dp))

    Button(onClick = {
      isNotValidEmail = !isValidEmail(email)
      isNotValidPhone = !isValidPhone(phone)
      isNameEmpty = isNotBlank(name)

      if (!isNotValidEmail && !isNotValidPhone && !isNameEmpty) {
        val profile = Profile(name, email, phone)
        val intent = Intent(context, ProfileDetailsActivity::class.java).apply {
          putExtra("profile", profile);
        }
        context.startActivity(intent)
      }

    }) { Text("Sign up") }
  }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
  PreparationTheme {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
      MainScreen(modifier = Modifier.padding(innerPadding))
    }
  }
}



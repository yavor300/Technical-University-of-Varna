package bg.tu_varna.sit.pmiu_exam_2

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bg.tu_varna.sit.pmiu_exam_2.data.EXTRA_PROFILE
import bg.tu_varna.sit.pmiu_exam_2.data.Profile
import bg.tu_varna.sit.pmiu_exam_2.ui.theme.Pmiuexam2Theme

class SecondActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val name: String = intent.getParcelableExtra("name", String::class.java)!!
    enableEdgeToEdge()
    setContent {
      Pmiuexam2Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          FormAdditionalDataScreen(name, Modifier.padding(innerPadding))
        }
      }
    }
  }
}

@Composable
fun FormAdditionalDataScreen(nameInput: String, modifier: Modifier = Modifier) {

  val context = LocalContext.current

  var email by remember { mutableStateOf("") }
  var isEmailValid by remember { mutableStateOf(true) }
  var phone by remember { mutableStateOf("") }
  var isPhoneValid by remember { mutableStateOf(true) }
  var webUrl by remember { mutableStateOf("") }
  var isWebUrlValid by remember { mutableStateOf(true) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {

    Spacer(Modifier.height(100.dp))

    Greeting(nameInput)

    Spacer(Modifier.height(16.dp))

    ErrorMessage(
      !isEmailValid || !isPhoneValid || !isWebUrlValid,
      stringResource(R.string.errorMesage)
    )
    CustomTextField(
      value = email,
      label = stringResource(R.string.formEmailLabel),
      placeholder = stringResource(R.string.formEmailPlacehodler),
      onValueChange = { email = it },
      errorCheck = !isEmailValid,
      keyboardType = KeyboardType.Text
    )
    Spacer(Modifier.height(16.dp))

    CustomTextField(
      value = phone,
      label = stringResource(R.string.formPhoneLabel),
      placeholder = stringResource(R.string.formPhonePlacehodler),
      onValueChange = { phone = it },
      errorCheck = !isPhoneValid,
      keyboardType = KeyboardType.Phone
    )
    Spacer(Modifier.height(16.dp))

    CustomTextField(
      value = webUrl,
      label = stringResource(R.string.formWebUrlLabel),
      placeholder = stringResource(R.string.formWebUrlPlacehodler),
      onValueChange = { webUrl = it },
      errorCheck = !isWebUrlValid,
      keyboardType = KeyboardType.Text
    )
    Spacer(Modifier.height(16.dp))

    Button(onClick = {

      isEmailValid = isEmailValid(email)
      isPhoneValid = isPhoneValid(phone)
      isWebUrlValid = isWebUrlValid(email)

      if (isEmailValid && isPhoneValid && isWebUrlValid) {
        val intent = Intent(context, ProfileDetailsActivity::class.java).apply {
          putExtra(EXTRA_PROFILE, Profile(nameInput, email, phone, webUrl));
        }
        context.startActivity(intent)
      }

    }) { Text(stringResource(R.string.showButton)) }
  }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  Pmiuexam2Theme {
    FormAdditionalDataScreen("Иван")
  }
}
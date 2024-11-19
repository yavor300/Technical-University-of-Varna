package bg.tu_varna.sit.lab_07

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.RememberObserver
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

class InsertActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val person = intent!!.getParcelableExtra(EXTRA_PERSON, Person::class.java)
    enableEdgeToEdge()
    setContent {
      Lab07Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          InsertCompose(
            person = person!!,
            modifier = Modifier.padding(innerPadding)
          )
        }
      }
    }
  }
}

@Composable
fun InsertCompose(person: Person, modifier: Modifier = Modifier) {
  val context = LocalContext.current;
  val intent = Intent(context, DisplayActivity::class.java)
  var email by remember { mutableStateOf("") }
  var phone by remember { mutableStateOf("") }
  var isNotValidEmail by remember { mutableStateOf(false) }
  var isNotValidPhone by remember { mutableStateOf(false) }
  Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Text(
      text = "Hello ${person.name}!",
      modifier = modifier
    )
    CustomTextField(
      modifier = modifier,
      name = email,
      text = stringResource(R.string.email),
      isError = isNotValidEmail,
      keyboardType = KeyboardType.Email
    ) {
      email = it
    }
    TextField(
      value = phone,
      onValueChange = { phone = it },
      placeholder = {
        Text(
          text = stringResource(R.string.phone)
        )
      },
      isError = isNotValidPhone,
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Phone
      )
    )
    if (isNotValidEmail) {
      Text(
        text = stringResource(R.string.errorMessage),
        modifier = modifier
      )
    }
    Button(onClick = {
      isNotValidEmail = !Patterns.EMAIL_ADDRESS.matcher(email).matches()
      isNotValidPhone = !Patterns.PHONE.matcher(phone).matches()
      if (!isNotValidEmail && !isNotValidPhone) {
        person.email = email
        person.phone = phone
        intent.putExtra(EXTRA_PERSON, person)
        context.startActivity(intent)
      }
    }) {
      Text(stringResource(R.string.next), fontSize = 12.sp)
    }
  }
}

@Composable
fun CustomTextField(
  modifier: Modifier,
  name: String,
  text: String,
  isError: Boolean,
  keyboardType: KeyboardType,
  onValueChange: (String) -> Unit
) {
  TextField(
    modifier = modifier,
    value = name,
    onValueChange = onValueChange,
    placeholder = {
      Text(
        text = text
      )
    },
    isError = isError,
    keyboardOptions = KeyboardOptions(
      keyboardType = keyboardType
    )
  )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
  Lab07Theme {
    InsertCompose(Person("Android"))
  }
}
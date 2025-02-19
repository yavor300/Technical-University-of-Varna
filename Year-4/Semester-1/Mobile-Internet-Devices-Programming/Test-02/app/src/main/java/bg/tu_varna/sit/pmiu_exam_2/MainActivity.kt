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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bg.tu_varna.sit.pmiu_exam_2.data.EXTRA_NAME
import bg.tu_varna.sit.pmiu_exam_2.ui.theme.Pmiuexam2Theme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      Pmiuexam2Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          FormNameScreen(Modifier.padding(innerPadding))
        }
      }
    }
  }
}

@Composable
fun FormNameScreen(modifier: Modifier = Modifier) {

  val context = LocalContext.current

  var name by remember { mutableStateOf("") }
  var isNameValid by remember { mutableStateOf(true) }

  Column (
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {

    Spacer(Modifier.height(100.dp))

    Greeting()

    Spacer(Modifier.height(16.dp))

    CustomTextField(
      value = name,
      label = stringResource(R.string.formNameLabel),
      placeholder = stringResource(R.string.formNamePlacehodler),
      onValueChange = { name = it },
      errorCheck = !isNameValid,
      keyboardType = KeyboardType.Text
    )
    ErrorMessage(!isNameValid, stringResource(R.string.errorMesage))
    Spacer(Modifier.height(16.dp))

    Button(onClick = {

      isNameValid = isNameValid(name)

      if (isNameValid) {
        val intent = Intent(context, SecondActivity::class.java).apply {
          putExtra(EXTRA_NAME, name);
        }
        context.startActivity(intent)
      }

    }) { Text(stringResource(R.string.mainScreenButtonText)) }
  }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
  Pmiuexam2Theme {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
      FormNameScreen(modifier = Modifier.padding(innerPadding))
    }
  }
}

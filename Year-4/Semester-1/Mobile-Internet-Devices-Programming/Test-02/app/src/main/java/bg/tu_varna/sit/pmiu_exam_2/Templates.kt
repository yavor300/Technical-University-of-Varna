package bg.tu_varna.sit.pmiu_exam_2

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextField(
  value: String,
  label: String,
  placeholder: String,
  onValueChange: (String) -> Unit,
  modifier: Modifier = Modifier,
  keyboardType: KeyboardType = KeyboardType.Text,
  errorCheck: Boolean
) {
  TextField(
    value = value,
    onValueChange = onValueChange,
    label = { Text(label) },
    placeholder = { Text(placeholder) },
    keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
    isError = errorCheck,
    modifier = modifier.fillMaxWidth()
  )
}

@Composable
fun ErrorMessage(hasError: Boolean, message: String) {
  if (hasError) {
    Text(message, color = MaterialTheme.colorScheme.error)
  }
}

@Composable
fun Greeting(name: String? = null) {
  Text(stringResource(R.string.greeting), fontSize = 68.sp, color = Color.Yellow, softWrap = false)
  name?.let {
    Text(name, fontSize = 68.sp, color = Color.Yellow, softWrap = false)
  }
}

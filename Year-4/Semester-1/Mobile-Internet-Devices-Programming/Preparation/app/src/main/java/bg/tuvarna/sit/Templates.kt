package bg.tuvarna.sit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextField(
  value: String,
  label: String,
  onValueChange: (String) -> Unit,
  modifier: Modifier = Modifier,
  keyboardType: KeyboardType = KeyboardType.Text,
  errorMessage: String? = null,
  errorCheck: Boolean
) {
  TextField(
    value = value,
    onValueChange = onValueChange,
    label = { Text(label) },
    keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
    isError = errorCheck,
    modifier = modifier.fillMaxWidth()
  )
  errorMessage?.let {
    if (errorCheck) {
      Text(it, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
    }
  }
}

@Composable
fun ReadOnlyTextField(value: String, label: String) {
  TextField(
    value = value,
    onValueChange = {}, // No operation on value change
    label = { Text(label) },
    readOnly = true, // Make the TextField read-only
    singleLine = true,
    textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
    modifier = Modifier.fillMaxWidth()
  )
}

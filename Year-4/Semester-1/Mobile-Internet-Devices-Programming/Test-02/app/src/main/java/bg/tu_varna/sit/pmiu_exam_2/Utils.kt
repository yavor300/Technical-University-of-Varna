package bg.tu_varna.sit.pmiu_exam_2

import android.util.Patterns
import bg.tu_varna.sit.pmiu_exam_2.data.MINIMUM_NAME_LENGTH

fun isNameValid(input: String): Boolean = input.isNotBlank() && input.length >= MINIMUM_NAME_LENGTH
fun isEmailValid(input: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(input).matches()
fun isPhoneValid(input: String): Boolean = Patterns.PHONE.matcher(input).matches()
fun isWebUrlValid(input: String): Boolean = Patterns.WEB_URL.matcher(input).matches()

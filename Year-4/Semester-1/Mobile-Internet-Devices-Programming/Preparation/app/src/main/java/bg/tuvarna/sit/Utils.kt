package bg.tuvarna.sit

import android.util.Patterns

fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
fun isValidPhone(phone: String): Boolean = Patterns.PHONE.matcher(phone).matches()
fun isNotBlank(input: String): Boolean = input.isBlank()

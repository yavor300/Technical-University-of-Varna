package bg.tu_varna.sit.test03.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Course(

  @StringRes val stringResourceId: Int,
  @DrawableRes val drawableResourceId: Int,
  var spaces: Int = 300
)

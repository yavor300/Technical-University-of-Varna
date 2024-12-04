package bg.tu_varna.sit.lab_09.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Place(

  @StringRes val stringResourceId: Int,
  @DrawableRes val drawableResourceId: Int
)
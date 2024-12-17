package bg.tu_varna.sit.lab_11.model

import androidx.annotation.DrawableRes

data class Place(
  @DrawableRes val drawableResourceId: Int,
  var description: String = "New place"
)

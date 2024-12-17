package bg.tu_varna.sit.lab_11.data

import bg.tu_varna.sit.lab_11.R
import bg.tu_varna.sit.lab_11.model.Place

object Datasource {

  private val places: MutableList<Place> = mutableListOf()

  @Deprecated("Use list in composable function instead")
  fun add(place: Place) {
    places.add(place)
  }

  @Deprecated("Use list in composable function instead")
  fun loadPlaces(): List<Place> {
    return places
  }

  private fun getDrawableResource(): Int {

    return listOf(
      R.drawable.image1,
      R.drawable.image2,
      R.drawable.image3,
      R.drawable.image4,
      R.drawable.image5,
      R.drawable.image6,
      R.drawable.image7,
      R.drawable.image8,
      R.drawable.image9,
      R.drawable.image10,
    ).random()
  }

  fun generatePlace(): Place {

    return Place(getDrawableResource(), "Place ${(1..10).random()}")
  }
}
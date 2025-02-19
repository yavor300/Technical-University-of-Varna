package bg.tu_varna.sit.test03.data

import bg.tu_varna.sit.test03.R
import bg.tu_varna.sit.test03.model.Course

object Datasource {

  fun load(): List<Course> {
    return listOf(
      Course(R.string.law, R.drawable.law),
      Course(R.string.film, R.drawable.film),
      Course(R.string.tech, R.drawable.tech),
      Course(R.string.crafts, R.drawable.crafts),
      Course(R.string.design, R.drawable.design),
      Course(R.string.architecture, R.drawable.architecture),
      Course(R.string.automotive, R.drawable.automotive),
      Course(R.string.biology, R.drawable.biology),
      Course(R.string.business, R.drawable.business),
      Course(R.string.culinary, R.drawable.culinary),
      Course(R.string.engineering, R.drawable.engineering),
      Course(R.string.drawing, R.drawable.drawing),
      Course(R.string.ecology, R.drawable.ecology),
      Course(R.string.physics, R.drawable.physics),
      Course(R.string.fashion, R.drawable.fashion),
    )
  }
}

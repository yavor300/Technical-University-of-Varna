package bg.tu_varna.sit.test03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bg.tu_varna.sit.test03.data.Datasource.load
import bg.tu_varna.sit.test03.model.Course
import bg.tu_varna.sit.test03.ui.theme.Test03Theme
import kotlinx.coroutines.delay

class GalleryActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      Test03Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          DataApp(modifier = Modifier.padding(innerPadding))
        }
      }
    }
  }
}

@Composable
fun DataApp(modifier: Modifier = Modifier) {

  val list = remember { mutableStateListOf<Course>() }
  if (list.isEmpty()) {
    list.addAll(load())
  }

  DataList(
    courses = list,
    modifier
  )

  LaunchedEffect(Unit) {
    while (true) {
      val randomDelay : Long = ((500..3000).random()).toLong()
      delay(randomDelay)
      for (course in list) {
        course.spaces -= (1..course.spaces).random()
        if (course.spaces <= 0) {
          course.spaces = 300
        }
      }
    }
  }
}

@Composable
fun DataList(courses: List<Course>, modifier: Modifier) {
  LazyVerticalGrid(
    columns = GridCells.Fixed(2),
  ) {
    items(courses) { course ->
      CourseCard(course, modifier)
    }
  }
}

@Composable
fun CourseCard(course: Course, modifier: Modifier = Modifier) {
  Card(modifier = modifier.padding(8.dp), shape = RoundedCornerShape(8.dp)) {
    Row(modifier = modifier) {
      Image(
        painter = painterResource(course.drawableResourceId),
        contentDescription = stringResource(course.stringResourceId),
        modifier = modifier
          .height(80.dp),
        contentScale = ContentScale.Crop
      )
      Column {
        Text(
          text = stringResource(course.stringResourceId),
          modifier = modifier,
        )
        Row {
          Image(
            painter = painterResource(R.drawable.ic_grain),
            contentDescription = "Availbale space icon",
            modifier = modifier,
            contentScale = ContentScale.Crop
          )
          Text(
            text = course.spaces.toString(),
            modifier = modifier,
          )
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
  Test03Theme {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
      DataApp(modifier = Modifier.padding(innerPadding))
    }
  }
}

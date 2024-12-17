package bg.tu_varna.sit.lab_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bg.tu_varna.sit.lab_09.data.Datasource
import bg.tu_varna.sit.lab_09.model.Place
import bg.tu_varna.sit.lab_09.ui.theme.Lab09Theme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      Lab09Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          PlaceApp(Modifier.padding(innerPadding))
        }
      }
    }
  }
}

@Composable
fun PlaceApp(modifier: Modifier) {
//  PlaceList(
//    places = Datasource().loadPlace()
//  )

  PlaceVerticalGrid(
    places = Datasource().loadPlace()
  )
}

@Composable
fun PlaceHorizontalGrid(places: List<Place>, modifier: Modifier = Modifier) {
  LazyHorizontalGrid(
    rows = GridCells.Fixed(2),
  ) {
    items(places) { place ->
      PlaceCard(place, modifier.padding(8.dp))
    }
  }
}

@Composable
fun PlaceVerticalGrid(places: List<Place>, modifier: Modifier = Modifier) {
  LazyVerticalGrid(
    columns = GridCells.Fixed(2),
  ) {
    items(places) { place ->
      PlaceCard(place, modifier.padding(8.dp))
    }
  }
}

@Composable
fun PlaceList(places: List<Place>, modifier: Modifier = Modifier) {
  LazyColumn(modifier = modifier) {
    items(places) { place ->
      PlaceCard(
        place = place,
        modifier = modifier.padding(8.dp)
      )

    }
  }
}

@Composable
fun PlaceListRow(places: List<Place>, modifier: Modifier = Modifier) {
  LazyRow(modifier = modifier) {
    items(places) { place ->
      PlaceCard(
        place = place,
        modifier = Modifier.padding(8.dp)
      )
    }
  }
}

@Composable
fun PlaceCard(place: Place, modifier: Modifier = Modifier) {
  Card(modifier = modifier) {
    Column(modifier = modifier) {
      Image(
        painter = painterResource(place.drawableResourceId),
        contentDescription = stringResource(place.stringResourceId),
        modifier = modifier
          .fillMaxWidth()
          .height(195.dp),
        contentScale = ContentScale.Crop
      )
      Text(
        text = stringResource(place.stringResourceId),
        modifier = modifier.padding(16.dp),
        style = MaterialTheme.typography.headlineSmall
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun PlaceCardPreview() {
  Lab09Theme {
    // PlaceCard(Place(R.string.place1, R.drawable.image1))
    // PlaceList(Datasource().loadPlace(), Modifier)
     PlaceVerticalGrid(Datasource().loadPlace(), Modifier)
    // PlaceHorizontalGrid(Datasource().loadPlace(), Modifier)
  }
}
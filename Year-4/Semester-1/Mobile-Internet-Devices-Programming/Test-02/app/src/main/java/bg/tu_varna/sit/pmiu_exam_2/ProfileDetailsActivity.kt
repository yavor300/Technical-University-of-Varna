package bg.tu_varna.sit.pmiu_exam_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bg.tu_varna.sit.pmiu_exam_2.data.Profile
import bg.tu_varna.sit.pmiu_exam_2.ui.theme.Pmiuexam2Theme

class ProfileDetailsActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val profile: Profile = intent.getParcelableExtra("profile", Profile::class.java)!!
    enableEdgeToEdge()
    setContent {
      Pmiuexam2Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          DisplayProfile(
            profile,
            modifier = Modifier.padding(innerPadding)
          )
        }
      }
    }
  }
}

@Composable
fun DisplayProfile(profile: Profile, modifier: Modifier = Modifier) {

  Column(
    modifier = Modifier.padding(16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Spacer(Modifier.height(100.dp))

    Greeting(profile.name)

    Spacer(Modifier.height(16.dp))

    Text(stringResource(R.string.detailsText), color = Color.LightGray, softWrap = false)

    Row(
      modifier = Modifier
        .padding(16.dp)
        .background(color = Color.Gray),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.End,

      ) {
      Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
      )
      {
        Text(stringResource(R.string.name), color = Color.LightGray)
        Text(stringResource(R.string.phone), color = Color.LightGray)
        Text(stringResource(R.string.email), color = Color.LightGray)
        Text(stringResource(R.string.webUrl), color = Color.LightGray)
      }
      Column(
        modifier = Modifier.padding(0.dp, 16.dp, 16.dp, 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
      )
      {
        Text(profile.name, color = Color.LightGray)
        Text(profile.phone, color = Color.LightGray)
        Text(profile.email, color = Color.LightGray)
        Text(profile.webUrl, color = Color.LightGray)
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
  Pmiuexam2Theme {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
      DisplayProfile(
        Profile("Иван", "email@email.dod", "052 234 543", "email.dod"),
        Modifier.padding(innerPadding)
      )
    }
  }
}
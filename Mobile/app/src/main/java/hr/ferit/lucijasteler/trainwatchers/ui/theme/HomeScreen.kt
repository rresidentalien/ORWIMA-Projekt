package hr.ferit.lucijasteler.trainwatchers.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import hr.ferit.lucijasteler.trainwatchers.data.TrainViewModel
import java.util.Date

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel : TrainViewModel = TrainViewModel()) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = AntiqueWhite)
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title("Trainwatchers")
        Subtitle("Lucija")
        Counter(viewModel)
    }
}

@Composable
fun Title(title : String) {
    Text(text = title,
        color = Color.Black,
        fontWeight = FontWeight.SemiBold,
        fontSize = 30.sp,
        modifier = Modifier
            .padding(bottom = 10.dp))
}

@Composable
fun Subtitle(name : String) {
    Text(text = "Welcome, $name!",
        color = Color.DarkGray,
        fontSize = 16.sp
    )
}

@Composable
fun Counter(viewModel : TrainViewModel) {
    if (viewModel.trains.isEmpty())
        Text(text = "No trains found. Enter some!",
            modifier = Modifier.padding(top = 30.dp),
            color = Brown)
    else
        Text(text = "You have entered ${viewModel.trains.size} trains so far",
            modifier = Modifier.padding(top = 30.dp),
            color = Brown)
}

@Composable
fun TrainCard(imageResource : String, model : String, city : String, date : Date) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Brown)
    ){
        Text(text = model)
        Text(text = "$city, $date")
        Image(
            painter = rememberAsyncImagePainter(model = imageResource),
            contentDescription = model,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun TrainsList(viewModel : TrainViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp)
    ) {
        items(viewModel.trains.size) {
            TrainCard(imageResource = viewModel.trains[it].images[0],
                model = viewModel.trains[it].model,
                city = viewModel.trains[it].city,
                date = viewModel.trains[it].date)
        }
    }
}
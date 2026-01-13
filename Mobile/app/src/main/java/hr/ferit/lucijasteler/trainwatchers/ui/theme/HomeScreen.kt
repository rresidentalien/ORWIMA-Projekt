package hr.ferit.lucijasteler.trainwatchers.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import hr.ferit.lucijasteler.trainwatchers.data.TrainViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(navController: NavHostController, viewModel: TrainViewModel, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = AntiqueWhite)
            .padding(top = 50.dp, bottom = 90.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title("Trainwatchers")
        Counter(viewModel)
        TrainsList(navController = navController, viewModel = viewModel)
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
fun Counter(viewModel : TrainViewModel) {
    if (viewModel.trains.isEmpty()) {
        Text(text = "No trains found. Enter some!",
            modifier = Modifier.padding(top = 30.dp),
            color = Brown)
    }
    else {
        Text(text = "You have entered ${viewModel.trains.size} trains so far.",
            modifier = Modifier.padding(top = 30.dp),
            color = Brown)
    }
}

@Composable
fun TrainCard(imageResource: String, model: String, city: String, date: Date, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Brown),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ){
        val formattedDate = SimpleDateFormat("dd.MM.yyyy.", Locale.getDefault()).format(date)
        Text(modifier = Modifier.padding(top = 20.dp, start = 20.dp, bottom = 10.dp), text = model, color = AntiqueWhite, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(modifier = Modifier.padding(start = 20.dp, bottom = 20.dp), text = "$city, $formattedDate", color = AntiqueWhite)
        if (imageResource.isNotEmpty()) {
            AsyncImage(
                model = imageResource,
                contentDescription = model,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@Composable
fun TrainsList(navController: NavHostController, viewModel : TrainViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(viewModel.trains) {train ->
            TrainCard(
                imageResource = train.images.firstOrNull() ?: "",
                model = train.model,
                city = train.city,
                date = train.date,
                onClick = {
                    navController.navigate(Destination.TRAIN_DETAILS_SCREEN.route.replace("{trainId}", train.id))
                }
            )
        }
    }
}

package hr.ferit.lucijasteler.trainwatchers.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import coil.compose.rememberAsyncImagePainter
import hr.ferit.lucijasteler.trainwatchers.data.Train
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TrainDetails(train: Train) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AntiqueWhite)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 90.dp)
    ) {
        ImageHeader(train)
        Spacer(modifier = Modifier.height(15.dp))
        TrainInformation(train)
        Spacer(modifier = Modifier.height(15.dp))
        ImagesRow(train)
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Composable
fun ImageHeader(train: Train) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Image(
            painter = rememberAsyncImagePainter(train.images.firstOrNull()),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )
        Text(
            modifier = Modifier
                .padding(start = 20.dp, bottom = 20.dp),
            text = train.model,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun TrainInformation(train: Train) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = DarkAntiqueWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            DetailText(label = "Operator", value = train.operator)
            DetailText(label = "Country", value = train.country)
            DetailText(label = "City", value = train.city)
            DetailText(label = "Date", value = SimpleDateFormat("dd.MM.yyyy.", Locale.getDefault()).format(train.date))
            DetailText(label = "Description", value = train.description)
        }
    }
}

@Composable
fun DetailText(label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                text = label,
                fontSize = 14.sp,
                color = Brown.copy(alpha = 0.7f)
            )
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Brown
            )
        }
    }
}

@Composable
fun ImagesRow(train : Train) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = DarkAntiqueWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Text(
            text = "Images",
            modifier = Modifier.padding(start = 20.dp, top = 10.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Brown
        )
        Spacer(modifier = Modifier.height(10.dp))
        if (train.images.isNotEmpty()) {
            LazyRow(
                contentPadding = PaddingValues(start = 15.dp, end = 15.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(train.images) { imageUrl ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(imageUrl),
                            contentDescription = null,
                            modifier = Modifier
                                .height(150.dp)
                                .width(200.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        } else {
            Text(
                text = "No images available.",
                modifier = Modifier.padding(start = 15.dp, bottom = 10.dp),
                fontSize = 15.sp,
                color = Brown
            )
        }
    }
}
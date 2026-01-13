package hr.ferit.lucijasteler.trainwatchers.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(train.images.firstOrNull()),
                contentDescription = null,
                modifier = Modifier.fillMaxSize().alpha(0.5f),
                contentScale = ContentScale.Crop
            )
            Text(
                text = train.model,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text("Operator: ${train.operator}", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Country: ${train.country}", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("City: ${train.city}", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Date: ${SimpleDateFormat("dd.MM.yyyy.", Locale.getDefault()).format(train.date)}", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Description: ${train.description}", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(train.images) { imageUrl ->
                    Image(
                        painter = rememberAsyncImagePainter(imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .height(150.dp)
                            .width(200.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

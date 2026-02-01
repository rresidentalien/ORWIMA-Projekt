package hr.ferit.lucijasteler.trainwatchers.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AboutApp() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AntiqueWhite)
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title("Trainwatchers: About")
        AboutText()
        WebsiteLinkButton()
    }
}

@Composable
fun AboutText() {
    Text(text = "Trainwatchers was created as part of my Basics of Web and Mobile Application Development class at FERIT, " +
            "Osijek. It has an accompanying website about my train journeys and some train travel tips, as well as some travel ideas. " +
            "I chose my project to be about trains because I think it's the best form of public transport, and I enjoy seeing different trains when I travel.",
        fontSize = 15.sp,
        color = Brown,
        textAlign = TextAlign.Justify,
        modifier = Modifier
            .padding(top = 20.dp, start = 20.dp)
    )
}

@Composable
fun WebsiteLinkButton() {
    val uriHandler = LocalUriHandler.current
    OutlinedButton(
        onClick = { uriHandler.openUri("https://rresidentalien.github.io/ORWIMA-Projekt/Web/Pages/homepage.html") },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Brown),
        modifier = Modifier
            .height(60.dp)
            .padding(top = 20.dp)){
        Text(text = "Visit website")
    }
}

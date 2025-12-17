package hr.ferit.lucijasteler.trainwatchers.data

import androidx.annotation.DrawableRes
import java.sql.Date

data class Train(
    val model : String,
    val operator : String,
    val country : String,
    val city : String,
    val description : String,
    val date : Date,
    @DrawableRes val images : List<String>,
    val favourite : Boolean
)

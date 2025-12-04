package hr.ferit.lucijasteler.trainwatchers.data

import androidx.annotation.DrawableRes
import java.sql.Date

data class Train(
    val model : String,
    val operator : String,
    val country : String,
    val city : String,
    val date : Date,
    val description : String,
    @DrawableRes val images : List<Int>
)

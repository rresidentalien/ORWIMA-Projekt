package hr.ferit.lucijasteler.trainwatchers.data

import androidx.annotation.DrawableRes
import java.sql.Date

data class Train(
    val id : String = "",
    val model : String = "",
    val operator : String = "",
    val country : String = "",
    val city : String = "",
    val description : String = "",
    val date : Date = Date(0),
    @DrawableRes val images : List<String> = listOf(),
    val favourite : Boolean = false
)

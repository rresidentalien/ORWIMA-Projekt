package hr.ferit.lucijasteler.trainwatchers.data

import java.util.Date

data class Train(
    val id : String = "",
    val model : String = "",
    val operator : String = "",
    val country : String = "",
    val city : String = "",
    val description : String = "",
    val date : Date = Date(),
    val images : List<String> = listOf()
)

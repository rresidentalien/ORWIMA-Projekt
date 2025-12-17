package hr.ferit.lucijasteler.trainwatchers.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlin.text.get

class TrainViewModel : ViewModel() {
    private val db = Firebase.firestore

    val trains = mutableStateListOf<Train>()

    init {
        db.collection("trains")
            .get()
            .addOnSuccessListener {
                    result ->
                for (document in result) {
                    val train = document.toObject(Train::class.java)
                    trains.add(train)
                }
            }
    }
}

package hr.ferit.lucijasteler.trainwatchers.data

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class TrainViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val storage = FirebaseStorage.getInstance()

    val trains = mutableStateListOf<Train>()

    init {
        fetchTrains()
    }

    private fun fetchTrains() {
        db.collection("trains")
            .get()
            .addOnSuccessListener { entries ->
                trains.clear()
                for (item in entries) {
                    val train = item.toObject(Train::class.java).copy(id = item.id)
                    trains.add(train)
                }
            }
    }

    fun addTrain(train: Train, imageUris: List<Uri>) {
        val uploadedImageUrls = mutableListOf<String>()

        if (imageUris.isEmpty()) {
            db.collection("trains")
                .add(train.copy(images = emptyList()))
                .addOnSuccessListener {
                    fetchTrains()
                }
            return
        }

        imageUris.forEach { uri ->
            val fileName = UUID.randomUUID().toString()
            val imageRef = storage.reference.child("images/$fileName")
            imageRef.putFile(uri)
                .addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener { downloadedUrl ->
                        uploadedImageUrls.add(downloadedUrl.toString())
                        if (uploadedImageUrls.size == imageUris.size) {
                            db.collection("trains")
                                .add(train.copy(images = uploadedImageUrls))
                                .addOnSuccessListener {
                                    fetchTrains()
                                }
                        }
                    }
                }
        }
    }
}

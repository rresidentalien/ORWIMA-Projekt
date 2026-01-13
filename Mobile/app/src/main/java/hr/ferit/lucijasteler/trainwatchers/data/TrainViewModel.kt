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
            .addOnSuccessListener { result ->
                trains.clear()
                for (document in result) {
                    val train = document.toObject(Train::class.java).copy(id = document.id)
                    trains.add(train)
                }
            }
    }

    fun addTrain(train: Train, imageUris: List<Uri>) {
        val storageRef = storage.reference
        val uploadedImageUrls = mutableListOf<String>()
        val totalImages = imageUris.size

        if (totalImages == 0) {
            db.collection("trains")
                .add(train.copy(images = emptyList()))
                .addOnSuccessListener {
                    fetchTrains()
                }
            return
        }

        imageUris.forEach { uri ->
            val fileName = UUID.randomUUID().toString()
            val imageRef = storageRef.child("images/$fileName")
            imageRef.putFile(uri)
                .addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                        uploadedImageUrls.add(downloadUri.toString())
                        if (uploadedImageUrls.size == totalImages) {
                            val trainWithImages = train.copy(images = uploadedImageUrls)
                            db.collection("trains")
                                .add(trainWithImages)
                                .addOnSuccessListener {
                                    fetchTrains()
                                }
                        }
                    }
                }
        }
    }
}

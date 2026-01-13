package hr.ferit.lucijasteler.trainwatchers.ui.theme

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import hr.ferit.lucijasteler.trainwatchers.data.Train
import hr.ferit.lucijasteler.trainwatchers.data.TrainViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddNew(trainViewModel: TrainViewModel = viewModel()) {
    var model by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var selectedImages by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(color = AntiqueWhite)
            .padding(top = 50.dp, start = 15.dp, end = 15.dp, bottom = 120.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Title("Add New")
        TextInput(title = "Model", value = model, onValueChange = { model = it })
        TextInput(title = "Operator", value = operator, onValueChange = { operator = it })
        TextInput(title = "Country", value = country, onValueChange = { country = it })
        TextInput(title = "City", value = city, onValueChange = { city = it })
        LongTextInput(title = "Description", value = description, onValueChange = { description = it })
        DatePickerButton(selectedDate = selectedDate, onDateSelected = { selectedDate = it })
        ImageUploadButton(selectedImages = selectedImages, onImagesSelected = { selectedImages = it })
        SubmitButton(
            onClick = {
                val train = Train(
                    model = model,
                    operator = operator,
                    country = country,
                    city = city,
                    description = description,
                    date = Date(selectedDate ?: 0),
                    images = selectedImages.map { it.toString() }
                )
                trainViewModel.AddTrain(train)
                model = ""
                operator = ""
                country = ""
                city = ""
                description = ""
                selectedDate = null
                selectedImages = emptyList()
                showDialog = true
            }
        )

        if (showDialog) {
            AlertDialog(
                title = {
                    Text(text = "Train added!")
                },
                text = {
                    Text(text = "You may view it in My Trains tab or the home screen.")
                },
                onDismissRequest = {
                    showDialog = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                        }
                    ) {
                        Text("Okay!")
                    }
                }
            )
        }
    }
}

@Composable
fun TextInput(title: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(title) },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Brown,
            unfocusedBorderColor = Brown,
            focusedLabelColor = Brown,
            unfocusedLabelColor = Brown,
            cursorColor = Brown,
            focusedTextColor = Brown,
            unfocusedTextColor = Brown
        )
    )
}

@Composable
fun LongTextInput(title: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(title) },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Brown,
            unfocusedBorderColor = Brown,
            focusedLabelColor = Brown,
            unfocusedLabelColor = Brown,
            cursorColor = Brown,
            focusedTextColor = Brown,
            unfocusedTextColor = Brown
        )
    )
}

@Composable
fun DatePickerButton(selectedDate: Long?, onDateSelected: (Long?) -> Unit) {
    var showDatePicker by remember { mutableStateOf(false) }

    OutlinedButton(
        onClick = { showDatePicker = true },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Brown),
        border = BorderStroke(1.dp, Brown),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        if (selectedDate != null) {
            val date = Date(selectedDate)
            val format = SimpleDateFormat("dd.MM.yyyy.", Locale.getDefault())
            Text("Selected date: ${format.format(date)}")
        } else {
            Text("Select date...")
        }
    }
    if (showDatePicker) {
        DatePicker(
            onDateSelected = onDateSelected,
            onDismiss = { showDatePicker = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
fun ImageUploadButton(selectedImages: List<Uri>, onImagesSelected: (List<Uri>) -> Unit) {
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris -> onImagesSelected(uris) }
    )

    OutlinedButton(
        onClick = { imagePickerLauncher.launch("image/*") },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Brown),
        border = BorderStroke(1.dp, Brown),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Text("Upload images...")
    }

    if (selectedImages.isNotEmpty()) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(selectedImages) { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = "Selected image",
                    modifier = Modifier.size(128.dp)
                )
            }
        }
    }
}

@Composable
fun SubmitButton(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        icon = { Icon(Icons.Outlined.Add, contentDescription = "Add") },
        text = { Text("Submit") },
        containerColor = Brown,
        contentColor = AntiqueWhite,
    )
}

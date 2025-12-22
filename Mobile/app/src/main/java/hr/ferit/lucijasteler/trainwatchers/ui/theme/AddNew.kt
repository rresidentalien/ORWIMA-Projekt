package hr.ferit.lucijasteler.trainwatchers.ui.theme

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Preview
@Composable
fun AddNew(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = AntiqueWhite)
            .padding(top = 50.dp, start = 16.dp, end = 16.dp, bottom = 120.dp)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Title("Add New")
            TextInput("Model")
            TextInput("Operator")
            TextInput("Country")
            TextInput("City")
            LongTextInput("Description")
            DatePickerButton()
            ImageUploadButton()
        }
        SubmitButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun TextInput(title : String) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(title) },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        singleLine = true
    )
}

@Composable
fun LongTextInput(title : String) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(title) },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth()
    )
}

@Composable
fun DatePickerButton() {
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Long?>(null) }

    OutlinedButton(
        onClick = { showDatePicker = true },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Brown),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        if (selectedDate != null) {
            val date = java.sql.Date(selectedDate!!)
            Text("Selected date: $date")
        } else {
            Text("Select date...")
        }
    }
    if (showDatePicker) {
        DatePicker(
            onDateSelected = {
                selectedDate = it
            },
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
fun ImageUploadButton() {
    var selectedImages by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris -> selectedImages = uris }
    )

    OutlinedButton(
        onClick = { imagePickerLauncher.launch("image/*") },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Brown),
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
fun SubmitButton(modifier : Modifier = Modifier) {
    ExtendedFloatingActionButton(
        onClick = { TODO() },
        icon = { Icon(Icons.Outlined.Add, contentDescription = "Add") },
        text = { Text("Submit") },
        containerColor = Brown,
        contentColor = AntiqueWhite,
        modifier = modifier
    )
}

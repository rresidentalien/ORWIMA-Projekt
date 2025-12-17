package hr.ferit.lucijasteler.trainwatchers.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
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
import java.text.SimpleDateFormat

@Preview
@Composable
fun AddNew(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AntiqueWhite)
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        AddNewHeader("Add New")
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextInput("Model")
            TextInput("Operator")
            TextInput("Country")
            TextInput("City")
            LongTextInput("Description")
            DatePickerButton()
        }
    }
}

@Composable
fun AddNewHeader(title : String) {
    Text(text = title,
        color = Color.Black,
        fontWeight = FontWeight.SemiBold,
        fontSize = 30.sp)
}

@Composable
fun TextInput(title : String) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(title) },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth().height(60.dp),
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
        modifier = Modifier.fillMaxWidth().height(60.dp)
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
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

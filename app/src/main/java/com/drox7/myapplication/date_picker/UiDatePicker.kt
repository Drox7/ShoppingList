package com.drox7.myapplication.date_picker

//import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiDatePicker() {
    val calendar = Calendar.getInstance()
    calendar.time// add year, month (Jan), date

    // set the initial date
    //val datePickerState = rememberDatePickerState(initialSelectedDateMillis = calendar.timeInMillis)
   // val timePickerState = rememberTimePickerState(initialHour = 12)

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    var selectedDate by remember {
        mutableLongStateOf(calendar.timeInMillis) // or use mutableStateOf(calendar.timeInMillis)
    }

    if (showDatePicker) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            TimePicker(
//                modifier = Modifier.weight(0.5f),
//                state = timePickerState
//            )

//            DatePicker(
//                state = datePickerState
//            )
        }

//        DatePickerDialog(
//            onDismissRequest = {
//                showDatePicker = false
//            },
//            confirmButton = {
//                TextButton(onClick = {
//                    showDatePicker = false
//                    selectedDate = datePickerState.selectedDateMillis!!
//                }) {
//                    Text(text = "Confirm")
//                }
//            },
//            dismissButton = {
//                TextButton(onClick = {
//                    showDatePicker = false
//                }) {
//                    Text(text = "Cancel")
//                }
//            }
//        ) {
//            DatePicker(
//                state = datePickerState,
//                showModeToggle = true
//            )
//        }
    }

    Button(
        onClick = {
            showDatePicker = true
        }
    ) {
        Text(text = "Show Date Picker")
    }

    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ROOT)
    Text(
        text = "Selected date: ${formatter.format(Date(selectedDate))}"
    )
}
package com.drox7.myapplication.date_picker


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.drox7.myapplication.R
import com.drox7.myapplication.dialog.DialogController
import com.drox7.myapplication.ui.theme.BlueLight
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiDatePicker(
    dialogController: DialogController
) {
    //val calendar = Calendar.getInstance()
    //calendar.time// add year, month (Jan), date
    val formatterDate = SimpleDateFormat("dd.MM.yyyy", Locale.ROOT)
    val formatterTime = SimpleDateFormat("HH:mm", Locale.ROOT)
    val formatterHour = SimpleDateFormat("HH", Locale.ROOT)
    val formatterMinute = SimpleDateFormat("mm", Locale.ROOT)
    // set the initial date
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = dialogController.dateTimeItemMillis.value+10800000)
    val timePickerState = rememberTimePickerState(
        initialHour = formatterHour.format(dialogController.dateTimeItemMillis.value).toInt(),
        initialMinute = formatterMinute.format(dialogController.dateTimeItemMillis.value).toInt()
    )

    var showDatePicker by remember {
        mutableStateOf(false)
    }
    var showTimePicker by remember {
        mutableStateOf(false)
    }


    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    dialogController.dateTimeItemMillis.value= datePickerState.selectedDateMillis!!-10800000 + timePickerState.hour*60*60*1000 + timePickerState.minute*60*1000
                }) {
                    Icon(imageVector = Icons.Filled.Check,
                        contentDescription = "Confirm",
                        tint = BlueLight
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDatePicker = false
                }) {
                    Icon(imageVector = Icons.Filled.Close,
                        contentDescription = "Dismiss",
                        tint = BlueLight
                    )
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false
            )
        }


    }

    if(showTimePicker) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DatePickerDialog(
                    modifier = Modifier
                        .fillMaxWidth()
                ,
                onDismissRequest = {
                    showTimePicker = false
                },
                confirmButton = {
                    TextButton(onClick = {
                        showTimePicker = false
                        dialogController.dateTimeItemMillis.value = datePickerState.selectedDateMillis!!-10800000+ timePickerState.hour*60*60*1000+ timePickerState.minute*60*1000
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Confirm",
                            tint = BlueLight
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showTimePicker = false
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Dismiss",
                            tint = BlueLight
                        )
                    }
                }
            ) {

                    TimePicker(
                        modifier = Modifier
                            .padding(top=20.dp)
                            .align(Alignment.CenterHorizontally),
                        state = timePickerState
                    )


            }
        }

    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { showDatePicker = !showDatePicker}) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_calendar_month_24),
                contentDescription = "Pick date",
                tint = BlueLight
            )
        }
        Text(
            //text = "${formatter.format(Date(datePickerState.selectedDateMillis!!))}"
            text = formatterDate.format(Date(datePickerState.selectedDateMillis!!))
        )

        IconButton(onClick = { showTimePicker = !showTimePicker}) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_access_time_24),
                contentDescription = "Pick time",
                tint = BlueLight
            )
        }
        Text(
            text = formatterTime.format(dialogController.dateTimeItemMillis.value)
        )
    }
}
package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.AmberGold
import com.example.ui.theme.DarkSurfaceBorder
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.DarkSurfaceCardHighlight
import com.example.ui.theme.Navy900
import com.example.ui.theme.TextPrimaryLight
import com.example.ui.theme.TextSecondaryLight
import com.example.ui.viewmodel.CustomerFormState

@Composable
fun AddEditCustomerDialog(
    initialState: CustomerFormState,
    onDismiss: () -> Unit,
    onSave: (CustomerFormState) -> Unit
) {
    var form by remember { mutableStateOf(initialState) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .fillMaxHeight(0.88f)
                .testTag("add_edit_customer_dialog"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
            border = androidx.compose.foundation.BorderStroke(1.2.dp, AmberGold.copy(alpha = 0.7f))
        ) {
            Column(
                modifier = Modifier
                    .padding(18.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = if (form.id == null || form.id == 0L) "Add New Customer Record" else "Edit Customer Record",
                    color = AmberGold,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                AdminInputField(label = "Full Name *", value = form.name, onValueChange = { form = form.copy(name = it) })
                AdminInputField(label = "Mobile Number *", value = form.mobile, onValueChange = { form = form.copy(mobile = it) })
                AdminInputField(label = "Email Address", value = form.email, onValueChange = { form = form.copy(email = it) })
                AdminInputField(label = "Pickup / Residential Address", value = form.address, onValueChange = { form = form.copy(address = it) })
                AdminInputField(label = "Destination", value = form.destination, onValueChange = { form = form.copy(destination = it) })
                AdminInputField(label = "Tour Package", value = form.packageChosen, onValueChange = { form = form.copy(packageChosen = it) })
                AdminInputField(label = "Vehicle Allotted", value = form.vehicle, onValueChange = { form = form.copy(vehicle = it) })
                AdminInputField(label = "Travel Date (e.g. 15 Sep 2026)", value = form.travelDate, onValueChange = { form = form.copy(travelDate = it) })
                AdminInputField(label = "Booking Status (CONFIRMED / COMPLETED / PENDING)", value = form.bookingStatus, onValueChange = { form = form.copy(bookingStatus = it) })
                AdminInputField(label = "Special Notes / Preferences", value = form.notes, onValueChange = { form = form.copy(notes = it) })

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f).height(42.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceCardHighlight, contentColor = TextPrimaryLight)
                    ) {
                        Text("CANCEL")
                    }

                    Button(
                        onClick = { onSave(form) },
                        modifier = Modifier.weight(1.3f).height(42.dp).testTag("save_customer_btn"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AmberGold, contentColor = Navy900)
                    ) {
                        Text("SAVE CUSTOMER", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun AdminInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 11.5.sp) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AmberGold,
            unfocusedBorderColor = DarkSurfaceBorder,
            focusedTextColor = TextPrimaryLight,
            unfocusedTextColor = TextPrimaryLight,
            focusedContainerColor = Navy900,
            unfocusedContainerColor = Navy900
        )
    )
}

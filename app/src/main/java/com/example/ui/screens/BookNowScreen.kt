package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AmberGold
import com.example.ui.theme.DarkSurfaceBorder
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.DarkSurfaceCardHighlight
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.Navy800
import com.example.ui.theme.Navy900
import com.example.ui.theme.RoyalBlue
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.TextPrimaryLight
import com.example.ui.theme.TextSecondaryLight
import com.example.ui.theme.WhatsAppGreen
import com.example.ui.viewmodel.TravelViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookNowScreen(
    viewModel: TravelViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val form by viewModel.bookingForm.collectAsState()
    val allPackages = viewModel.allTourPackages
    val allFleet = viewModel.allFleetItems

    var packageDropdownExpanded by remember { mutableStateOf(false) }
    var vehicleDropdownExpanded by remember { mutableStateOf(false) }

    // Real-time price estimator
    val matchedPkg = allPackages.find { it.name.contains(form.tourPackage, ignoreCase = true) }
    val basePricePerPerson = matchedPkg?.startingPrice ?: 5500
    val estimatedTotal = basePricePerPerson * form.passengers

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Navy900)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        // Screen Title Banner
        Column {
            Text(
                text = "BOOK YOUR TOUR & VEHICLE",
                color = AmberGold,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.5.sp
            )
            Text(
                text = "Instant booking confirmation with zero advance booking fees. We coordinate pickup directly.",
                color = TextSecondaryLight,
                fontSize = 11.5.sp
            )
        }

        // Live Price Estimator Sticky Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(12.dp, RoundedCornerShape(16.dp), ambientColor = RoyalBlue)
                .border(1.2.dp, Brush.horizontalGradient(listOf(AmberGold, RoyalBlue)), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Navy800)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "ESTIMATED TOTAL (APPROX)", color = TextSecondaryLight, fontSize = 10.5.sp, fontWeight = FontWeight.Bold)
                    Text(text = "₹$estimatedTotal", color = AmberGold, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
                    Text(text = "for ${form.passengers} travellers • ${form.roomsCount} rooms", color = EmeraldGreen, fontSize = 11.sp)
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = DarkSurfaceCardHighlight,
                    border = androidx.compose.foundation.BorderStroke(1.dp, AmberGold.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = "Pay on Boarding",
                        color = TextPrimaryLight,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // Error message notification
        if (form.errorMessage != null) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = SaffronOrange.copy(alpha = 0.2f),
                border = androidx.compose.foundation.BorderStroke(1.dp, SaffronOrange)
            ) {
                Text(
                    text = "⚠️ ${form.errorMessage}",
                    color = SaffronOrange,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }

        // Booking Form Fields Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
            border = androidx.compose.foundation.BorderStroke(1.dp, DarkSurfaceBorder)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(text = "1. Personal Contact Details", color = AmberGold, fontSize = 13.5.sp, fontWeight = FontWeight.Bold)

                // Customer Name
                OutlinedTextField(
                    value = form.customerName,
                    onValueChange = { viewModel.updateBookingForm { f -> f.copy(customerName = it, errorMessage = null) } },
                    label = { Text("Full Name *") },
                    leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = AmberGold) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("booking_name_input"),
                    colors = formTextFieldColors()
                )

                // Mobile Number
                OutlinedTextField(
                    value = form.mobileNumber,
                    onValueChange = { viewModel.updateBookingForm { f -> f.copy(mobileNumber = it, errorMessage = null) } },
                    label = { Text("10-Digit Mobile Number *") },
                    leadingIcon = { Icon(imageVector = Icons.Default.Phone, contentDescription = null, tint = AmberGold) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("booking_mobile_input"),
                    colors = formTextFieldColors()
                )

                // Email
                OutlinedTextField(
                    value = form.email,
                    onValueChange = { viewModel.updateBookingForm { f -> f.copy(email = it) } },
                    label = { Text("Email Address (Optional)") },
                    leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = AmberGold) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("booking_email_input"),
                    colors = formTextFieldColors()
                )

                Divider(color = DarkSurfaceBorder, thickness = 0.8.dp, modifier = Modifier.padding(vertical = 4.dp))

                Text(text = "2. Travel Route & Dates", color = AmberGold, fontSize = 13.5.sp, fontWeight = FontWeight.Bold)

                // Pickup Location
                OutlinedTextField(
                    value = form.pickupLocation,
                    onValueChange = { viewModel.updateBookingForm { f -> f.copy(pickupLocation = it, errorMessage = null) } },
                    label = { Text("Pickup Location (e.g. Delhi NCR, Haridwar) *") },
                    leadingIcon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = AmberGold) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("booking_pickup_input"),
                    colors = formTextFieldColors()
                )

                // Destination
                OutlinedTextField(
                    value = form.destination,
                    onValueChange = { viewModel.updateBookingForm { f -> f.copy(destination = it, errorMessage = null) } },
                    label = { Text("Destination (e.g. Chopta, Manali, Kedarnath) *") },
                    leadingIcon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = AmberGold) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("booking_dest_input"),
                    colors = formTextFieldColors()
                )

                // Dates Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = form.travelDate,
                        onValueChange = { viewModel.updateBookingForm { f -> f.copy(travelDate = it, errorMessage = null) } },
                        label = { Text("Travel Date *") },
                        placeholder = { Text("e.g. 15 Sep", fontSize = 11.sp, color = TextSecondaryLight) },
                        leadingIcon = { Icon(imageVector = Icons.Default.DateRange, contentDescription = null, tint = AmberGold) },
                        singleLine = true,
                        modifier = Modifier.weight(1f).testTag("booking_date_input"),
                        colors = formTextFieldColors()
                    )

                    OutlinedTextField(
                        value = form.returnDate,
                        onValueChange = { viewModel.updateBookingForm { f -> f.copy(returnDate = it) } },
                        label = { Text("Return Date") },
                        placeholder = { Text("e.g. 19 Sep", fontSize = 11.sp, color = TextSecondaryLight) },
                        singleLine = true,
                        modifier = Modifier.weight(1f).testTag("booking_return_input"),
                        colors = formTextFieldColors()
                    )
                }

                Divider(color = DarkSurfaceBorder, thickness = 0.8.dp, modifier = Modifier.padding(vertical = 4.dp))

                Text(text = "3. Package & Vehicle Selection", color = AmberGold, fontSize = 13.5.sp, fontWeight = FontWeight.Bold)

                // Package Dropdown
                ExposedDropdownMenuBox(
                    expanded = packageDropdownExpanded,
                    onExpandedChange = { packageDropdownExpanded = !packageDropdownExpanded }
                ) {
                    OutlinedTextField(
                        value = form.tourPackage,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Select Tour Package") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = packageDropdownExpanded) },
                        modifier = Modifier.fillMaxWidth().menuAnchor().testTag("booking_package_dropdown"),
                        colors = formTextFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = packageDropdownExpanded,
                        onDismissRequest = { packageDropdownExpanded = false },
                        modifier = Modifier.background(Navy900)
                    ) {
                        allPackages.forEach { pkg ->
                            DropdownMenuItem(
                                text = { Text(text = "${pkg.name} (${pkg.days}D / ₹${pkg.startingPrice})", color = TextPrimaryLight, fontSize = 12.5.sp) },
                                onClick = {
                                    viewModel.updateBookingForm { f -> f.copy(tourPackage = pkg.name, destination = pkg.name.replace(" TOUR", "")) }
                                    packageDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                // Vehicle Dropdown
                ExposedDropdownMenuBox(
                    expanded = vehicleDropdownExpanded,
                    onExpandedChange = { vehicleDropdownExpanded = !vehicleDropdownExpanded }
                ) {
                    OutlinedTextField(
                        value = form.vehicleType,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Select Vehicle Fleet Preference") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = vehicleDropdownExpanded) },
                        modifier = Modifier.fillMaxWidth().menuAnchor().testTag("booking_vehicle_dropdown"),
                        colors = formTextFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = vehicleDropdownExpanded,
                        onDismissRequest = { vehicleDropdownExpanded = false },
                        modifier = Modifier.background(Navy900)
                    ) {
                        allFleet.forEach { veh ->
                            DropdownMenuItem(
                                text = { Text(text = "${veh.name} (${veh.category})", color = TextPrimaryLight, fontSize = 12.5.sp) },
                                onClick = {
                                    viewModel.updateBookingForm { f -> f.copy(vehicleType = veh.name) }
                                    vehicleDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                // Passenger & Room Counters
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Passengers Counter
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Navy800),
                        border = androidx.compose.foundation.BorderStroke(1.dp, DarkSurfaceBorder)
                    ) {
                        Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Passengers", color = TextSecondaryLight, fontSize = 11.sp)
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                IconButton(
                                    onClick = { if (form.passengers > 1) viewModel.updateBookingForm { it.copy(passengers = it.passengers - 1) } },
                                    modifier = Modifier.size(28.dp).clip(CircleShape).background(DarkSurfaceCardHighlight)
                                ) {
                                    Icon(imageVector = Icons.Default.Remove, contentDescription = "Decrease", tint = AmberGold, modifier = Modifier.size(16.dp))
                                }
                                Text(text = "${form.passengers}", color = TextPrimaryLight, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                IconButton(
                                    onClick = { viewModel.updateBookingForm { it.copy(passengers = it.passengers + 1) } },
                                    modifier = Modifier.size(28.dp).clip(CircleShape).background(DarkSurfaceCardHighlight)
                                ) {
                                    Icon(imageVector = Icons.Default.Add, contentDescription = "Increase", tint = AmberGold, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }

                    // Rooms Counter
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Navy800),
                        border = androidx.compose.foundation.BorderStroke(1.dp, DarkSurfaceBorder)
                    ) {
                        Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Rooms", color = TextSecondaryLight, fontSize = 11.sp)
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                IconButton(
                                    onClick = { if (form.roomsCount > 1) viewModel.updateBookingForm { it.copy(roomsCount = it.roomsCount - 1) } },
                                    modifier = Modifier.size(28.dp).clip(CircleShape).background(DarkSurfaceCardHighlight)
                                ) {
                                    Icon(imageVector = Icons.Default.Remove, contentDescription = "Decrease", tint = AmberGold, modifier = Modifier.size(16.dp))
                                }
                                Text(text = "${form.roomsCount}", color = TextPrimaryLight, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                IconButton(
                                    onClick = { viewModel.updateBookingForm { it.copy(roomsCount = it.roomsCount + 1) } },
                                    modifier = Modifier.size(28.dp).clip(CircleShape).background(DarkSurfaceCardHighlight)
                                ) {
                                    Icon(imageVector = Icons.Default.Add, contentDescription = "Increase", tint = AmberGold, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }

                // Special Requirements
                OutlinedTextField(
                    value = form.specialRequirements,
                    onValueChange = { viewModel.updateBookingForm { f -> f.copy(specialRequirements = it) } },
                    label = { Text("Special Requests / Promo Codes") },
                    placeholder = { Text("e.g. Child seat, vegetarian meals, wheelchair assistance", fontSize = 11.sp, color = TextSecondaryLight) },
                    minLines = 2,
                    maxLines = 4,
                    modifier = Modifier.fillMaxWidth().testTag("booking_req_input"),
                    colors = formTextFieldColors()
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Submit Action Button
                Button(
                    onClick = { viewModel.submitBooking(context) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("submit_booking_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AmberGold, contentColor = Navy900),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Icon(imageVector = Icons.Default.ShoppingBag, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "CONFIRM & GENERATE TICKET", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp)
                }
            }
        }

        // Assistance Contact Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Navy800),
            border = androidx.compose.foundation.BorderStroke(1.dp, DarkSurfaceBorder)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Need Booking Help?", color = TextPrimaryLight, fontSize = 12.5.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Call 9891719744 for immediate assistance", color = AmberGold, fontSize = 11.sp)
                }

                Button(
                    onClick = { viewModel.callPhone(context) },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue, contentColor = Color.White)
                ) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("CALL", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(90.dp))
    }
}

@Composable
private fun formTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = AmberGold,
    unfocusedBorderColor = DarkSurfaceBorder,
    focusedTextColor = TextPrimaryLight,
    unfocusedTextColor = TextPrimaryLight,
    focusedContainerColor = Navy900,
    unfocusedContainerColor = Navy900
)

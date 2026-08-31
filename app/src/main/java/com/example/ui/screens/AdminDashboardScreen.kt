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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BookingEntity
import com.example.data.model.ContactEnquiryEntity
import com.example.data.model.CustomerEntity
import com.example.ui.theme.AmberGold
import com.example.ui.theme.DarkSurfaceBorder
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.DarkSurfaceCardHighlight
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.Navy700
import com.example.ui.theme.Navy800
import com.example.ui.theme.Navy900
import com.example.ui.theme.RoyalBlue
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.TextPrimaryLight
import com.example.ui.theme.TextSecondaryLight
import com.example.ui.viewmodel.TravelViewModel

@Composable
fun AdminDashboardScreen(
    viewModel: TravelViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val bookings by viewModel.bookingsList.collectAsState()
    val customers by viewModel.searchFilteredCustomers.collectAsState()
    val enquiries by viewModel.enquiriesList.collectAsState()
    val customerQuery by viewModel.customerSearchQuery.collectAsState()

    var selectedAdminTab by remember { mutableIntStateOf(0) }
    val adminTabs = listOf("Bookings (${bookings.size})", "Customers (${customers.size})", "Enquiries (${enquiries.size})")

    val confirmedCount = bookings.count { it.status.equals("CONFIRMED", ignoreCase = true) }
    val completedCount = bookings.count { it.status.equals("COMPLETED", ignoreCase = true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Navy900)
    ) {
        // Dashboard Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Navy800)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "ADMIN & CRM DASHBOARD",
                        color = AmberGold,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "Manage live bookings, customer database and leads",
                        color = TextSecondaryLight,
                        fontSize = 11.sp
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = EmeraldGreen.copy(alpha = 0.2f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldGreen)
                ) {
                    Text(
                        text = "● Live DB Active",
                        color = EmeraldGreen,
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }
            }

            // Key Metrics Summary 4-Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                MetricCard("Bookings", "${bookings.size}", RoyalBlue, Modifier.weight(1f))
                MetricCard("Confirmed", "$confirmedCount", EmeraldGreen, Modifier.weight(1f))
                MetricCard("Customers", "${customers.size}", AmberGold, Modifier.weight(1f))
                MetricCard("Enquiries", "${enquiries.size}", SaffronOrange, Modifier.weight(1f))
            }

            // Admin Sub-Tabs
            TabRow(
                selectedTabIndex = selectedAdminTab,
                containerColor = Navy800,
                contentColor = AmberGold,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedAdminTab]),
                        color = AmberGold,
                        height = 3.dp
                    )
                }
            ) {
                adminTabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedAdminTab == index,
                        onClick = { selectedAdminTab = index },
                        text = {
                            Text(
                                text = title,
                                fontSize = 11.5.sp,
                                fontWeight = if (selectedAdminTab == index) FontWeight.Bold else FontWeight.Medium,
                                color = if (selectedAdminTab == index) AmberGold else TextSecondaryLight
                            )
                        }
                    )
                }
            }
        }

        // Active Tab Content
        when (selectedAdminTab) {
            0 -> BookingsManagementView(
                bookings = bookings,
                onStatusChange = { id, st -> viewModel.updateBookingStatus(id, st) },
                onDelete = { viewModel.deleteBooking(it) },
                onCall = { viewModel.callPhone(context, it) }
            )
            1 -> CustomersManagementView(
                customers = customers,
                searchQuery = customerQuery,
                onSearchChange = { viewModel.setCustomerSearchQuery(it) },
                onAddCustomer = { viewModel.openAddCustomerDialog() },
                onEditCustomer = { viewModel.openEditCustomerDialog(it) },
                onDeleteCustomer = { viewModel.deleteCustomer(it) },
                onCallCustomer = { viewModel.callPhone(context, it) }
            )
            2 -> EnquiriesManagementView(
                enquiries = enquiries,
                onMarkResolved = { viewModel.markEnquiryResolved(it) },
                onCall = { viewModel.callPhone(context, it) }
            )
        }
    }
}

@Composable
private fun MetricCard(label: String, value: String, accentColor: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
        border = androidx.compose.foundation.BorderStroke(0.8.dp, DarkSurfaceBorder)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, color = accentColor, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
            Text(text = label, color = TextSecondaryLight, fontSize = 9.5.sp)
        }
    }
}

// 1. Bookings Management Sub-View
@Composable
private fun BookingsManagementView(
    bookings: List<BookingEntity>,
    onStatusChange: (Long, String) -> Unit,
    onDelete: (Long) -> Unit,
    onCall: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { Spacer(modifier = Modifier.height(8.dp)) }

        if (bookings.isEmpty()) {
            item {
                Text(
                    text = "No bookings found in database. New bookings submitted by customers will appear here automatically.",
                    color = TextSecondaryLight,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
            }
        }

        items(bookings, key = { it.id }) { b ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(6.dp, RoundedCornerShape(14.dp))
                    .border(1.dp, DarkSurfaceBorder, RoundedCornerShape(14.dp))
                    .testTag("admin_booking_${b.id}"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Surface(shape = RoundedCornerShape(6.dp), color = RoyalBlue) {
                                Text(text = b.bookingId, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                            Text(text = b.customerName, color = TextPrimaryLight, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = when (b.status.uppercase()) {
                                "CONFIRMED" -> EmeraldGreen.copy(alpha = 0.2f)
                                "COMPLETED" -> RoyalBlue.copy(alpha = 0.2f)
                                else -> SaffronOrange.copy(alpha = 0.2f)
                            }
                        ) {
                            Text(
                                text = b.status,
                                color = when (b.status.uppercase()) {
                                    "CONFIRMED" -> EmeraldGreen
                                    "COMPLETED" -> RoyalBlue
                                    else -> SaffronOrange
                                },
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Text(text = "📍 ${b.tourPackage} • ${b.pickupLocation} ➔ ${b.destination}", color = AmberGold, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Text(text = "🚐 ${b.vehicleType} • ${b.passengers} Passengers • Date: ${b.travelDate}", color = TextSecondaryLight, fontSize = 11.sp)
                    Text(text = "💰 Est. ₹${b.totalEstimatedPrice.toInt()} • Notes: ${b.specialRequirements}", color = TextSecondaryLight, fontSize = 11.sp)

                    Divider(color = DarkSurfaceBorder, thickness = 0.8.dp, modifier = Modifier.padding(vertical = 4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Button(
                                onClick = { onStatusChange(b.id, "CONFIRMED") },
                                modifier = Modifier.height(30.dp),
                                shape = RoundedCornerShape(6.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen, contentColor = Color.White)
                            ) {
                                Text("CONFIRM", fontSize = 9.5.sp, fontWeight = FontWeight.Bold)
                            }
                            Button(
                                onClick = { onStatusChange(b.id, "COMPLETED") },
                                modifier = Modifier.height(30.dp),
                                shape = RoundedCornerShape(6.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue, contentColor = Color.White)
                            ) {
                                Text("COMPLETE", fontSize = 9.5.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            IconButton(onClick = { onCall(b.mobileNumber) }, modifier = Modifier.size(30.dp).clip(CircleShape).background(Navy800)) {
                                Icon(imageVector = Icons.Default.Call, contentDescription = "Call", tint = AmberGold, modifier = Modifier.size(14.dp))
                            }
                            IconButton(onClick = { onDelete(b.id) }, modifier = Modifier.size(30.dp).clip(CircleShape).background(Navy800)) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = SaffronOrange, modifier = Modifier.size(14.dp))
                            }
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(90.dp)) }
    }
}

// 2. Customers Management Sub-View (CRM)
@Composable
private fun CustomersManagementView(
    customers: List<CustomerEntity>,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onAddCustomer: () -> Unit,
    onEditCustomer: (CustomerEntity) -> Unit,
    onDeleteCustomer: (CustomerEntity) -> Unit,
    onCallCustomer: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    placeholder = { Text("Search customer name, mobile, destination...", fontSize = 11.5.sp, color = TextSecondaryLight) },
                    leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = AmberGold) },
                    singleLine = true,
                    modifier = Modifier.weight(1f).testTag("customer_search_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AmberGold,
                        unfocusedBorderColor = DarkSurfaceBorder,
                        focusedTextColor = TextPrimaryLight,
                        unfocusedTextColor = TextPrimaryLight,
                        focusedContainerColor = Navy900,
                        unfocusedContainerColor = Navy900
                    )
                )

                Button(
                    onClick = onAddCustomer,
                    modifier = Modifier.height(48.dp).testTag("add_customer_btn"),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AmberGold, contentColor = Navy900)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("ADD", fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        items(customers, key = { it.id }) { c ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(12.dp))
                    .border(0.8.dp, DarkSurfaceBorder, RoundedCornerShape(12.dp))
                    .testTag("customer_card_${c.id}"),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = c.name, color = TextPrimaryLight, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Surface(shape = RoundedCornerShape(6.dp), color = Navy800) {
                            Text(text = c.bookingStatus, color = AmberGold, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }

                    Text(text = "📞 ${c.mobile} • ✉️ ${c.email}", color = TextSecondaryLight, fontSize = 11.5.sp)
                    Text(text = "🗺️ Destination: ${c.destination} (${c.packageChosen})", color = AmberGold, fontSize = 11.5.sp, fontWeight = FontWeight.SemiBold)
                    Text(text = "🚐 Vehicle: ${c.vehicle} • Travel Date: ${c.travelDate}", color = TextSecondaryLight, fontSize = 11.sp)
                    if (c.notes.isNotBlank()) {
                        Text(text = "📝 Notes: ${c.notes}", color = TextSecondaryLight, fontSize = 10.5.sp)
                    }

                    Divider(color = DarkSurfaceBorder, thickness = 0.6.dp, modifier = Modifier.padding(vertical = 4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { onCallCustomer(c.mobile) },
                            modifier = Modifier.height(28.dp),
                            shape = RoundedCornerShape(6.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue, contentColor = TextPrimaryLight)
                        ) {
                            Icon(imageVector = Icons.Default.Call, contentDescription = null, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("CALL", fontSize = 9.5.sp)
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Button(
                            onClick = { onEditCustomer(c) },
                            modifier = Modifier.height(28.dp),
                            shape = RoundedCornerShape(6.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceCardHighlight, contentColor = AmberGold)
                        ) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("EDIT", fontSize = 9.5.sp)
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        IconButton(onClick = { onDeleteCustomer(c) }, modifier = Modifier.size(28.dp)) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = SaffronOrange, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(90.dp)) }
    }
}

// 3. Enquiries Management Sub-View
@Composable
private fun EnquiriesManagementView(
    enquiries: List<ContactEnquiryEntity>,
    onMarkResolved: (Long) -> Unit,
    onCall: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item { Spacer(modifier = Modifier.height(8.dp)) }

        if (enquiries.isEmpty()) {
            item {
                Text(
                    text = "No enquiries in inbox. Website contact forms will appear here.",
                    color = TextSecondaryLight,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
            }
        }

        items(enquiries, key = { it.id }) { e ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(12.dp))
                    .border(0.8.dp, DarkSurfaceBorder, RoundedCornerShape(12.dp))
                    .testTag("enquiry_card_${e.id}"),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = e.name, color = TextPrimaryLight, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (e.isResolved) EmeraldGreen.copy(alpha = 0.2f) else SaffronOrange.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = if (e.isResolved) "RESOLVED" else "NEW LEAD",
                                color = if (e.isResolved) EmeraldGreen else SaffronOrange,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Text(text = "📞 ${e.mobile} • ✉️ ${e.email.ifBlank { "N/A" }}", color = AmberGold, fontSize = 11.5.sp)
                    Text(text = "\"${e.message}\"", color = TextSecondaryLight, fontSize = 12.sp, lineHeight = 16.sp)

                    Divider(color = DarkSurfaceBorder, thickness = 0.6.dp, modifier = Modifier.padding(vertical = 4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (!e.isResolved) {
                            Button(
                                onClick = { onMarkResolved(e.id) },
                                modifier = Modifier.height(28.dp),
                                shape = RoundedCornerShape(6.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen, contentColor = Color.White)
                            ) {
                                Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("MARK RESOLVED", fontSize = 9.5.sp)
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                        }

                        Button(
                            onClick = { onCall(e.mobile) },
                            modifier = Modifier.height(28.dp),
                            shape = RoundedCornerShape(6.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue, contentColor = TextPrimaryLight)
                        ) {
                            Icon(imageVector = Icons.Default.Call, contentDescription = null, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("CALL", fontSize = 9.5.sp)
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(90.dp)) }
    }
}

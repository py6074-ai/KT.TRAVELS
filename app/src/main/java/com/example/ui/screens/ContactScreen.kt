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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.ui.theme.Navy800
import com.example.ui.theme.Navy900
import com.example.ui.theme.RoyalBlue
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.TextPrimaryLight
import com.example.ui.theme.TextSecondaryLight
import com.example.ui.theme.WhatsAppGreen
import com.example.ui.viewmodel.TravelViewModel

@Composable
fun ContactScreen(
    viewModel: TravelViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Navy900)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        // Title Header
        Column {
            Text(
                text = "CONTACT & 24/7 HELPLINE",
                color = AmberGold,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.5.sp
            )
            Text(
                text = "Get in touch for custom holiday itineraries, group bookings or corporate offsites",
                color = TextSecondaryLight,
                fontSize = 11.5.sp
            )
        }

        // Direct Touchpoints Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(12.dp, RoundedCornerShape(18.dp), ambientColor = RoyalBlue)
                .border(1.2.dp, Brush.horizontalGradient(listOf(AmberGold, RoyalBlue)), RoundedCornerShape(18.dp)),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = "KHUSHI TOUR & TRAVELS", color = AmberGold, fontSize = 16.sp, fontWeight = FontWeight.Bold)

                // Phone
                ContactRowItem(
                    icon = Icons.Default.Call,
                    iconTint = AmberGold,
                    title = "Customer Helpline / Mobile",
                    value = "9891719744 (24x7 Active)",
                    onClick = { viewModel.callPhone(context) }
                )

                Divider(color = DarkSurfaceBorder, thickness = 0.8.dp)

                // WhatsApp
                ContactRowItem(
                    icon = Icons.Default.Chat,
                    iconTint = WhatsAppGreen,
                    title = "Instant WhatsApp Chat",
                    value = "+91 9891719744 (Immediate Response)",
                    onClick = { viewModel.openWhatsApp(context) }
                )

                Divider(color = DarkSurfaceBorder, thickness = 0.8.dp)

                // Email
                ContactRowItem(
                    icon = Icons.Default.Email,
                    iconTint = RoyalBlue,
                    title = "Official Email",
                    value = "kttravels@gmail.com",
                    onClick = { viewModel.sendEmail(context) }
                )

                Divider(color = DarkSurfaceBorder, thickness = 0.8.dp)

                // Address & Hours
                ContactRowItem(
                    icon = Icons.Default.LocationOn,
                    iconTint = SaffronOrange,
                    title = "Head Office & Fleet Yard",
                    value = "Main Road, Sector 62 / Connaught Place Hub, Delhi NCR - 110001",
                    onClick = {}
                )
            }
        }

        // Send Enquiry Form Card
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
                Text(text = "Send Us a Quick Message", color = AmberGold, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text(text = "Our travel coordinator will call you back within 15 minutes.", color = TextSecondaryLight, fontSize = 11.5.sp)

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Your Full Name *") },
                    leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = AmberGold) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("enquiry_name_input"),
                    colors = contactTextFieldColors()
                )

                OutlinedTextField(
                    value = mobile,
                    onValueChange = { mobile = it },
                    label = { Text("10-Digit Mobile Number *") },
                    leadingIcon = { Icon(imageVector = Icons.Default.Phone, contentDescription = null, tint = AmberGold) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("enquiry_mobile_input"),
                    colors = contactTextFieldColors()
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address") },
                    leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = AmberGold) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("enquiry_email_input"),
                    colors = contactTextFieldColors()
                )

                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    label = { Text("How can we help? (Destination, dates, passengers)") },
                    minLines = 3,
                    maxLines = 5,
                    modifier = Modifier.fillMaxWidth().testTag("enquiry_message_input"),
                    colors = contactTextFieldColors()
                )

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = {
                        viewModel.submitContactEnquiry(name, mobile, email, message, context)
                        name = ""
                        mobile = ""
                        email = ""
                        message = ""
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("submit_enquiry_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AmberGold, contentColor = Navy900),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Icon(imageVector = Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("SEND ENQUIRY", fontWeight = FontWeight.ExtraBold, fontSize = 12.5.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(90.dp))
    }
}

@Composable
private fun ContactRowItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    title: String,
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Navy800),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(18.dp))
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, color = TextSecondaryLight, fontSize = 11.sp)
            Text(text = value, color = TextPrimaryLight, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun contactTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = AmberGold,
    unfocusedBorderColor = DarkSurfaceBorder,
    focusedTextColor = TextPrimaryLight,
    unfocusedTextColor = TextPrimaryLight,
    focusedContainerColor = Navy900,
    unfocusedContainerColor = Navy900
)

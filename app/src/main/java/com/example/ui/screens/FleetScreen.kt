package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.Animated3DVehicleCard
import com.example.ui.theme.AmberGold
import com.example.ui.theme.DarkSurfaceBorder
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.Navy800
import com.example.ui.theme.Navy900
import com.example.ui.theme.RoyalBlue
import com.example.ui.theme.TextPrimaryLight
import com.example.ui.theme.TextSecondaryLight
import com.example.ui.viewmodel.TravelViewModel

@Composable
fun FleetScreen(
    viewModel: TravelViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val fleetItems = viewModel.allFleetItems

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Navy900)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "OUR PREMIUM VEHICLE FLEET",
                color = AmberGold,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.5.sp
            )
            Text(
                text = "Modern, comfortable, sanitized and GPS-tracked luxury tourist vehicles for hill and highway travel across India",
                color = TextSecondaryLight,
                fontSize = 11.5.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Fleet Safety Assurance Banner
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Navy800),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkSurfaceBorder)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.VerifiedUser, contentDescription = null, tint = AmberGold, modifier = Modifier.size(24.dp))
                    Column {
                        Text(text = "All-India Commercial Permit & Hill Certified", color = TextPrimaryLight, fontSize = 12.5.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Pushback reclining seats, air suspension, sanitized AC cabin & luggage rack included.", color = TextSecondaryLight, fontSize = 11.sp)
                    }
                }
            }
        }

        items(fleetItems, key = { it.id }) { vehicle ->
            Animated3DVehicleCard(
                vehicle = vehicle,
                onBookVehicle = { viewModel.prepareBookingForVehicle(it) },
                onGetQuote = { viewModel.prepareBookingForVehicle(it) },
                onWhatsApp = {
                    viewModel.openWhatsApp(context, "Hello Khushi Tour & Travels, I want to book / get quotation for ${vehicle.name} (${vehicle.category}).")
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}

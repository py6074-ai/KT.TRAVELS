package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DestinationSpot
import com.example.ui.components.Interactive3DMap
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
import com.example.ui.viewmodel.AppNavTab
import com.example.ui.viewmodel.TravelViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DestinationsScreen(
    viewModel: TravelViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val destinations = viewModel.allDestinationSpots
    val selectedSpot by viewModel.selectedDestinationSpot.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Navy900)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "3D INTERACTIVE DESTINATION MAP",
                color = AmberGold,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.5.sp
            )
            Text(
                text = "Explore 11 top North Indian & Himalayan destinations connected by Khushi Tour & Travels",
                color = TextSecondaryLight,
                fontSize = 11.5.sp
            )
            Spacer(modifier = Modifier.height(10.dp))

            // 3D Map
            Interactive3DMap(
                destinations = destinations,
                selectedSpot = selectedSpot,
                onSelectSpot = { viewModel.selectDestinationSpot(it) },
                onBookSpot = { spot ->
                    val matchedPkg = viewModel.allTourPackages.find { it.name.contains(spot.name, ignoreCase = true) }
                    if (matchedPkg != null) {
                        viewModel.prepareBookingForPackage(matchedPkg)
                    } else {
                        viewModel.navigateTo(AppNavTab.BOOK_NOW)
                    }
                },
                onWhatsAppSpot = { spot ->
                    viewModel.openWhatsApp(context, "Hello, I want to enquire about ${spot.name}, ${spot.stateOrRegion}.")
                }
            )

            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "DESTINATION TRAVEL GUIDES",
                color = AmberGold,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        items(destinations, key = { it.id }) { spot ->
            DestinationGuideCard(
                spot = spot,
                onSelect = { viewModel.selectDestinationSpot(spot) },
                onBook = {
                    val matched = viewModel.allTourPackages.find { it.name.contains(spot.name, ignoreCase = true) }
                    if (matched != null) {
                        viewModel.prepareBookingForPackage(matched)
                    } else {
                        viewModel.updateBookingForm { it.copy(destination = spot.name) }
                        viewModel.navigateTo(AppNavTab.BOOK_NOW)
                    }
                },
                onWhatsApp = {
                    viewModel.openWhatsApp(context, "Hello, I want to plan a tour to ${spot.name}, ${spot.stateOrRegion}.")
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DestinationGuideCard(
    spot: DestinationSpot,
    onSelect: () -> Unit,
    onBook: () -> Unit,
    onWhatsApp: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(16.dp), ambientColor = RoyalBlue)
            .border(1.dp, DarkSurfaceBorder, RoundedCornerShape(16.dp))
            .clickable { onSelect() }
            .testTag("dest_guide_${spot.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = AmberGold, modifier = Modifier.size(18.dp))
                    Text(text = spot.name, color = TextPrimaryLight, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(text = "(${spot.stateOrRegion})", color = TextSecondaryLight, fontSize = 12.sp)
                }

                Surface(shape = RoundedCornerShape(6.dp), color = Navy800, border = androidx.compose.foundation.BorderStroke(0.8.dp, DarkSurfaceBorder)) {
                    Text(text = spot.altitudeOrTag, color = EmeraldGreen, fontSize = 10.5.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(text = spot.shortDescription, color = TextSecondaryLight, fontSize = 12.5.sp, lineHeight = 16.sp)

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = null, tint = AmberGold, modifier = Modifier.size(13.dp))
                Text(text = "Best Season: ${spot.bestTimeToVisit}", color = TextPrimaryLight, fontSize = 11.5.sp, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(8.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                spot.mainAttractions.forEach { att ->
                    Surface(shape = RoundedCornerShape(6.dp), color = DarkSurfaceCardHighlight) {
                        Text(text = "📍 $att", color = TextSecondaryLight, fontSize = 10.5.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onBook,
                    modifier = Modifier.weight(1.2f).height(38.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AmberGold, contentColor = Navy900)
                ) {
                    Icon(imageVector = Icons.Default.ShoppingBag, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("BOOK TOUR", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                }

                Button(
                    onClick = onWhatsApp,
                    modifier = Modifier.weight(1f).height(38.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen, contentColor = Color.White)
                ) {
                    Icon(imageVector = Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("WHATSAPP", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                }
            }
        }
    }
}

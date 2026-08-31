package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.TourPackage
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TourPackageDetailDialog(
    tourPackage: TourPackage,
    onDismiss: () -> Unit,
    onBookNow: (TourPackage) -> Unit,
    onCallClick: () -> Unit,
    onWhatsAppClick: (TourPackage) -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.96f)
                .fillMaxHeight(0.92f)
                .border(1.5.dp, Brush.horizontalGradient(listOf(AmberGold, RoyalBlue)), RoundedCornerShape(24.dp))
                .testTag("tour_package_detail_dialog"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Navy900)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header Image Bar with Title & Close
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    Image(
                        painter = painterResource(id = tourPackage.imageDrawableRes),
                        contentDescription = tourPackage.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Gradient Scrim
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Navy900.copy(alpha = 0.4f), Navy900.copy(alpha = 0.95f))
                                )
                            )
                    )

                    // Close Button
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                            .clip(CircleShape)
                            .background(Navy900.copy(alpha = 0.75f))
                            .testTag("dialog_close_btn")
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextPrimaryLight)
                    }

                    // Package Title & Price Badge
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = AmberGold,
                            modifier = Modifier.padding(bottom = 6.dp)
                        ) {
                            Text(
                                text = "${tourPackage.days} DAYS / ${tourPackage.nights} NIGHTS",
                                color = Navy900,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                        Text(
                            text = tourPackage.name,
                            color = TextPrimaryLight,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = "Starting from ₹${tourPackage.startingPrice} per person",
                            color = AmberGold,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Scrollable Content
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // Overview
                    Text(text = "Overview", color = AmberGold, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = tourPackage.overview, color = TextPrimaryLight, fontSize = 13.sp, lineHeight = 18.sp)

                    Spacer(modifier = Modifier.height(14.dp))

                    // Highlights
                    Text(text = "Tour Highlights", color = AmberGold, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    FlowRow(
                        modifier = Modifier.fillMaxWidth().padding(top = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        tourPackage.highlights.forEach { hl ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = DarkSurfaceCardHighlight,
                                border = androidx.compose.foundation.BorderStroke(0.8.dp, DarkSurfaceBorder)
                            ) {
                                Text(
                                    text = "★ $hl",
                                    color = TextPrimaryLight,
                                    fontSize = 11.5.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Day-by-Day Schedule (Itinerary)
                    Text(text = "Day-by-Day Detailed Itinerary", color = AmberGold, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    tourPackage.itinerary.forEach { day ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
                        ) {
                            Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = RoyalBlue
                                    ) {
                                        Text(
                                            text = "DAY ${day.dayNumber}",
                                            color = Color.White,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                    Text(
                                        text = day.title,
                                        color = AmberGold,
                                        fontSize = 13.5.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(text = day.description, color = TextPrimaryLight, fontSize = 12.5.sp, lineHeight = 17.sp)
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "🍽️ Meals: ${day.mealIncluded}", color = TextSecondaryLight, fontSize = 11.sp)
                                    Text(text = "🏨 Stay: ${day.stayLocation}", color = TextSecondaryLight, fontSize = 11.sp)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Trip Logistics Specs
                    Text(text = "Package Inclusions & Details", color = AmberGold, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(DarkSurfaceCard)
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        DetailItemRow(Icons.Default.Place, "Pickup Location:", tourPackage.pickupLocation)
                        DetailItemRow(Icons.Default.LocationOn, "Drop Location:", tourPackage.dropLocation)
                        DetailItemRow(Icons.Default.Hotel, "Accommodation:", tourPackage.hotelsAccommodation)
                        DetailItemRow(Icons.Default.Fastfood, "Meals Included:", tourPackage.meals)
                        DetailItemRow(Icons.Default.DirectionsBus, "Vehicle:", tourPackage.vehicleIncluded)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Inclusions & Exclusions
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "✓ Inclusions", color = EmeraldGreen, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            tourPackage.inclusions.forEach { inc ->
                                Text(text = "• $inc", color = TextPrimaryLight, fontSize = 11.sp, modifier = Modifier.padding(vertical = 2.dp))
                            }
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "✗ Exclusions", color = SaffronOrange, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            tourPackage.exclusions.forEach { exc ->
                                Text(text = "• $exc", color = TextSecondaryLight, fontSize = 11.sp, modifier = Modifier.padding(vertical = 2.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Important Info & Cancellation
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Navy800)
                    ) {
                        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = AmberGold, modifier = Modifier.size(14.dp))
                                Text(text = "Cancellation Policy", color = AmberGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            Text(text = tourPackage.cancellationPolicy, color = TextSecondaryLight, fontSize = 11.5.sp)
                        }
                    }
                }

                // Sticky Bottom Dialog Action Row
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Navy800,
                    border = androidx.compose.foundation.BorderStroke(1.dp, DarkSurfaceBorder)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { onBookNow(tourPackage) },
                            modifier = Modifier.weight(1.3f).height(44.dp).testTag("dialog_book_now_btn"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AmberGold,
                                contentColor = Navy900
                            )
                        ) {
                            Icon(imageVector = Icons.Default.ShoppingBag, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "BOOK NOW", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }

                        Button(
                            onClick = { onWhatsAppClick(tourPackage) },
                            modifier = Modifier.weight(1f).height(44.dp).testTag("dialog_whatsapp_btn"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = WhatsAppGreen,
                                contentColor = Color.White
                            )
                        ) {
                            Icon(imageVector = Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "WHATSAPP", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }

                        Button(
                            onClick = onCallClick,
                            modifier = Modifier.weight(0.9f).height(44.dp).testTag("dialog_call_btn"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = RoyalBlue,
                                contentColor = TextPrimaryLight
                            )
                        ) {
                            Icon(imageVector = Icons.Default.Call, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailItemRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = AmberGold, modifier = Modifier.size(15.dp).padding(top = 2.dp))
        Text(text = label, color = TextSecondaryLight, fontSize = 11.5.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.width(100.dp))
        Text(text = value, color = TextPrimaryLight, fontSize = 12.sp, modifier = Modifier.weight(1f))
    }
}

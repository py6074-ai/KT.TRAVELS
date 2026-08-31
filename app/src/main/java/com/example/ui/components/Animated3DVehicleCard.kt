package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.AirlineSeatReclineNormal
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Luggage
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.RequestQuote
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.VehicleFleetItem
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
fun Animated3DVehicleCard(
    vehicle: VehicleFleetItem,
    onBookVehicle: (VehicleFleetItem) -> Unit,
    onGetQuote: (VehicleFleetItem) -> Unit,
    onWhatsApp: (VehicleFleetItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var tiltX by remember { mutableFloatStateOf(0f) }
    var tiltY by remember { mutableFloatStateOf(0f) }

    // Slow ambient zoom animation
    val infiniteTransition = rememberInfiniteTransition(label = "vehicle_zoom")
    val scaleFactor by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "slow_zoom"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        tiltX = (tiltX + dragAmount.x * 0.08f).coerceIn(-15f, 15f)
                        tiltY = (tiltY + dragAmount.y * 0.08f).coerceIn(-10f, 10f)
                    },
                    onDragEnd = {
                        tiltX = 0f
                        tiltY = 0f
                    }
                )
            }
            .graphicsLayer {
                rotationY = tiltX
                rotationX = -tiltY
                cameraDistance = 14f * density
            }
            .shadow(14.dp, RoundedCornerShape(20.dp), ambientColor = RoyalBlue, spotColor = AmberGold)
            .border(1.2.dp, Brush.horizontalGradient(listOf(DarkSurfaceBorder, AmberGold.copy(alpha = 0.5f))), RoundedCornerShape(20.dp))
            .testTag("vehicle_card_${vehicle.id}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Vehicle Image Box with 3D Slow Zoom & Overlays
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            ) {
                Image(
                    painter = painterResource(id = vehicle.imageDrawableRes),
                    contentDescription = vehicle.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .scale(scaleFactor),
                    contentScale = ContentScale.Crop
                )

                // Top Badges (Category & AC Status)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Navy900.copy(alpha = 0.85f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, AmberGold)
                    ) {
                        Text(
                            text = vehicle.category,
                            color = AmberGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (vehicle.isAC) RoyalBlue.copy(alpha = 0.9f) else SaffronOrange.copy(alpha = 0.9f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = if (vehicle.isAC) Icons.Default.AcUnit else Icons.Default.DirectionsBus,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = if (vehicle.isAC) "AC LUXURY" else "NON-AC",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Bottom Price Pill
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Navy900.copy(alpha = 0.95f))
                            )
                        )
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = vehicle.pricePerKmOrDay,
                            color = AmberGold,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "⭐ Verified Chauffeur",
                            color = EmeraldGreen,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // Details Body
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = vehicle.name,
                    color = TextPrimaryLight,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = vehicle.description,
                    color = TextSecondaryLight,
                    fontSize = 12.5.sp,
                    lineHeight = 17.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Specs Grid
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(DarkSurfaceCardHighlight)
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    VehicleSpecRow(
                        icon = Icons.Default.AirlineSeatReclineNormal,
                        label = "Capacity:",
                        value = vehicle.seatingCapacity
                    )
                    VehicleSpecRow(
                        icon = Icons.Default.Luggage,
                        label = "Luggage:",
                        value = vehicle.luggageCapacity
                    )
                    VehicleSpecRow(
                        icon = Icons.Default.Shield,
                        label = "Driver:",
                        value = vehicle.driverAvailability
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Feature tags
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    vehicle.features.take(4).forEach { feat ->
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Navy800,
                            border = androidx.compose.foundation.BorderStroke(0.8.dp, DarkSurfaceBorder)
                        ) {
                            Text(
                                text = "✓ $feat",
                                color = TextSecondaryLight,
                                fontSize = 10.5.sp,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 3 Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { onBookVehicle(vehicle) },
                        modifier = Modifier
                            .weight(1.2f)
                            .height(42.dp)
                            .testTag("book_vehicle_${vehicle.id}"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AmberGold,
                            contentColor = Navy900
                        )
                    ) {
                        Text(text = "BOOK VEHICLE", fontWeight = FontWeight.Bold, fontSize = 11.5.sp)
                    }

                    OutlinedButton(
                        onClick = { onGetQuote(vehicle) },
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp)
                            .testTag("quote_vehicle_${vehicle.id}"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = AmberGold),
                        border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.horizontalGradient(listOf(AmberGold, SaffronOrange)))
                    ) {
                        Icon(imageVector = Icons.Default.RequestQuote, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "GET QUOTE", fontWeight = FontWeight.SemiBold, fontSize = 11.sp)
                    }

                    Button(
                        onClick = { onWhatsApp(vehicle) },
                        modifier = Modifier
                            .weight(0.9f)
                            .height(42.dp)
                            .testTag("whatsapp_vehicle_${vehicle.id}"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WhatsAppGreen,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(imageVector = Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "WHATSAPP", fontWeight = FontWeight.Bold, fontSize = 10.5.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun VehicleSpecRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = AmberGold,
            modifier = Modifier.size(14.dp)
        )
        Text(
            text = label,
            color = TextSecondaryLight,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            color = TextPrimaryLight,
            fontSize = 11.5.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

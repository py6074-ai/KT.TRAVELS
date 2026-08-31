package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DestinationSpot
import com.example.ui.theme.AmberGold
import com.example.ui.theme.DarkSurfaceBorder
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.DarkSurfaceCardHighlight
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.Navy700
import com.example.ui.theme.Navy800
import com.example.ui.theme.Navy900
import com.example.ui.theme.RoyalBlue
import com.example.ui.theme.RoyalBlueLight
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.SunGold
import com.example.ui.theme.TextPrimaryLight
import com.example.ui.theme.TextSecondaryLight
import com.example.ui.theme.WhatsAppGreen
import kotlin.math.sqrt

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Interactive3DMap(
    destinations: List<DestinationSpot>,
    selectedSpot: DestinationSpot?,
    onSelectSpot: (DestinationSpot?) -> Unit,
    onBookSpot: (DestinationSpot) -> Unit,
    onWhatsAppSpot: (DestinationSpot) -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "map_radar")
    val pulseRadius by infiniteTransition.animateFloat(
        initialValue = 6f,
        targetValue = 26f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulse_radius"
    )
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 0.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulse_alpha"
    )

    Column(modifier = modifier.fillMaxWidth()) {
        // Map Container Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(340.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Navy900)
                .border(1.2.dp, Brush.horizontalGradient(listOf(RoyalBlue, AmberGold)), RoundedCornerShape(20.dp))
                .pointerInput(destinations) {
                    detectTapGestures { tapOffset ->
                        val tapX = tapOffset.x / size.width
                        val tapY = tapOffset.y / size.height

                        // Find closest destination within threshold
                        var closest: DestinationSpot? = null
                        var minDist = Float.MAX_VALUE
                        for (spot in destinations) {
                            val dx = spot.mapX - tapX
                            val dy = spot.mapY - tapY
                            val dist = sqrt(dx * dx + dy * dy)
                            if (dist < 0.12f && dist < minDist) {
                                minDist = dist
                                closest = spot
                            }
                        }
                        if (closest != null) {
                            onSelectSpot(closest)
                        }
                    }
                }
                .testTag("interactive_3d_map")
        ) {
            // Background Canvas (Topographic Contours, Highway Links & 3D Glowing Pins)
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height

                // Topo grid lines
                for (gx in 0..10) {
                    val x = w * (gx / 10f)
                    drawLine(
                        color = RoyalBlue.copy(alpha = 0.08f),
                        start = Offset(x, 0f),
                        end = Offset(x, h),
                        strokeWidth = 1f
                    )
                }
                for (gy in 0..8) {
                    val y = h * (gy / 8f)
                    drawLine(
                        color = RoyalBlue.copy(alpha = 0.08f),
                        start = Offset(0f, y),
                        end = Offset(w, y),
                        strokeWidth = 1f
                    )
                }

                // Delhi NCR Hub (Origin)
                val hubX = w * 0.45f
                val hubY = h * 0.46f

                // Draw Connecting Highway Routes
                for (spot in destinations) {
                    val sx = w * spot.mapX
                    val sy = h * spot.mapY

                    val routePath = Path().apply {
                        moveTo(hubX, hubY)
                        quadraticTo(
                            (hubX + sx) / 2f + (if (sx > hubX) 20f else -20f),
                            (hubY + sy) / 2f,
                            sx,
                            sy
                        )
                    }

                    val isSelected = selectedSpot?.id == spot.id
                    drawPath(
                        path = routePath,
                        color = if (isSelected) AmberGold else RoyalBlueLight.copy(alpha = 0.4f),
                        style = Stroke(
                            width = if (isSelected) 3.5f else 1.8f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 8f), 0f),
                            cap = StrokeCap.Round
                        )
                    )
                }

                // Delhi NCR Central Hub Marker
                drawCircle(
                    brush = Brush.radialGradient(listOf(SunGold, SaffronOrange, Color.Transparent), center = Offset(hubX, hubY), radius = 24f),
                    radius = 24f,
                    center = Offset(hubX, hubY)
                )
                drawCircle(
                    color = Color.White,
                    radius = 6f,
                    center = Offset(hubX, hubY)
                )

                // Draw Destination Pins
                for (spot in destinations) {
                    val sx = w * spot.mapX
                    val sy = h * spot.mapY
                    val isSelected = selectedSpot?.id == spot.id

                    // Radar pulse
                    drawCircle(
                        color = (if (isSelected) AmberGold else RoyalBlueGlow).copy(alpha = pulseAlpha),
                        radius = pulseRadius * (if (isSelected) 1.4f else 1.0f),
                        center = Offset(sx, sy),
                        style = Stroke(width = 2f)
                    )

                    // Pin core
                    drawCircle(
                        color = if (isSelected) AmberGold else RoyalBlue,
                        radius = if (isSelected) 10f else 7f,
                        center = Offset(sx, sy)
                    )
                    drawCircle(
                        color = Color.White,
                        radius = if (isSelected) 4f else 3f,
                        center = Offset(sx, sy)
                    )
                }
            }

            // Top Hub Overlay Label
            Surface(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp),
                shape = RoundedCornerShape(10.dp),
                color = Navy800.copy(alpha = 0.88f),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkSurfaceBorder)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(imageVector = Icons.Default.Navigation, contentDescription = null, tint = AmberGold, modifier = Modifier.size(14.dp))
                    Text(text = "Tap any destination pin to explore", color = TextPrimaryLight, fontSize = 11.5.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            // Quick Destination Selector Pills Bar at Bottom of Map
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Navy900.copy(alpha = 0.95f))
                        )
                    )
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                destinations.take(5).forEach { dest ->
                    val isSelected = selectedSpot?.id == dest.id
                    Surface(
                        modifier = Modifier
                            .clickable { onSelectSpot(dest) }
                            .testTag("map_pill_${dest.id}"),
                        shape = RoundedCornerShape(8.dp),
                        color = if (isSelected) AmberGold else DarkSurfaceCard,
                        border = androidx.compose.foundation.BorderStroke(0.8.dp, if (isSelected) SunGold else DarkSurfaceBorder)
                    ) {
                        Text(
                            text = dest.name,
                            color = if (isSelected) Navy900 else TextPrimaryLight,
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Selected Destination Interactive Detail Card
        AnimatedVisibility(
            visible = selectedSpot != null,
            enter = fadeIn() + slideInVertically(initialOffsetY = { 40 })
        ) {
            selectedSpot?.let { spot ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(12.dp, RoundedCornerShape(18.dp), ambientColor = RoyalBlue)
                        .border(1.2.dp, AmberGold.copy(alpha = 0.6f), RoundedCornerShape(18.dp))
                        .testTag("map_spot_detail_card"),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = AmberGold, modifier = Modifier.size(18.dp))
                                    Text(
                                        text = "${spot.name}, ${spot.stateOrRegion}",
                                        color = AmberGold,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Text(
                                    text = spot.altitudeOrTag,
                                    color = EmeraldGreen,
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            IconButton(
                                onClick = { onSelectSpot(null) },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextSecondaryLight)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = spot.shortDescription,
                            color = TextPrimaryLight,
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = null, tint = AmberGold, modifier = Modifier.size(14.dp))
                            Text(text = "Best Time to Visit: ", color = TextSecondaryLight, fontSize = 11.5.sp, fontWeight = FontWeight.Medium)
                            Text(text = spot.bestTimeToVisit, color = TextPrimaryLight, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = "Key Attractions:", color = TextSecondaryLight, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        FlowRow(
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            spot.mainAttractions.forEach { att ->
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = DarkSurfaceCardHighlight
                                ) {
                                    Text(
                                        text = "📍 $att",
                                        color = TextPrimaryLight,
                                        fontSize = 11.sp,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Action Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { onBookSpot(spot) },
                                modifier = Modifier.weight(1f).height(42.dp).testTag("book_spot_btn"),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AmberGold,
                                    contentColor = Navy900
                                )
                            ) {
                                Icon(imageVector = Icons.Default.ShoppingBag, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "BOOK NOW", fontWeight = FontWeight.Bold, fontSize = 11.5.sp)
                            }

                            Button(
                                onClick = { onWhatsAppSpot(spot) },
                                modifier = Modifier.weight(1f).height(42.dp).testTag("whatsapp_spot_btn"),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = WhatsAppGreen,
                                    contentColor = Color.White
                                )
                            ) {
                                Icon(imageVector = Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "WHATSAPP", fontWeight = FontWeight.Bold, fontSize = 11.5.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
val RoyalBlueGlow = Color(0xFF60A5FA)

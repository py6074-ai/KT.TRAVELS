package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.AmberGold
import com.example.ui.theme.DarkSurfaceBorder
import com.example.ui.theme.DarkSurfaceCard
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
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Animated3DHeroSection(
    onBookTripClick: () -> Unit,
    onExplorePackagesClick: () -> Unit,
    onCallClick: () -> Unit,
    onWhatsAppClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var dragTiltX by remember { mutableFloatStateOf(0f) }
    var dragTiltY by remember { mutableFloatStateOf(0f) }

    val infiniteTransition = rememberInfiniteTransition(label = "hero_3d")
    
    val roadOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "road_dash"
    )

    val busBounce by infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bus_bounce"
    )

    val pinFloat by infiniteTransition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pin_float"
    )

    val cloudShift by infiniteTransition.animateFloat(
        initialValue = -50f,
        targetValue = 50f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "cloud_shift"
    )

    val sunPulse by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sun_pulse"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        dragTiltX = (dragTiltX + dragAmount.x * 0.05f).coerceIn(-12f, 12f)
                        dragTiltY = (dragTiltY + dragAmount.y * 0.05f).coerceIn(-8f, 8f)
                    },
                    onDragEnd = {
                        dragTiltX = 0f
                        dragTiltY = 0f
                    }
                )
            }
            .graphicsLayer {
                rotationY = dragTiltX
                rotationX = -dragTiltY
                cameraDistance = 12f * density
            }
    ) {
        // 3D Canvas Background (Mountains, Highway, Clouds, Birds, Sun)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(460.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height

                // Sky Gradient
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF070B18),
                            Color(0xFF0F1B3B),
                            Color(0xFF1E3A8A),
                            Color(0xFFEA580C).copy(alpha = 0.6f),
                            Color(0xFFF59E0B).copy(alpha = 0.4f)
                        ),
                        startY = 0f,
                        endY = height * 0.65f
                    )
                )

                // Sun & Atmospheric Glow
                val sunCenter = Offset(width * 0.78f, height * 0.28f)
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            SunGold.copy(alpha = 0.9f),
                            AmberGold.copy(alpha = 0.5f),
                            SaffronOrange.copy(alpha = 0.15f),
                            Color.Transparent
                        ),
                        center = sunCenter,
                        radius = 110f * sunPulse
                    ),
                    radius = 110f * sunPulse,
                    center = sunCenter
                )
                drawCircle(
                    color = Color.White.copy(alpha = 0.95f),
                    radius = 24f,
                    center = sunCenter
                )

                // Flying Birds
                drawBird(this, width * 0.35f + cloudShift * 0.3f, height * 0.18f, 18f)
                drawBird(this, width * 0.42f + cloudShift * 0.35f, height * 0.14f, 14f)
                drawBird(this, width * 0.48f + cloudShift * 0.25f, height * 0.20f, 12f)

                // Far Himalayan Mountain Range (Snowy Peaks)
                val farMountainPath = Path().apply {
                    moveTo(0f, height * 0.50f)
                    lineTo(width * 0.15f, height * 0.32f)
                    lineTo(width * 0.30f, height * 0.48f)
                    lineTo(width * 0.52f, height * 0.25f)
                    lineTo(width * 0.68f, height * 0.42f)
                    lineTo(width * 0.85f, height * 0.30f)
                    lineTo(width, height * 0.46f)
                    lineTo(width, height)
                    lineTo(0f, height)
                    close()
                }
                drawPath(
                    path = farMountainPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFE2E8F0), Color(0xFF475569), Color(0xFF1E293B)),
                        startY = height * 0.25f,
                        endY = height * 0.65f
                    )
                )

                // Mid Mountain Ridge (Pine Forest Green & Slate)
                val midMountainPath = Path().apply {
                    moveTo(0f, height * 0.58f)
                    lineTo(width * 0.22f, height * 0.44f)
                    lineTo(width * 0.45f, height * 0.56f)
                    lineTo(width * 0.72f, height * 0.38f)
                    lineTo(width, height * 0.54f)
                    lineTo(width, height)
                    lineTo(0f, height)
                    close()
                }
                drawPath(
                    path = midMountainPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF065F46), Color(0xFF0F172A)),
                        startY = height * 0.38f,
                        endY = height * 0.75f
                    )
                )

                // 3D Perspective Winding Highway Road
                val roadPath = Path().apply {
                    moveTo(width * 0.48f, height * 0.52f) // Horizon vanish point
                    cubicTo(
                        width * 0.45f, height * 0.65f,
                        width * 0.20f, height * 0.80f,
                        -width * 0.1f, height
                    )
                    lineTo(width * 1.1f, height)
                    cubicTo(
                        width * 0.75f, height * 0.82f,
                        width * 0.54f, height * 0.66f,
                        width * 0.52f, height * 0.52f
                    )
                    close()
                }
                drawPath(
                    path = roadPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF334155), Color(0xFF1E293B), Color(0xFF0F172A)),
                        startY = height * 0.52f,
                        endY = height
                    )
                )

                // Road Yellow Edge Lines
                val leftEdge = Path().apply {
                    moveTo(width * 0.48f, height * 0.52f)
                    cubicTo(
                        width * 0.45f, height * 0.65f,
                        width * 0.20f, height * 0.80f,
                        -width * 0.1f, height
                    )
                }
                drawPath(leftEdge, AmberGold, style = Stroke(width = 4f))

                val rightEdge = Path().apply {
                    moveTo(width * 0.52f, height * 0.52f)
                    cubicTo(
                        width * 0.54f, height * 0.66f,
                        width * 0.75f, height * 0.82f,
                        width * 1.1f, height
                    )
                }
                drawPath(rightEdge, AmberGold, style = Stroke(width = 4f))

                // Animated Center Dashed Line
                val steps = 14
                for (i in 0 until steps) {
                    val progress = ((i.toFloat() + (roadOffset / 100f)) % steps) / steps.toFloat()
                    val py = height * 0.52f + progress * progress * (height * 0.48f)
                    val px = width * 0.50f + sin(progress * 3.14f) * (width * 0.10f)
                    val dashLength = 8f + progress * 28f
                    val dashThickness = 2f + progress * 5f

                    drawLine(
                        color = Color.White.copy(alpha = 0.85f),
                        start = Offset(px, py),
                        end = Offset(px + sin(progress * 1.5f) * 6f, py + dashLength),
                        strokeWidth = dashThickness,
                        cap = StrokeCap.Round
                    )
                }
            }

            // Dark vignette overlay at bottom of canvas to blend with content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Transparent, Navy900.copy(alpha = 0.95f)),
                            startY = 200f,
                            endY = 460f * 3f
                        )
                    )
            )
        }

        // Foreground Content & Hero Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Floating Animated Location Pin Badge
            Surface(
                modifier = Modifier
                    .offset { IntOffset(0, pinFloat.toInt()) }
                    .shadow(12.dp, RoundedCornerShape(24.dp), ambientColor = AmberGold, spotColor = AmberGold)
                    .border(1.dp, AmberGold.copy(alpha = 0.6f), RoundedCornerShape(24.dp))
                    .testTag("hero_location_badge"),
                shape = RoundedCornerShape(24.dp),
                color = Navy800.copy(alpha = 0.92f)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Pin",
                        tint = AmberGold,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "Your Journey Starts Here",
                        color = TextPrimaryLight,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star",
                        tint = SaffronOrange,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Main Brand Headline
            Text(
                text = "KHUSHI TOUR & TRAVELS",
                color = AmberGold,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.2.sp,
                modifier = Modifier.shadow(8.dp, ambientColor = AmberGold)
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Subtitle
            Text(
                text = "Travel Beyond Destinations.\nCreate Beautiful Memories.",
                color = TextPrimaryLight,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Additional Brand Pitch
            Text(
                text = "Comfortable Travel • Trusted Service • Amazing Journeys",
                color = TextSecondaryLight,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Prominent 3D Bus Hero Showcase (Uploaded bus image with 3D tilt & bounce)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .offset { IntOffset(0, busBounce.toInt()) }
                    .shadow(16.dp, RoundedCornerShape(20.dp), ambientColor = RoyalBlue, spotColor = AmberGold)
                    .border(1.5.dp, Brush.horizontalGradient(listOf(AmberGold, RoyalBlue, SaffronOrange)), RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.img_bus_urbania),
                        contentDescription = "Khushi Tour & Travels Premium Luxury Bus Fleet",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Card Bottom Info Strip
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Navy900.copy(alpha = 0.95f))
                                )
                            )
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(WhatsAppGreen)
                                    )
                                    Text(
                                        text = "KHUSHI LUXURY FLEET",
                                        color = AmberGold,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.5.sp
                                    )
                                }
                                Text(
                                    text = "Force Urbania & Deluxe Volvo Coaches",
                                    color = TextPrimaryLight,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = RoyalBlue.copy(alpha = 0.85f)
                            ) {
                                Text(
                                    text = "★ 4.9 Rated",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 4 Hero CTA Buttons
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                maxItemsInEachRow = 2
            ) {
                Button(
                    onClick = onBookTripClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(46.dp)
                        .testTag("hero_book_trip_btn"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AmberGold,
                        contentColor = Navy900
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Icon(imageVector = Icons.Default.ShoppingBag, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "BOOK YOUR TRIP", fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
                }

                Button(
                    onClick = onExplorePackagesClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(46.dp)
                        .testTag("hero_explore_packages_btn"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RoyalBlue,
                        contentColor = TextPrimaryLight
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Icon(imageVector = Icons.Default.Explore, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "EXPLORE PACKAGES", fontWeight = FontWeight.Bold, fontSize = 11.5.sp)
                }

                OutlinedButton(
                    onClick = onCallClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp)
                        .testTag("hero_call_now_btn"),
                    shape = RoundedCornerShape(14.dp),
                    border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.horizontalGradient(listOf(AmberGold, SaffronOrange))),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = AmberGold)
                ) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "CALL NOW", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }

                Button(
                    onClick = onWhatsAppClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp)
                        .testTag("hero_whatsapp_btn"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WhatsAppGreen,
                        contentColor = Color.White
                    )
                ) {
                    Icon(imageVector = Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "WHATSAPP US", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}

private fun drawBird(drawScope: DrawScope, x: Float, y: Float, size: Float) {
    val path = Path().apply {
        moveTo(x - size, y)
        quadraticTo(x - size * 0.5f, y - size * 0.6f, x, y)
        quadraticTo(x + size * 0.5f, y - size * 0.6f, x + size, y)
    }
    drawScope.drawPath(path, Color.White.copy(alpha = 0.75f), style = Stroke(width = 2.5f, cap = StrokeCap.Round))
}

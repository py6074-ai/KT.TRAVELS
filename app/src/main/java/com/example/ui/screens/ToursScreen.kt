package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.TourPackage
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ToursScreen(
    viewModel: TravelViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val filteredPackages by viewModel.filteredPackages.collectAsState()
    val searchQuery by viewModel.packageSearchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    val categories = listOf("All", "Himalayan", "Pilgrimage", "Heritage", "International", "Weekend")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Navy900)
    ) {
        // Top Header & Search Bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Navy800)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "ALL TOUR PACKAGES (20)",
                color = AmberGold,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.5.sp
            )
            Text(
                text = "All-inclusive road trips with luxury AC Travellers & hill-expert drivers",
                color = TextSecondaryLight,
                fontSize = 11.5.sp
            )

            // Search TextField
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.setPackageSearchQuery(it) },
                placeholder = { Text("Search by name, state or highlight...", fontSize = 12.sp, color = TextSecondaryLight) },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = AmberGold) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.setPackageSearchQuery("") }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Clear", tint = TextSecondaryLight)
                        }
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("tour_search_input"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AmberGold,
                    unfocusedBorderColor = DarkSurfaceBorder,
                    focusedTextColor = TextPrimaryLight,
                    unfocusedTextColor = TextPrimaryLight,
                    focusedContainerColor = Navy900,
                    unfocusedContainerColor = Navy900
                )
            )

            // Category Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                categories.forEach { cat ->
                    val isSelected = selectedCategory.equals(cat, ignoreCase = true)
                    Surface(
                        modifier = Modifier
                            .clickable { viewModel.setSelectedCategory(cat) }
                            .testTag("category_chip_$cat"),
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) AmberGold else DarkSurfaceCardHighlight,
                        border = if (isSelected) null else androidx.compose.foundation.BorderStroke(0.8.dp, DarkSurfaceBorder)
                    ) {
                        Text(
                            text = cat,
                            color = if (isSelected) Navy900 else TextPrimaryLight,
                            fontSize = 11.5.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // Package List LazyColumn
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Showing ${filteredPackages.size} Available Packages",
                    color = TextSecondaryLight,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            items(filteredPackages, key = { it.id }) { pkg ->
                TourPackageCard(
                    tourPackage = pkg,
                    onViewDetails = { viewModel.selectPackageForDetails(pkg) },
                    onBookNow = { viewModel.prepareBookingForPackage(pkg) },
                    onWhatsApp = {
                        viewModel.openWhatsApp(context, "Hello, I want to enquire about ${pkg.name} (${pkg.days} Days / ${pkg.nights} Nights).")
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(90.dp)) // Sticky bottom bar margin
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TourPackageCard(
    tourPackage: TourPackage,
    onViewDetails: () -> Unit,
    onBookNow: () -> Unit,
    onWhatsApp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(12.dp, RoundedCornerShape(18.dp), ambientColor = RoyalBlue)
            .border(1.2.dp, DarkSurfaceBorder, RoundedCornerShape(18.dp))
            .clickable { onViewDetails() }
            .testTag("tour_card_${tourPackage.id}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Image Banner with Badges
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
            ) {
                Image(
                    painter = painterResource(id = tourPackage.imageDrawableRes),
                    contentDescription = tourPackage.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Category & Duration Badges
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Navy900.copy(alpha = 0.88f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, AmberGold)
                    ) {
                        Text(
                            text = tourPackage.category.uppercase(),
                            color = AmberGold,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = RoyalBlue.copy(alpha = 0.92f)
                    ) {
                        Text(
                            text = "${tourPackage.days} DAYS / ${tourPackage.nights} NIGHTS",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                // Price Tag Bottom Strip
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Navy900.copy(alpha = 0.95f))
                            )
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "All-Inclusive Tour",
                            color = TextSecondaryLight,
                            fontSize = 10.5.sp
                        )
                        Text(
                            text = "₹${tourPackage.startingPrice} / Person",
                            color = AmberGold,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }

            // Info Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                Text(
                    text = tourPackage.name,
                    color = TextPrimaryLight,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = tourPackage.tagLine,
                    color = TextSecondaryLight,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Highlights pills
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    tourPackage.highlights.take(3).forEach { hl ->
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = DarkSurfaceCardHighlight
                        ) {
                            Text(
                                text = "★ $hl",
                                color = TextPrimaryLight,
                                fontSize = 10.5.sp,
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
                        onClick = onBookNow,
                        modifier = Modifier.weight(1.2f).height(40.dp).testTag("book_pkg_btn_${tourPackage.id}"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AmberGold, contentColor = Navy900)
                    ) {
                        Icon(imageVector = Icons.Default.ShoppingBag, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "BOOK NOW", fontWeight = FontWeight.Bold, fontSize = 11.5.sp)
                    }

                    OutlinedButton(
                        onClick = onViewDetails,
                        modifier = Modifier.weight(1f).height(40.dp).testTag("details_pkg_btn_${tourPackage.id}"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = AmberGold),
                        border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.horizontalGradient(listOf(AmberGold, SaffronOrange)))
                    ) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "ITINERARY", fontWeight = FontWeight.SemiBold, fontSize = 11.sp)
                    }

                    Button(
                        onClick = onWhatsApp,
                        modifier = Modifier.weight(0.9f).height(40.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen, contentColor = Color.White)
                    ) {
                        Icon(imageVector = Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "CHAT", fontWeight = FontWeight.Bold, fontSize = 10.5.sp)
                    }
                }
            }
        }
    }
}

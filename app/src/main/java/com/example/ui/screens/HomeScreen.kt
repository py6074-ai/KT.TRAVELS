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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.TourPackage
import com.example.ui.components.Animated3DHeroSection
import com.example.ui.components.Animated3DVehicleCard
import com.example.ui.components.Interactive3DMap
import com.example.ui.theme.AmberGold
import com.example.ui.theme.DarkSurfaceBorder
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.DarkSurfaceCardHighlight
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.Navy800
import com.example.ui.theme.Navy900
import com.example.ui.theme.RoyalBlue
import com.example.ui.theme.RoyalBlueLight
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.SunGold
import com.example.ui.theme.TextPrimaryLight
import com.example.ui.theme.TextSecondaryLight
import com.example.ui.theme.WhatsAppGreen
import com.example.ui.viewmodel.AppNavTab
import com.example.ui.viewmodel.TravelViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    viewModel: TravelViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val allPackages = viewModel.allTourPackages
    val allFleet = viewModel.allFleetItems
    val allOffers = viewModel.allSpecialOffers
    val selectedSpot by viewModel.selectedDestinationSpot.collectAsState()
    val reviews by viewModel.reviewsList.collectAsState()

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Navy900)
            .verticalScroll(scrollState)
    ) {
        // 1. 3D ANIMATED HERO SECTION
        Animated3DHeroSection(
            onBookTripClick = { viewModel.navigateTo(AppNavTab.BOOK_NOW) },
            onExplorePackagesClick = { viewModel.navigateTo(AppNavTab.TOURS) },
            onCallClick = { viewModel.callPhone(context) },
            onWhatsAppClick = { viewModel.openWhatsApp(context) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 2. QUICK TRIP SEARCH & INSTANT BOOKING BAR
        QuickSearchCard(
            onSearch = { from, to ->
                if (to.isNotBlank()) {
                    viewModel.setPackageSearchQuery(to)
                    viewModel.navigateTo(AppNavTab.TOURS)
                } else {
                    viewModel.navigateTo(AppNavTab.TOURS)
                }
            },
            onDirectBook = {
                viewModel.navigateTo(AppNavTab.BOOK_NOW)
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 3. WHY CHOOSE KHUSHI TOUR & TRAVELS (6 PILLARS)
        SectionHeader(
            title = "WHY CHOOSE KHUSHI TOUR & TRAVELS",
            subtitle = "Your Trusted Travel Partner for Safe, Luxurious & Memorable Trips"
        )
        Spacer(modifier = Modifier.height(10.dp))
        WhyChooseUsGrid()

        Spacer(modifier = Modifier.height(26.dp))

        // 4. POPULAR 3D TOUR PACKAGES (CAROUSEL / FEATURED HIGHLIGHTS)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "FEATURED TOUR PACKAGES",
                    color = AmberGold,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "Handcrafted all-inclusive Himalayan & Heritage itineraries",
                    color = TextSecondaryLight,
                    fontSize = 11.5.sp
                )
            }
            Text(
                text = "View All (20) →",
                color = RoyalBlueLight,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable { viewModel.navigateTo(AppNavTab.TOURS) }
                    .padding(4.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Horizontal Carousel of Featured Tour Packages
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            allPackages.take(6).forEach { pkg ->
                FeaturedPackageCard(
                    tourPackage = pkg,
                    onViewDetails = { viewModel.selectPackageForDetails(pkg) },
                    onBookNow = { viewModel.prepareBookingForPackage(pkg) }
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // 5. INTERACTIVE 3D DESTINATION MAP
        SectionHeader(
            title = "EXPLORE DESTINATIONS ON 3D MAP",
            subtitle = "Tap any sacred or Himalayan spot to view distances, best seasons and routes"
        )
        Spacer(modifier = Modifier.height(12.dp))
        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            Interactive3DMap(
                destinations = viewModel.allDestinationSpots,
                selectedSpot = selectedSpot,
                onSelectSpot = { viewModel.selectDestinationSpot(it) },
                onBookSpot = { spot ->
                    val matchedPkg = allPackages.find { it.name.contains(spot.name, ignoreCase = true) }
                    if (matchedPkg != null) {
                        viewModel.prepareBookingForPackage(matchedPkg)
                    } else {
                        viewModel.navigateTo(AppNavTab.BOOK_NOW)
                    }
                },
                onWhatsAppSpot = { spot ->
                    viewModel.openWhatsApp(context, "Hello, I want to enquire about tour packages for ${spot.name}, ${spot.stateOrRegion}.")
                }
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        // 6. OUR LUXURY FLEET (FEATURING UPLOADED BUS PHOTOS)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "OUR LUXURY FLEET",
                    color = AmberGold,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "Force Urbania, Deluxe Volvo Buses & Maharaja Travellers",
                    color = TextSecondaryLight,
                    fontSize = 11.5.sp
                )
            }
            Text(
                text = "View Fleet →",
                color = RoyalBlueLight,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable { viewModel.navigateTo(AppNavTab.FLEET) }
                    .padding(4.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            allFleet.take(3).forEach { vehicle ->
                Animated3DVehicleCard(
                    vehicle = vehicle,
                    onBookVehicle = { viewModel.prepareBookingForVehicle(it) },
                    onGetQuote = { viewModel.prepareBookingForVehicle(it) },
                    onWhatsApp = {
                        viewModel.openWhatsApp(context, "Hello, I want to enquire about hiring ${vehicle.name} (${vehicle.category}).")
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // 7. SPECIAL OFFERS & PROMOS BANNER
        SectionHeader(
            title = "LIMITED TIME SPECIAL OFFERS",
            subtitle = "Exclusive seasonal discounts on North India & pilgrimage holiday packages"
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            allOffers.take(4).forEach { offer ->
                HomeOfferCard(
                    offer = offer,
                    onClaim = {
                        viewModel.updateBookingForm { it.copy(specialRequirements = "Claimed Offer Promo Code: ${offer.code}") }
                        viewModel.navigateTo(AppNavTab.BOOK_NOW)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // 8. VERIFIED CUSTOMER REVIEWS
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "CUSTOMER TESTIMONIALS",
                    color = AmberGold,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "Rated 4.9/5 by 12,500+ happy travellers",
                    color = TextSecondaryLight,
                    fontSize = 11.5.sp
                )
            }
            Button(
                onClick = { viewModel.setWriteReviewOpen(true) },
                modifier = Modifier.height(36.dp).testTag("write_review_btn_home"),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue, contentColor = TextPrimaryLight)
            ) {
                Icon(imageVector = Icons.Default.RateReview, contentDescription = null, modifier = Modifier.size(13.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Add Review", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            reviews.take(3).forEach { rev ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
                    border = androidx.compose.foundation.BorderStroke(1.dp, DarkSurfaceBorder)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = rev.customerName, color = TextPrimaryLight, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                Text(text = rev.destination, color = AmberGold, fontSize = 11.5.sp, fontWeight = FontWeight.SemiBold)
                            }
                            Row {
                                for (i in 1..rev.rating) {
                                    Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = SaffronOrange, modifier = Modifier.size(14.dp))
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "\"${rev.review}\"", color = TextSecondaryLight, fontSize = 12.5.sp, lineHeight = 17.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // 9. INSTANT CONTACT & HOTLINE FOOTER CARD
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .shadow(16.dp, RoundedCornerShape(20.dp), ambientColor = RoyalBlue)
                .border(1.2.dp, Brush.horizontalGradient(listOf(AmberGold, RoyalBlue)), RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Ready to Plan Your Dream Journey?",
                    color = AmberGold,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Speak with our friendly travel coordinators. We are available 24 hours a day, 7 days a week.",
                    color = TextPrimaryLight,
                    fontSize = 12.5.sp,
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { viewModel.callPhone(context) },
                        modifier = Modifier.weight(1f).height(44.dp).testTag("home_footer_call_btn"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue, contentColor = TextPrimaryLight)
                    ) {
                        Icon(imageVector = Icons.Default.Call, contentDescription = null, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("9891719744", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }

                    Button(
                        onClick = { viewModel.openWhatsApp(context) },
                        modifier = Modifier.weight(1f).height(44.dp).testTag("home_footer_whatsapp_btn"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen, contentColor = Color.White)
                    ) {
                        Icon(imageVector = Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("WhatsApp Us", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = AmberGold, modifier = Modifier.size(14.dp))
                    Text(text = "kttravels@gmail.com", color = TextSecondaryLight, fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(90.dp)) // Padding for sticky bottom bar
    }
}

@Composable
private fun SectionHeader(title: String, subtitle: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = title,
            color = AmberGold,
            fontSize = 17.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 0.5.sp
        )
        Text(
            text = subtitle,
            color = TextSecondaryLight,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
private fun QuickSearchCard(
    onSearch: (from: String, to: String) -> Unit,
    onDirectBook: () -> Unit
) {
    var fromLoc by remember { mutableStateOf("Delhi NCR") }
    var toLoc by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(12.dp, RoundedCornerShape(18.dp), ambientColor = RoyalBlue)
            .border(1.dp, AmberGold.copy(alpha = 0.5f), RoundedCornerShape(18.dp)),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = AmberGold, modifier = Modifier.size(18.dp))
                Text(
                    text = "SEARCH TOURS & HIRE VEHICLES",
                    color = AmberGold,
                    fontSize = 13.5.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = fromLoc,
                    onValueChange = { fromLoc = it },
                    label = { Text("From (Pickup)", fontSize = 11.sp) },
                    singleLine = true,
                    modifier = Modifier.weight(1f).testTag("quick_search_from"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AmberGold,
                        unfocusedBorderColor = DarkSurfaceBorder,
                        focusedTextColor = TextPrimaryLight,
                        unfocusedTextColor = TextPrimaryLight,
                        focusedContainerColor = Navy900,
                        unfocusedContainerColor = Navy900
                    )
                )

                OutlinedTextField(
                    value = toLoc,
                    onValueChange = { toLoc = it },
                    label = { Text("To (Destination)", fontSize = 11.sp) },
                    placeholder = { Text("e.g. Chopta, Manali", fontSize = 11.sp, color = TextSecondaryLight) },
                    singleLine = true,
                    modifier = Modifier.weight(1f).testTag("quick_search_to"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AmberGold,
                        unfocusedBorderColor = DarkSurfaceBorder,
                        focusedTextColor = TextPrimaryLight,
                        unfocusedTextColor = TextPrimaryLight,
                        focusedContainerColor = Navy900,
                        unfocusedContainerColor = Navy900
                    )
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onSearch(fromLoc, toLoc) },
                    modifier = Modifier.weight(1f).height(42.dp).testTag("quick_search_find_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AmberGold, contentColor = Navy900)
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null, modifier = Modifier.size(15.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("FIND PACKAGES", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                }

                Button(
                    onClick = onDirectBook,
                    modifier = Modifier.weight(1f).height(42.dp).testTag("quick_search_book_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue, contentColor = TextPrimaryLight)
                ) {
                    Icon(imageVector = Icons.Default.ShoppingBag, contentDescription = null, modifier = Modifier.size(15.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("CUSTOM BOOKING", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
private fun WhyChooseUsGrid() {
    val reasons = listOf(
        Triple(Icons.Default.DirectionsBus, "Modern Luxury Fleet", "Force Urbania, 17/26 Seater Travellers & Volvo Multi-Axle Coaches with AC & recliners."),
        Triple(Icons.Default.Shield, "Certified Hill Drivers", "Experienced mountain drivers with 10+ years safe driving record on steep hairpins."),
        Triple(Icons.Default.AccessTime, "24/7 Live Assistance", "Dedicated trip coordinator on call & WhatsApp throughout your travel."),
        Triple(Icons.Default.VerifiedUser, "100% Transparent Rates", "No hidden charges, zero surprise toll fees, honest all-inclusive quotation."),
        Triple(Icons.Default.Explore, "Customized Itineraries", "Flexible day schedules, hotel category choices, and personalized sightseeing."),
        Triple(Icons.Default.AcUnit, "Sanitized & Air-Conditioned", "Deep cleaned, spotless plush interiors with USB chargers and entertainment screens.")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (i in 0 until reasons.size step 2) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (j in i until minOf(i + 2, reasons.size)) {
                    val item = reasons[j]
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
                        border = androidx.compose.foundation.BorderStroke(1.dp, DarkSurfaceBorder)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(RoyalBlue.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = item.first, contentDescription = null, tint = AmberGold, modifier = Modifier.size(18.dp))
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = item.second, color = TextPrimaryLight, fontSize = 12.5.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = item.third, color = TextSecondaryLight, fontSize = 11.sp, lineHeight = 15.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FeaturedPackageCard(
    tourPackage: TourPackage,
    onViewDetails: () -> Unit,
    onBookNow: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(260.dp)
            .shadow(12.dp, RoundedCornerShape(18.dp), ambientColor = RoyalBlue)
            .border(1.dp, DarkSurfaceBorder, RoundedCornerShape(18.dp))
            .clickable { onViewDetails() }
            .testTag("featured_pkg_${tourPackage.id}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                Image(
                    painter = painterResource(id = tourPackage.imageDrawableRes),
                    contentDescription = tourPackage.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    shape = RoundedCornerShape(6.dp),
                    color = Navy900.copy(alpha = 0.85f)
                ) {
                    Text(
                        text = "${tourPackage.days}D / ${tourPackage.nights}N",
                        color = AmberGold,
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = tourPackage.name,
                    color = TextPrimaryLight,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = tourPackage.tagLine,
                    color = TextSecondaryLight,
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Starting from", color = TextSecondaryLight, fontSize = 9.5.sp)
                        Text(text = "₹${tourPackage.startingPrice}", color = AmberGold, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
                    }

                    Button(
                        onClick = onBookNow,
                        modifier = Modifier.height(34.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AmberGold, contentColor = Navy900)
                    ) {
                        Text("BOOK", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeOfferCard(
    offer: com.example.data.model.SpecialOffer,
    onClaim: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(240.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .border(1.2.dp, SaffronOrange.copy(alpha = 0.6f), RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = SaffronOrange
            ) {
                Text(
                    text = offer.badge,
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = offer.title, color = TextPrimaryLight, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text(text = offer.description, color = TextSecondaryLight, fontSize = 11.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "CODE: ${offer.code}", color = AmberGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Button(
                    onClick = onClaim,
                    modifier = Modifier.height(30.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RoyalBlue, contentColor = Color.White)
                ) {
                    Text("CLAIM", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

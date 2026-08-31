package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SpecialOffer
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
import com.example.ui.viewmodel.AppNavTab
import com.example.ui.viewmodel.TravelViewModel

@Composable
fun OffersScreen(
    viewModel: TravelViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val offers = viewModel.allSpecialOffers

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
                text = "SPECIAL TRAVEL OFFERS & DISCOUNTS",
                color = AmberGold,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.5.sp
            )
            Text(
                text = "Use these exclusive coupon codes to save big on your next road trip booking",
                color = TextSecondaryLight,
                fontSize = 11.5.sp
            )
        }

        items(offers, key = { it.id }) { offer ->
            OfferVoucherCard(
                offer = offer,
                onApply = {
                    viewModel.updateBookingForm { it.copy(specialRequirements = "Claimed Offer Coupon: ${offer.code} (${offer.title})") }
                    viewModel.navigateTo(AppNavTab.BOOK_NOW)
                    Toast.makeText(context, "Promo Code ${offer.code} applied to booking!", Toast.LENGTH_SHORT).show()
                },
                onCopy = {
                    Toast.makeText(context, "Copied code: ${offer.code}", Toast.LENGTH_SHORT).show()
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}

@Composable
private fun OfferVoucherCard(
    offer: SpecialOffer,
    onApply: () -> Unit,
    onCopy: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(10.dp, RoundedCornerShape(16.dp), ambientColor = SaffronOrange)
            .border(1.2.dp, SaffronOrange.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
            .testTag("offer_card_${offer.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(shape = RoundedCornerShape(6.dp), color = SaffronOrange) {
                    Text(
                        text = offer.badge,
                        color = Color.White,
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Text(
                    text = offer.validityDate,
                    color = AmberGold,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = offer.title, color = TextPrimaryLight, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = offer.description, color = TextSecondaryLight, fontSize = 12.sp, lineHeight = 16.sp)

            Spacer(modifier = Modifier.height(10.dp))

            // Pricing comparison row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "₹${offer.originalPrice}", color = TextSecondaryLight, fontSize = 12.sp, textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough)
                    Text(text = "₹${offer.offerPrice}", color = AmberGold, fontSize = 17.sp, fontWeight = FontWeight.ExtraBold)
                    Text(text = "per person", color = TextSecondaryLight, fontSize = 11.sp)
                }

                Text(text = offer.destinationName, color = TextPrimaryLight, fontSize = 11.sp, fontWeight = FontWeight.Medium)
            }

            Divider(color = DarkSurfaceBorder, thickness = 0.8.dp, modifier = Modifier.padding(vertical = 10.dp))

            // Coupon Code & Apply Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.clickable { onCopy() },
                    shape = RoundedCornerShape(8.dp),
                    color = DarkSurfaceCardHighlight,
                    border = androidx.compose.foundation.BorderStroke(1.dp, AmberGold.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Copy", tint = AmberGold, modifier = Modifier.size(13.dp))
                        Text(text = offer.code, color = AmberGold, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
                    }
                }

                Button(
                    onClick = onApply,
                    modifier = Modifier.height(38.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AmberGold, contentColor = Navy900)
                ) {
                    Icon(imageVector = Icons.Default.ShoppingBag, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("APPLY & BOOK", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

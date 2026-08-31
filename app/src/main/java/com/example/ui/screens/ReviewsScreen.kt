package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AmberGold
import com.example.ui.theme.DarkSurfaceBorder
import com.example.ui.theme.DarkSurfaceCard
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.Navy800
import com.example.ui.theme.Navy900
import com.example.ui.theme.RoyalBlue
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.TextPrimaryLight
import com.example.ui.theme.TextSecondaryLight
import com.example.ui.viewmodel.TravelViewModel

@Composable
fun ReviewsScreen(
    viewModel: TravelViewModel,
    modifier: Modifier = Modifier
) {
    val reviews by viewModel.reviewsList.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Navy900)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "CUSTOMER REVIEWS & RATINGS",
                        color = AmberGold,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "Real feedback from travellers who rode with Khushi Tour & Travels",
                        color = TextSecondaryLight,
                        fontSize = 11.5.sp
                    )
                }

                Button(
                    onClick = { viewModel.setWriteReviewOpen(true) },
                    modifier = Modifier.height(38.dp).testTag("write_review_btn"),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AmberGold, contentColor = Navy900)
                ) {
                    Icon(imageVector = Icons.Default.RateReview, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("WRITE REVIEW", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Overall Score Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard),
                border = androidx.compose.foundation.BorderStroke(1.2.dp, AmberGold.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(text = "4.9", color = AmberGold, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
                            Text(text = "/ 5.0", color = TextSecondaryLight, fontSize = 14.sp)
                        }
                        Row {
                            for (i in 1..5) {
                                Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = SaffronOrange, modifier = Modifier.size(18.dp))
                            }
                        }
                        Text(text = "Based on 1,450+ verified customer reviews", color = TextSecondaryLight, fontSize = 11.sp)
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Surface(shape = RoundedCornerShape(6.dp), color = EmeraldGreen.copy(alpha = 0.2f)) {
                            Text(text = "99.4% Recommendation", color = EmeraldGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Top Rated Luxury Fleet in Delhi NCR", color = TextSecondaryLight, fontSize = 10.5.sp)
                    }
                }
            }
        }

        items(reviews, key = { it.id }) { rev ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(6.dp, RoundedCornerShape(14.dp))
                    .border(1.dp, DarkSurfaceBorder, RoundedCornerShape(14.dp))
                    .testTag("review_item_${rev.id}"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceCard)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(
                                modifier = Modifier.size(36.dp).clip(CircleShape).background(RoyalBlue),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = rev.customerName.take(1).uppercase(),
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(text = rev.customerName, color = TextPrimaryLight, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                    if (rev.isVerified) {
                                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Verified", tint = EmeraldGreen, modifier = Modifier.size(14.dp))
                                    }
                                }
                                Text(text = "${rev.destination} • ${rev.tripDate}", color = AmberGold, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }

                        Row {
                            for (i in 1..rev.rating) {
                                Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = SaffronOrange, modifier = Modifier.size(14.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "\"${rev.review}\"", color = TextPrimaryLight, fontSize = 12.5.sp, lineHeight = 17.sp)
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}

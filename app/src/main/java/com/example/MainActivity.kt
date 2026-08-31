package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.ui.components.AddEditCustomerDialog
import com.example.ui.components.BookingConfirmationDialog
import com.example.ui.components.GalleryViewerDialog
import com.example.ui.components.StickyBottomActionBar
import com.example.ui.components.TopBrandHeader
import com.example.ui.components.TourPackageDetailDialog
import com.example.ui.components.WriteReviewDialog
import com.example.ui.screens.AboutScreen
import com.example.ui.screens.AdminDashboardScreen
import com.example.ui.screens.BookNowScreen
import com.example.ui.screens.ContactScreen
import com.example.ui.screens.DestinationsScreen
import com.example.ui.screens.FleetScreen
import com.example.ui.screens.GalleryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.OffersScreen
import com.example.ui.screens.ReviewsScreen
import com.example.ui.screens.ToursScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.Navy900
import com.example.ui.viewmodel.AppNavTab
import com.example.ui.viewmodel.TravelViewModel

class MainActivity : ComponentActivity() {

    private val travelViewModel: TravelViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme(darkTheme = true) {
                KhushiTravelApp(viewModel = travelViewModel)
            }
        }
    }
}

@Composable
fun KhushiTravelApp(viewModel: TravelViewModel) {
    val context = LocalContext.current
    val currentTab by viewModel.currentTab.collectAsState()

    // Dialog state collections
    val selectedPackageForDetails by viewModel.selectedPackageForDetails.collectAsState()
    val lastConfirmedBooking by viewModel.lastConfirmedBooking.collectAsState()
    val galleryIndex by viewModel.selectedGalleryItemIndex.collectAsState()
    val isWriteReviewOpen by viewModel.isWriteReviewOpen.collectAsState()
    val customerDialogState by viewModel.customerDialogState.collectAsState()

    // Back handler: return to HOME tab if on a secondary tab
    BackHandler(enabled = currentTab != AppNavTab.HOME) {
        viewModel.navigateTo(AppNavTab.HOME)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Navy900),
        topBar = {
            TopBrandHeader(
                currentTab = currentTab,
                onTabSelected = { viewModel.navigateTo(it) },
                onCallClick = { viewModel.callPhone(context) },
                onWhatsAppClick = { viewModel.openWhatsApp(context) }
            )
        },
        bottomBar = {
            StickyBottomActionBar(
                onCallClick = { viewModel.callPhone(context) },
                onWhatsAppClick = { viewModel.openWhatsApp(context) },
                onBookNowClick = { viewModel.navigateTo(AppNavTab.BOOK_NOW) }
            )
        },
        containerColor = Navy900
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Navy900)
        ) {
            AnimatedContent(
                targetState = currentTab,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "main_screen_tab_switch"
            ) { tab ->
                when (tab) {
                    AppNavTab.HOME -> HomeScreen(viewModel = viewModel)
                    AppNavTab.TOURS -> ToursScreen(viewModel = viewModel)
                    AppNavTab.DESTINATIONS -> DestinationsScreen(viewModel = viewModel)
                    AppNavTab.FLEET -> FleetScreen(viewModel = viewModel)
                    AppNavTab.GALLERY -> GalleryScreen(viewModel = viewModel)
                    AppNavTab.OFFERS -> OffersScreen(viewModel = viewModel)
                    AppNavTab.REVIEWS -> ReviewsScreen(viewModel = viewModel)
                    AppNavTab.BOOK_NOW -> BookNowScreen(viewModel = viewModel)
                    AppNavTab.ABOUT -> AboutScreen(viewModel = viewModel)
                    AppNavTab.CONTACT -> ContactScreen(viewModel = viewModel)
                    AppNavTab.ADMIN -> AdminDashboardScreen(viewModel = viewModel)
                }
            }

            // MODALS & DIALOG OVERLAYS
            selectedPackageForDetails?.let { pkg ->
                TourPackageDetailDialog(
                    tourPackage = pkg,
                    onDismiss = { viewModel.selectPackageForDetails(null) },
                    onBookNow = {
                        viewModel.selectPackageForDetails(null)
                        viewModel.prepareBookingForPackage(pkg)
                    },
                    onCallClick = { viewModel.callPhone(context) },
                    onWhatsAppClick = {
                        viewModel.openWhatsApp(context, "Hello, I want to book ${pkg.name} (${pkg.days} Days / ${pkg.nights} Nights).")
                    }
                )
            }

            lastConfirmedBooking?.let { booking ->
                BookingConfirmationDialog(
                    booking = booking,
                    onDismiss = { viewModel.dismissBookingConfirmation() },
                    onWhatsAppConfirm = {
                        viewModel.openWhatsApp(
                            context,
                            "Booking Confirmation #${booking.bookingId}\n" +
                                    "Name: ${booking.customerName}\n" +
                                    "Mobile: ${booking.mobileNumber}\n" +
                                    "Package: ${booking.tourPackage}\n" +
                                    "Vehicle: ${booking.vehicleType}\n" +
                                    "Pickup: ${booking.pickupLocation} -> ${booking.destination}\n" +
                                    "Travel Date: ${booking.travelDate}\n" +
                                    "Passengers: ${booking.passengers}"
                        )
                    },
                    onCallAgent = { viewModel.callPhone(context) }
                )
            }

            galleryIndex?.let { index ->
                GalleryViewerDialog(
                    items = viewModel.allGalleryItems,
                    initialIndex = index,
                    onDismiss = { viewModel.openGalleryViewer(null) }
                )
            }

            if (isWriteReviewOpen) {
                WriteReviewDialog(
                    onDismiss = { viewModel.setWriteReviewOpen(false) },
                    onSubmitReview = { name, dest, rating, text ->
                        viewModel.submitReview(name, dest, rating, text)
                    }
                )
            }

            customerDialogState?.let { formState ->
                AddEditCustomerDialog(
                    initialState = formState,
                    onDismiss = { viewModel.closeCustomerDialog() },
                    onSave = { form -> viewModel.saveCustomerFromDialog(form) }
                )
            }
        }
    }
}

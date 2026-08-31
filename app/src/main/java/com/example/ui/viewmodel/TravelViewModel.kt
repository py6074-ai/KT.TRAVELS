package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.BookingEntity
import com.example.data.model.ContactEnquiryEntity
import com.example.data.model.CustomerEntity
import com.example.data.model.CustomerReviewEntity
import com.example.data.model.DestinationSpot
import com.example.data.model.GalleryItem
import com.example.data.model.TourPackage
import com.example.data.model.VehicleFleetItem
import com.example.data.repository.TravelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.random.Random

enum class AppNavTab(val label: String) {
    HOME("Home"),
    TOURS("Packages"),
    DESTINATIONS("3D Map"),
    FLEET("Our Fleet"),
    GALLERY("Gallery"),
    OFFERS("Offers"),
    REVIEWS("Reviews"),
    BOOK_NOW("Book Now"),
    ABOUT("About Us"),
    CONTACT("Contact"),
    ADMIN("Admin")
}

data class SearchFilterState(
    val fromLocation: String = "Delhi NCR",
    val toLocation: String = "",
    val travelDate: String = "",
    val returnDate: String = "",
    val passengers: Int = 2,
    val vehicleType: String = "All Vehicles",
    val tourPackageName: String = "All Packages"
)

data class BookingFormState(
    val customerName: String = "",
    val mobileNumber: String = "",
    val email: String = "",
    val pickupLocation: String = "",
    val destination: String = "",
    val travelDate: String = "",
    val returnDate: String = "",
    val passengers: Int = 2,
    val tourPackage: String = "CHOPTA TOUR",
    val vehicleType: String = "Luxury Force Urbania (17-Seater)",
    val roomsCount: Int = 1,
    val specialRequirements: String = "",
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)

data class CustomerFormState(
    val id: Long? = null,
    val name: String = "",
    val mobile: String = "",
    val email: String = "",
    val address: String = "",
    val destination: String = "",
    val bookingDate: String = "",
    val travelDate: String = "",
    val packageChosen: String = "",
    val vehicle: String = "",
    val bookingStatus: String = "CONFIRMED",
    val notes: String = ""
)

class TravelViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TravelRepository

    init {
        val database = AppDatabase.getDatabase(application, viewModelScope)
        repository = TravelRepository(
            database.bookingDao(),
            database.customerDao(),
            database.reviewDao(),
            database.enquiryDao()
        )
    }

    // Navigation
    private val _currentTab = MutableStateFlow(AppNavTab.HOME)
    val currentTab: StateFlow<AppNavTab> = _currentTab.asStateFlow()

    fun navigateTo(tab: AppNavTab) {
        _currentTab.value = tab
    }

    // Static collections from repository
    val allTourPackages: List<TourPackage> = repository.getAllTourPackages()
    val allFleetItems: List<VehicleFleetItem> = repository.getFleetItems()
    val allDestinationSpots: List<DestinationSpot> = repository.getDestinationSpots()
    val allSpecialOffers = repository.getSpecialOffers()
    val allGalleryItems: List<GalleryItem> = repository.getGalleryItems()
    val allFAQItems = repository.getFAQItems()

    // Package search & category filters
    private val _packageSearchQuery = MutableStateFlow("")
    val packageSearchQuery: StateFlow<String> = _packageSearchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    val filteredPackages: StateFlow<List<TourPackage>> = combine(
        _packageSearchQuery,
        _selectedCategory
    ) { query, category ->
        allTourPackages.filter { pkg ->
            val matchesQuery = query.isBlank() ||
                    pkg.name.contains(query, ignoreCase = true) ||
                    pkg.tagLine.contains(query, ignoreCase = true) ||
                    pkg.highlights.any { it.contains(query, ignoreCase = true) }
            val matchesCategory = category == "All" || pkg.category.equals(category, ignoreCase = true)
            matchesQuery && matchesCategory
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), allTourPackages)

    fun setPackageSearchQuery(query: String) {
        _packageSearchQuery.value = query
    }

    fun setSelectedCategory(category: String) {
        _selectedCategory.value = category
    }

    // Selected items for modals/dialogs
    private val _selectedPackageForDetails = MutableStateFlow<TourPackage?>(null)
    val selectedPackageForDetails: StateFlow<TourPackage?> = _selectedPackageForDetails.asStateFlow()

    fun selectPackageForDetails(pkg: TourPackage?) {
        _selectedPackageForDetails.value = pkg
    }

    private val _selectedDestinationSpot = MutableStateFlow<DestinationSpot?>(null)
    val selectedDestinationSpot: StateFlow<DestinationSpot?> = _selectedDestinationSpot.asStateFlow()

    fun selectDestinationSpot(spot: DestinationSpot?) {
        _selectedDestinationSpot.value = spot
    }

    private val _selectedFleetItem = MutableStateFlow<VehicleFleetItem?>(null)
    val selectedFleetItem: StateFlow<VehicleFleetItem?> = _selectedFleetItem.asStateFlow()

    fun selectFleetItem(item: VehicleFleetItem?) {
        _selectedFleetItem.value = item
    }

    private val _selectedGalleryItemIndex = MutableStateFlow<Int?>(null)
    val selectedGalleryItemIndex: StateFlow<Int?> = _selectedGalleryItemIndex.asStateFlow()

    fun openGalleryViewer(index: Int?) {
        _selectedGalleryItemIndex.value = index
    }

    // Quick Search panel
    private val _searchFilter = MutableStateFlow(SearchFilterState())
    val searchFilter: StateFlow<SearchFilterState> = _searchFilter.asStateFlow()

    fun updateSearchFilter(update: (SearchFilterState) -> SearchFilterState) {
        _searchFilter.value = update(_searchFilter.value)
    }

    // Booking form
    private val _bookingForm = MutableStateFlow(BookingFormState())
    val bookingForm: StateFlow<BookingFormState> = _bookingForm.asStateFlow()

    private val _lastConfirmedBooking = MutableStateFlow<BookingEntity?>(null)
    val lastConfirmedBooking: StateFlow<BookingEntity?> = _lastConfirmedBooking.asStateFlow()

    fun updateBookingForm(update: (BookingFormState) -> BookingFormState) {
        _bookingForm.value = update(_bookingForm.value)
    }

    fun prepareBookingForPackage(pkg: TourPackage) {
        _bookingForm.value = _bookingForm.value.copy(
            tourPackage = pkg.name,
            destination = pkg.name.replace(" TOUR", ""),
            pickupLocation = "Delhi NCR"
        )
        _currentTab.value = AppNavTab.BOOK_NOW
    }

    fun prepareBookingForVehicle(vehicle: VehicleFleetItem) {
        _bookingForm.value = _bookingForm.value.copy(
            vehicleType = vehicle.name,
            pickupLocation = "Delhi NCR"
        )
        _currentTab.value = AppNavTab.BOOK_NOW
    }

    fun submitBooking(context: Context) {
        val current = _bookingForm.value
        if (current.customerName.trim().isBlank()) {
            _bookingForm.value = current.copy(errorMessage = "Please enter your full name.")
            return
        }
        if (current.mobileNumber.trim().length < 10) {
            _bookingForm.value = current.copy(errorMessage = "Please enter a valid 10-digit mobile number.")
            return
        }
        if (current.pickupLocation.trim().isBlank()) {
            _bookingForm.value = current.copy(errorMessage = "Please specify your pickup location.")
            return
        }
        if (current.destination.trim().isBlank()) {
            _bookingForm.value = current.copy(errorMessage = "Please specify your destination.")
            return
        }
        if (current.travelDate.trim().isBlank()) {
            _bookingForm.value = current.copy(errorMessage = "Please enter your travel start date.")
            return
        }

        val generatedBookingId = "KTT-${Random.nextInt(10000, 99999)}"
        val basePrice = allTourPackages.find { it.name.contains(current.tourPackage, ignoreCase = true) }?.startingPrice ?: 5500
        val estimatedTotal = (basePrice * current.passengers).toDouble()

        val newBooking = BookingEntity(
            bookingId = generatedBookingId,
            customerName = current.customerName.trim(),
            mobileNumber = current.mobileNumber.trim(),
            email = current.email.trim().ifBlank { "kttravels.booking@gmail.com" },
            pickupLocation = current.pickupLocation.trim(),
            destination = current.destination.trim(),
            travelDate = current.travelDate.trim(),
            returnDate = current.returnDate.trim().ifBlank { "As per itinerary" },
            passengers = current.passengers,
            tourPackage = current.tourPackage,
            vehicleType = current.vehicleType,
            roomsCount = current.roomsCount,
            specialRequirements = current.specialRequirements.trim().ifBlank { "Standard comfort package" },
            status = "CONFIRMED",
            totalEstimatedPrice = estimatedTotal
        )

        val newCustomer = CustomerEntity(
            name = current.customerName.trim(),
            mobile = current.mobileNumber.trim(),
            email = current.email.trim(),
            address = current.pickupLocation.trim(),
            destination = current.destination.trim(),
            bookingDate = "Today",
            travelDate = current.travelDate.trim(),
            packageChosen = current.tourPackage,
            vehicle = current.vehicleType,
            bookingStatus = "CONFIRMED",
            travelHistoryCount = 1,
            notes = current.specialRequirements.trim()
        )

        viewModelScope.launch {
            repository.insertBooking(newBooking)
            repository.insertCustomer(newCustomer)
            _lastConfirmedBooking.value = newBooking
            _bookingForm.value = BookingFormState(isSuccess = true)
            Toast.makeText(context, "Booking #$generatedBookingId Confirmed Successfully!", Toast.LENGTH_LONG).show()
        }
    }

    fun dismissBookingConfirmation() {
        _lastConfirmedBooking.value = null
    }

    // Room DB Reactive flows
    val bookingsList: StateFlow<List<BookingEntity>> = repository.allBookings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val customersList: StateFlow<List<CustomerEntity>> = repository.allCustomers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val reviewsList: StateFlow<List<CustomerReviewEntity>> = repository.allReviews
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val enquiriesList: StateFlow<List<ContactEnquiryEntity>> = repository.allEnquiries
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Admin & Customer management actions
    private val _customerSearchQuery = MutableStateFlow("")
    val customerSearchQuery: StateFlow<String> = _customerSearchQuery.asStateFlow()

    fun setCustomerSearchQuery(query: String) {
        _customerSearchQuery.value = query
    }

    val searchFilteredCustomers: StateFlow<List<CustomerEntity>> = combine(
        customersList,
        _customerSearchQuery
    ) { list, query ->
        if (query.isBlank()) list
        else list.filter {
            it.name.contains(query, ignoreCase = true) ||
            it.mobile.contains(query, ignoreCase = true) ||
            it.destination.contains(query, ignoreCase = true) ||
            it.packageChosen.contains(query, ignoreCase = true)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _customerDialogState = MutableStateFlow<CustomerFormState?>(null)
    val customerDialogState: StateFlow<CustomerFormState?> = _customerDialogState.asStateFlow()

    fun openAddCustomerDialog() {
        _customerDialogState.value = CustomerFormState()
    }

    fun openEditCustomerDialog(customer: CustomerEntity) {
        _customerDialogState.value = CustomerFormState(
            id = customer.id,
            name = customer.name,
            mobile = customer.mobile,
            email = customer.email,
            address = customer.address,
            destination = customer.destination,
            bookingDate = customer.bookingDate,
            travelDate = customer.travelDate,
            packageChosen = customer.packageChosen,
            vehicle = customer.vehicle,
            bookingStatus = customer.bookingStatus,
            notes = customer.notes
        )
    }

    fun closeCustomerDialog() {
        _customerDialogState.value = null
    }

    fun saveCustomerFromDialog(form: CustomerFormState) {
        if (form.name.isBlank() || form.mobile.isBlank()) return
        viewModelScope.launch {
            if (form.id == null || form.id == 0L) {
                repository.insertCustomer(
                    CustomerEntity(
                        name = form.name,
                        mobile = form.mobile,
                        email = form.email,
                        address = form.address,
                        destination = form.destination,
                        bookingDate = form.bookingDate.ifBlank { "2026-08-31" },
                        travelDate = form.travelDate.ifBlank { "2026-09-15" },
                        packageChosen = form.packageChosen,
                        vehicle = form.vehicle,
                        bookingStatus = form.bookingStatus,
                        notes = form.notes
                    )
                )
            } else {
                repository.updateCustomer(
                    CustomerEntity(
                        id = form.id,
                        name = form.name,
                        mobile = form.mobile,
                        email = form.email,
                        address = form.address,
                        destination = form.destination,
                        bookingDate = form.bookingDate,
                        travelDate = form.travelDate,
                        packageChosen = form.packageChosen,
                        vehicle = form.vehicle,
                        bookingStatus = form.bookingStatus,
                        notes = form.notes
                    )
                )
            }
            closeCustomerDialog()
        }
    }

    fun deleteCustomer(customer: CustomerEntity) {
        viewModelScope.launch {
            repository.deleteCustomer(customer)
        }
    }

    fun updateBookingStatus(id: Long, newStatus: String) {
        viewModelScope.launch {
            repository.updateBookingStatus(id, newStatus)
        }
    }

    fun deleteBooking(id: Long) {
        viewModelScope.launch {
            repository.deleteBooking(id)
        }
    }

    // Reviews management
    private val _isWriteReviewOpen = MutableStateFlow(false)
    val isWriteReviewOpen: StateFlow<Boolean> = _isWriteReviewOpen.asStateFlow()

    fun setWriteReviewOpen(open: Boolean) {
        _isWriteReviewOpen.value = open
    }

    fun submitReview(name: String, destination: String, rating: Int, reviewText: String) {
        if (name.isBlank() || reviewText.isBlank()) return
        viewModelScope.launch {
            repository.insertReview(
                CustomerReviewEntity(
                    customerName = name.trim(),
                    destination = destination.trim().ifBlank { "North India Tour" },
                    rating = rating.coerceIn(1, 5),
                    review = reviewText.trim(),
                    tripDate = "August 2026",
                    isVerified = true
                )
            )
            _isWriteReviewOpen.value = false
        }
    }

    // Contact Enquiry form
    fun submitContactEnquiry(name: String, mobile: String, email: String, message: String, context: Context) {
        if (name.isBlank() || mobile.isBlank()) {
            Toast.makeText(context, "Please enter your name and phone number.", Toast.LENGTH_SHORT).show()
            return
        }
        viewModelScope.launch {
            repository.insertEnquiry(
                ContactEnquiryEntity(
                    name = name.trim(),
                    mobile = mobile.trim(),
                    email = email.trim(),
                    message = message.trim()
                )
            )
            Toast.makeText(context, "Enquiry received! Our travel expert will call you shortly at $mobile.", Toast.LENGTH_LONG).show()
        }
    }

    fun markEnquiryResolved(id: Long) {
        viewModelScope.launch {
            repository.markEnquiryResolved(id)
        }
    }

    // Communications Helpers
    fun callPhone(context: Context, number: String = "9891719744") {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$number")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Calling $number", Toast.LENGTH_SHORT).show()
        }
    }

    fun openWhatsApp(context: Context, customMessage: String? = null) {
        val msg = customMessage ?: "Hello Khushi Tour & Travels, I want to enquire about a tour package."
        try {
            val encoded = Uri.encode(msg)
            val url = "https://api.whatsapp.com/send?phone=919891719744&text=$encoded"
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "WhatsApp: $msg", Toast.LENGTH_SHORT).show()
        }
    }

    fun sendEmail(context: Context) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:kttravels@gmail.com")
                putExtra(Intent.EXTRA_SUBJECT, "Tour & Travel Enquiry - Khushi Tour & Travels")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Email: kttravels@gmail.com", Toast.LENGTH_SHORT).show()
        }
    }
}

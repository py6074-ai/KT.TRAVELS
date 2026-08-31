package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.R

@Entity(tableName = "bookings")
data class BookingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val bookingId: String,
    val customerName: String,
    val mobileNumber: String,
    val email: String,
    val pickupLocation: String,
    val destination: String,
    val travelDate: String,
    val returnDate: String,
    val passengers: Int,
    val tourPackage: String,
    val vehicleType: String,
    val roomsCount: Int,
    val specialRequirements: String,
    val status: String = "PENDING", // PENDING, CONFIRMED, COMPLETED, CANCELLED
    val totalEstimatedPrice: Double = 0.0,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "customers")
data class CustomerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val mobile: String,
    val email: String,
    val address: String,
    val destination: String,
    val bookingDate: String,
    val travelDate: String,
    val packageChosen: String,
    val vehicle: String,
    val bookingStatus: String = "CONFIRMED",
    val travelHistoryCount: Int = 1,
    val notes: String = ""
)

@Entity(tableName = "reviews")
data class CustomerReviewEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val customerName: String,
    val destination: String,
    val rating: Int,
    val review: String,
    val tripDate: String,
    val isVerified: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "enquiries")
data class ContactEnquiryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val mobile: String,
    val email: String,
    val message: String,
    val isResolved: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

data class DaySchedule(
    val dayNumber: Int,
    val title: String,
    val description: String,
    val mealIncluded: String,
    val stayLocation: String
)

data class TourPackage(
    val id: String,
    val name: String,
    val tagLine: String,
    val days: Int,
    val nights: Int,
    val startingPrice: Int,
    val imageDrawableRes: Int,
    val category: String, // "Himalayan", "Pilgrimage", "Heritage", "International", "Weekend"
    val highlights: List<String>,
    val overview: String,
    val itinerary: List<DaySchedule>,
    val pickupLocation: String = "Delhi / NCR / Dehradun / Haridwar",
    val dropLocation: String = "Delhi / NCR / Same as pickup",
    val hotelsAccommodation: String = "3-Star / 4-Star Premium Deluxe Hotels with Mountain/City View",
    val sightseeing: String = "Full customized sightseeing with dedicated chauffeur & local permits",
    val meals: String = "Breakfast & Dinner (Buffet / Pure Veg options available)",
    val vehicleIncluded: String = "Luxury AC Tempo Traveller / Tourist Coach / Innova Crysta",
    val inclusions: List<String> = listOf(
        "AC Vehicle for entire trip as per group size",
        "Driver allowance, toll taxes, state permits & parking charges",
        "Hotel accommodation on double/triple sharing",
        "Daily morning breakfast and delicious dinner",
        "24/7 On-Trip dedicated customer assistance"
    ),
    val exclusions: List<String> = listOf(
        "Monument entrance fees & camera tickets",
        "Personal expenses, laundry, tips & horse/pony rides",
        "Airfare or Train fare to pickup point",
        "Emergency evacuation or medical expenses",
        "Any item not explicitly mentioned in inclusions"
    ),
    val importantNotes: String = "Carry valid government ID proofs (Aadhaar / Passport). Warm clothing recommended for high altitude trips.",
    val cancellationPolicy: String = "Free cancellation up to 7 days before departure. 50% refund between 3 to 7 days. Non-refundable within 72 hours.",
    val rating: Double = 4.9,
    val reviewsCount: Int = 120
)

data class VehicleFleetItem(
    val id: String,
    val name: String,
    val category: String, // TOURIST TRAVELLER, LUXURY TRAVELLER, AC TRAVELLER, NON-AC TRAVELLER, TOURIST BUS, CAR
    val imageDrawableRes: Int,
    val seatingCapacity: String,
    val isAC: Boolean,
    val driverAvailability: String = "Experienced Professional Uniformed Driver Included",
    val luggageCapacity: String,
    val tourSuitability: String,
    val pricePerKmOrDay: String,
    val description: String,
    val features: List<String>
)

data class DestinationSpot(
    val id: String,
    val name: String,
    val stateOrRegion: String,
    val imageDrawableRes: Int,
    val mapX: Float, // 0f..1f relative
    val mapY: Float, // 0f..1f relative
    val shortDescription: String,
    val bestTimeToVisit: String,
    val mainAttractions: List<String>,
    val availablePackages: List<String>,
    val altitudeOrTag: String
)

data class SpecialOffer(
    val id: String,
    val title: String,
    val badge: String,
    val discountPercent: Int,
    val originalPrice: Int,
    val offerPrice: Int,
    val validityDate: String,
    val description: String,
    val destinationName: String,
    val code: String
)

data class GalleryItem(
    val id: String,
    val title: String,
    val category: String,
    val imageDrawableRes: Int,
    val caption: String
)

data class FAQItem(
    val question: String,
    val answer: String
)

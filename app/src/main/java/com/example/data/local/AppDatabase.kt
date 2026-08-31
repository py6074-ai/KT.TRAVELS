package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.BookingEntity
import com.example.data.model.ContactEnquiryEntity
import com.example.data.model.CustomerEntity
import com.example.data.model.CustomerReviewEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        BookingEntity::class,
        CustomerEntity::class,
        CustomerReviewEntity::class,
        ContactEnquiryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookingDao(): BookingDao
    abstract fun customerDao(): CustomerDao
    abstract fun reviewDao(): ReviewDao
    abstract fun enquiryDao(): EnquiryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "khushi_travels_database"
                )
                .addCallback(DatabaseCallback(scope))
                .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateInitialData(database)
                    }
                }
            }
        }

        private suspend fun populateInitialData(database: AppDatabase) {
            val customerDao = database.customerDao()
            val bookingDao = database.bookingDao()
            val reviewDao = database.reviewDao()
            val enquiryDao = database.enquiryDao()

            // Initial Customers
            val c1 = CustomerEntity(
                name = "Rahul Sharma",
                mobile = "9876543210",
                email = "rahul.sharma@gmail.com",
                address = "Sector 62, Noida, UP",
                destination = "Chopta & Tungnath",
                bookingDate = "2026-08-20",
                travelDate = "2026-09-10",
                packageChosen = "Chopta Tour",
                vehicle = "Luxury Force Traveller (17-Seater)",
                bookingStatus = "CONFIRMED",
                travelHistoryCount = 3,
                notes = "Vegetarian meals requested, senior citizens onboard"
            )
            val c2 = CustomerEntity(
                name = "Pooja Verma",
                mobile = "9811223344",
                email = "pooja.v@outlook.com",
                address = "Rohini Sector 9, Delhi",
                destination = "Manali & Solang Valley",
                bookingDate = "2026-08-25",
                travelDate = "2026-09-15",
                packageChosen = "Manali Tour",
                vehicle = "Tourist Traveller AC",
                bookingStatus = "CONFIRMED",
                travelHistoryCount = 1,
                notes = "Hotel with balcony mountain view preferred"
            )
            val c3 = CustomerEntity(
                name = "Amitabh Gupta",
                mobile = "9955882211",
                email = "gupta.amitabh@yahoo.com",
                address = "Indirapuram, Ghaziabad",
                destination = "Char Dham Yatra",
                bookingDate = "2026-08-28",
                travelDate = "2026-10-02",
                packageChosen = "Char Dham Yatra",
                vehicle = "Deluxe Volvo Coach (45-Seater)",
                bookingStatus = "PENDING",
                travelHistoryCount = 2,
                notes = "Helicopter ticket assistance requested"
            )
            val c4 = CustomerEntity(
                name = "Vikramaditya Singh",
                mobile = "9711002233",
                email = "vikram.singh@gmail.com",
                address = "Civil Lines, Jaipur",
                destination = "Shimla Tour",
                bookingDate = "2026-08-10",
                travelDate = "2026-08-22",
                packageChosen = "Shimla Tour",
                vehicle = "Luxury Force Traveller (17-Seater)",
                bookingStatus = "COMPLETED",
                travelHistoryCount = 4,
                notes = "Very happy with the driver service"
            )

            customerDao.insertCustomer(c1)
            customerDao.insertCustomer(c2)
            customerDao.insertCustomer(c3)
            customerDao.insertCustomer(c4)

            // Initial Bookings
            bookingDao.insertBooking(
                BookingEntity(
                    bookingId = "KTT-78901",
                    customerName = "Rahul Sharma",
                    mobileNumber = "9876543210",
                    email = "rahul.sharma@gmail.com",
                    pickupLocation = "Akshardham, Delhi",
                    destination = "Chopta & Chandrashila",
                    travelDate = "2026-09-10",
                    returnDate = "2026-09-14",
                    passengers = 14,
                    tourPackage = "Chopta Tour",
                    vehicleType = "Luxury Force Traveller (17-Seater)",
                    roomsCount = 5,
                    specialRequirements = "Camp stay in Chopta + bonfire night",
                    status = "CONFIRMED",
                    totalEstimatedPrice = 48000.0
                )
            )
            bookingDao.insertBooking(
                BookingEntity(
                    bookingId = "KTT-78902",
                    customerName = "Pooja Verma",
                    mobileNumber = "9811223344",
                    email = "pooja.v@outlook.com",
                    pickupLocation = "Kashmere Gate, Delhi",
                    destination = "Manali & Solang Valley",
                    travelDate = "2026-09-15",
                    returnDate = "2026-09-20",
                    passengers = 8,
                    tourPackage = "Manali Tour",
                    vehicleType = "Tourist Traveller AC",
                    roomsCount = 3,
                    specialRequirements = "Atal Tunnel & Sissu day trip included",
                    status = "CONFIRMED",
                    totalEstimatedPrice = 36000.0
                )
            )
            bookingDao.insertBooking(
                BookingEntity(
                    bookingId = "KTT-78903",
                    customerName = "Amitabh Gupta",
                    mobileNumber = "9955882211",
                    email = "gupta.amitabh@yahoo.com",
                    pickupLocation = "Haridwar Railway Station",
                    destination = "Char Dham Yatra",
                    travelDate = "2026-10-02",
                    returnDate = "2026-10-12",
                    passengers = 25,
                    tourPackage = "Char Dham Yatra",
                    vehicleType = "Deluxe Volvo Coach (45-Seater)",
                    roomsCount = 10,
                    specialRequirements = "Senior citizen assistance for Yamunotri trek",
                    status = "PENDING",
                    totalEstimatedPrice = 165000.0
                )
            )
            bookingDao.insertBooking(
                BookingEntity(
                    bookingId = "KTT-78904",
                    customerName = "Vikramaditya Singh",
                    mobileNumber = "9711002233",
                    email = "vikram.singh@gmail.com",
                    pickupLocation = "Chandigarh Airport",
                    destination = "Shimla & Kufri",
                    travelDate = "2026-08-22",
                    returnDate = "2026-08-26",
                    passengers = 6,
                    tourPackage = "Shimla Tour",
                    vehicleType = "Luxury Force Traveller (17-Seater)",
                    roomsCount = 2,
                    specialRequirements = "Completed safely",
                    status = "COMPLETED",
                    totalEstimatedPrice = 24500.0
                )
            )

            // Initial Reviews
            reviewDao.insertReview(
                CustomerReviewEntity(
                    customerName = "Col. R.K. Mehta",
                    destination = "Kedarnath & Badrinath",
                    rating = 5,
                    review = "Exceptional service by Khushi Tour & Travels! The Tempo Traveller was spotlessly clean, pushback seats were ultra comfortable on mountain roads, and our driver Vinod ji was extremely skilled and polite. Highly recommended for family pilgrimage!",
                    tripDate = "May 2026"
                )
            )
            reviewDao.insertReview(
                CustomerReviewEntity(
                    customerName = "Sunita Aggarwal",
                    destination = "Chopta & Tungnath Trek",
                    rating = 5,
                    review = "Booked a 17-seater luxury traveller for our group of 14 friends. The booking process on phone (9891719744) was instantaneous and completely hassle-free. Best travel company in Delhi NCR!",
                    tripDate = "June 2026"
                )
            )
            reviewDao.insertReview(
                CustomerReviewEntity(
                    customerName = "Dr. Sameer Joshi",
                    destination = "Himachal Tour (Manali & Shimla)",
                    rating = 5,
                    review = "Superb luxury buses, on-time pickup from Delhi, and honest transparent pricing without any hidden charges. The vehicle AC and suspension were first-class.",
                    tripDate = "July 2026"
                )
            )
            reviewDao.insertReview(
                CustomerReviewEntity(
                    customerName = "Ananya Dixit",
                    destination = "Ayodhya & Varanasi Tour",
                    rating = 5,
                    review = "Our spiritual tour was made unforgettable. Smooth driving, comfortable luggage space, and 24/7 support throughout. Will definitely travel again with Khushi Travels.",
                    tripDate = "August 2026"
                )
            )

            // Initial Enquiries
            enquiryDao.insertEnquiry(
                ContactEnquiryEntity(
                    name = "Deepak Rawat",
                    mobile = "9810998877",
                    email = "deepak.rawat@techcorp.com",
                    message = "Interested in corporate group tour for 35 members to Rishikesh rafting & camping in October."
                )
            )
            enquiryDao.insertEnquiry(
                ContactEnquiryEntity(
                    name = "Meenakshi Sundaram",
                    mobile = "9444112233",
                    email = "meena.sundaram@gmail.com",
                    message = "Need quote for 26-seater AC bus from Delhi to Rajasthan Golden Triangle (Jaipur, Agra, Mathura)."
                )
            )
        }
    }
}

package com.example.data.repository

import com.example.R
import com.example.data.local.BookingDao
import com.example.data.local.CustomerDao
import com.example.data.local.EnquiryDao
import com.example.data.local.ReviewDao
import com.example.data.model.BookingEntity
import com.example.data.model.ContactEnquiryEntity
import com.example.data.model.CustomerEntity
import com.example.data.model.CustomerReviewEntity
import com.example.data.model.DaySchedule
import com.example.data.model.DestinationSpot
import com.example.data.model.FAQItem
import com.example.data.model.GalleryItem
import com.example.data.model.SpecialOffer
import com.example.data.model.TourPackage
import com.example.data.model.VehicleFleetItem
import kotlinx.coroutines.flow.Flow

class TravelRepository(
    private val bookingDao: BookingDao,
    private val customerDao: CustomerDao,
    private val reviewDao: ReviewDao,
    private val enquiryDao: EnquiryDao
) {
    val allBookings: Flow<List<BookingEntity>> = bookingDao.getAllBookings()
    val allCustomers: Flow<List<CustomerEntity>> = customerDao.getAllCustomers()
    val allReviews: Flow<List<CustomerReviewEntity>> = reviewDao.getAllReviews()
    val allEnquiries: Flow<List<ContactEnquiryEntity>> = enquiryDao.getAllEnquiries()

    fun searchCustomers(query: String): Flow<List<CustomerEntity>> = customerDao.searchCustomers(query)

    suspend fun insertBooking(booking: BookingEntity): Long = bookingDao.insertBooking(booking)
    suspend fun updateBooking(booking: BookingEntity) = bookingDao.updateBooking(booking)
    suspend fun updateBookingStatus(id: Long, status: String) = bookingDao.updateBookingStatus(id, status)
    suspend fun deleteBooking(id: Long) = bookingDao.deleteBooking(id)

    suspend fun insertCustomer(customer: CustomerEntity): Long = customerDao.insertCustomer(customer)
    suspend fun updateCustomer(customer: CustomerEntity) = customerDao.updateCustomer(customer)
    suspend fun deleteCustomer(customer: CustomerEntity) = customerDao.deleteCustomer(customer)
    suspend fun deleteCustomerById(id: Long) = customerDao.deleteCustomerById(id)

    suspend fun insertReview(review: CustomerReviewEntity): Long = reviewDao.insertReview(review)
    suspend fun insertEnquiry(enquiry: ContactEnquiryEntity): Long = enquiryDao.insertEnquiry(enquiry)
    suspend fun markEnquiryResolved(id: Long) = enquiryDao.markResolved(id)

    // ==========================================
    // 20 ALL-INCLUSIVE POPULAR TOUR PACKAGES
    // ==========================================
    fun getAllTourPackages(): List<TourPackage> {
        return listOf(
            TourPackage(
                id = "pkg_chopta",
                name = "CHOPTA TOUR",
                tagLine = "Mini Switzerland of India & Highest Shiva Temple",
                days = 4,
                nights = 3,
                startingPrice = 6499,
                imageDrawableRes = R.drawable.img_hero_mountain,
                category = "Himalayan",
                highlights = listOf("Tungnath Temple Trek", "Chandrashila Summit (4000m)", "Deoria Tal Lake", "Alpine Meadow Camping"),
                overview = "Experience the untouched beauty of Chopta, nestled in the Garhwal Himalayas. Trek to the world's highest Shiva shrine at Tungnath and enjoy 360-degree panoramic views of Himalayan giants like Nanda Devi and Trishul.",
                itinerary = listOf(
                    DaySchedule(1, "Delhi/Haridwar to Chopta", "Early morning departure in luxury AC Traveller. Scenic drive along Ganga and Alaknanda rivers. Check-in to Swiss camps with bonfire.", "Dinner", "Chopta Alpine Camp"),
                    DaySchedule(2, "Trek to Tungnath & Chandrashila", "Moderate trek through lush rhododendron forests to ancient Tungnath Temple and summit Chandrashila for spectacular sunset.", "Breakfast & Dinner", "Chopta Alpine Camp"),
                    DaySchedule(3, "Deoria Tal Lake Exploration", "Short trek to the pristine emerald waters of Deoria Tal reflecting Chaukhamba peaks. Evening leisure and music.", "Breakfast & Dinner", "Sari Village / Chopta Resort"),
                    DaySchedule(4, "Return Journey via Rishikesh", "Breakfast and drive back with stopover at Devprayag Sangam and Rishikesh Laxman Jhula. Drop at Delhi.", "Breakfast", "Home")
                )
            ),
            TourPackage(
                id = "pkg_shimla",
                name = "SHIMLA TOUR",
                tagLine = "Queen of Hills & Colonial Majesty",
                days = 3,
                nights = 2,
                startingPrice = 4999,
                imageDrawableRes = R.drawable.img_bus_urbania,
                category = "Himalayan",
                highlights = listOf("The Mall Road & Ridge", "Kufri Snow Adventure", "Jakhoo Hanuman Temple", "Christ Church"),
                overview = "Relax among pine-covered hills, British heritage architecture, vibrant cafes on The Mall, and thrilling horseback & snow activities in Kufri.",
                itinerary = listOf(
                    DaySchedule(1, "Delhi to Shimla via Pinjore", "Scenic hill road drive in luxury pushback Traveller. Hotel check-in. Evening stroll at Mall Road & Lakkar Bazaar.", "Dinner", "Shimla Valley Resort"),
                    DaySchedule(2, "Kufri & Local Sightseeing", "Visit Kufri Fun World, Himalayan Nature Park, Jakhoo Hill cable car, and iconic Ridge.", "Breakfast & Dinner", "Shimla Valley Resort"),
                    DaySchedule(3, "Shimla Heritage & Return", "Visit Viceregal Lodge (Indian Institute of Advanced Study) and return drive to Delhi NCR.", "Breakfast", "Home")
                )
            ),
            TourPackage(
                id = "pkg_haridwar",
                name = "HARIDWAR TOUR",
                tagLine = "Gateway to the Gods & Ganga Aarti",
                days = 2,
                nights = 1,
                startingPrice = 2999,
                imageDrawableRes = R.drawable.img_bus_volvo,
                category = "Pilgrimage",
                highlights = listOf("Har Ki Pauri Evening Ganga Aarti", "Mansa Devi & Chandi Devi Ropeway", "Daksheshwar Mahadev Temple", "Famous Street Food & Ghats"),
                overview = "Immerse yourself in divine spiritual energy on the banks of Holy River Ganga. Witness the world-famous grand evening Aarti ceremony at Har Ki Pauri with lamps floating in the river.",
                itinerary = listOf(
                    DaySchedule(1, "Delhi to Haridwar & Maha Aarti", "Smooth highway journey via Meerut Expressway. Hotel check-in, holy dip at Brahmakund, and VIP sitting for Ganga Aarti.", "Dinner", "Haridwar Riverside Hotel"),
                    DaySchedule(2, "Temple Darshan & Return", "Ropeway ride to Mansa Devi and Chandi Devi shrines, local shopping for sacred souvenirs, and return drive.", "Breakfast", "Home")
                )
            ),
            TourPackage(
                id = "pkg_manali",
                name = "MANALI TOUR",
                tagLine = "Valley of the Gods & Snow Paradise",
                days = 5,
                nights = 4,
                startingPrice = 7999,
                imageDrawableRes = R.drawable.img_hero_mountain,
                category = "Himalayan",
                highlights = listOf("Solang Valley Paragliding", "Atal Tunnel & Sissu Lahaul", "Hadimba Devi Temple", "Old Manali Cafes & Mall Road"),
                overview = "The ultimate mountain holiday destination. From paragliding in Solang to crossing the world's longest high-altitude tunnel into mystical Lahaul Valley.",
                itinerary = listOf(
                    DaySchedule(1, "Delhi to Manali Overnighter", "Board luxury AC Pushback Traveller in the evening. Scenic overnight cruise through Punjab and Himachal.", "Packed Refreshment", "Onboard Luxury Bus"),
                    DaySchedule(2, "Arrival & Local Sightseeing", "Check-in, relax. Visit Hadimba Temple, Vashisht Hot Springs, Club House, and Mall Road.", "Dinner", "Manali Mountain View Hotel"),
                    DaySchedule(3, "Solang Valley & Atal Tunnel", "Full day adventure at Solang (ropeway, quad biking) and crossing engineering marvel Atal Tunnel into Sissu waterfalls.", "Breakfast & Dinner", "Manali Mountain View Hotel"),
                    DaySchedule(4, "Naggar Castle & Kullu River Rafting", "Visit historic Naggar Castle and enjoy thrilling Beas River white-water rafting in Kullu.", "Breakfast & Dinner", "Manali Mountain View Hotel"),
                    DaySchedule(5, "Shopping & Return to Delhi", "Morning free for Tibetan market shopping. Afternoon departure for Delhi.", "Breakfast", "Home")
                )
            ),
            TourPackage(
                id = "pkg_mussoorie",
                name = "MUSSOORIE TOUR",
                tagLine = "Queen of the Hills with Doon Valley Panorama",
                days = 3,
                nights = 2,
                startingPrice = 4599,
                imageDrawableRes = R.drawable.img_bus_maharaja,
                category = "Himalayan",
                highlights = listOf("Kempty Falls Cascades", "Gun Hill Cable Car", "Company Garden", "Lal Tibba Scenic Viewpoint"),
                overview = "A serene escape with misty clouds, majestic oak forests, and dazzling night views over the twinkling lights of Dehradun valley.",
                itinerary = listOf(
                    DaySchedule(1, "Delhi/Dehradun to Mussoorie", "Drive through scenic Mussoorie bypass. Hotel check-in. Evening at Mall Road & Kulri Bazaar.", "Dinner", "Mussoorie Hillside Hotel"),
                    DaySchedule(2, "Kempty Falls & Lal Tibba", "Splash in refreshing Kempty Falls, visit Landour, Char Dukan, and oldest viewpoint Lal Tibba.", "Breakfast & Dinner", "Mussoorie Hillside Hotel"),
                    DaySchedule(3, "Company Garden, Robber's Cave & Return", "Visit Company Garden floral park, stop at Robber's Cave in Dehradun, and drive back to Delhi.", "Breakfast", "Home")
                )
            ),
            TourPackage(
                id = "pkg_rishikesh",
                name = "RISHIKESH TOUR",
                tagLine = "Yoga Capital & River Rafting Adventure",
                days = 3,
                nights = 2,
                startingPrice = 3999,
                imageDrawableRes = R.drawable.img_bus_urbania,
                category = "Weekend",
                highlights = listOf("16km Ganga White Water Rafting", "Riverside Beach Camping & Bonfire", "Triveni Ghat Evening Aarti", "Beatles Ashram & Ram Jhula"),
                overview = "The perfect mix of spiritual peace and adrenaline rush. Experience cliff jumping, white-water rapids, riverside stargazing camps, and tranquil Vedic yoga vibes.",
                itinerary = listOf(
                    DaySchedule(1, "Delhi to Shivpuri Camps", "Arrival at luxury riverside tented resort. Welcome drinks, volleyball, evening bonfire and barbecue.", "Lunch & Dinner", "Shivpuri Jungle Camp"),
                    DaySchedule(2, "Grade III River Rafting & Ghats", "Thrilling 16km rafting from Marine Drive to NIM Beach with cliff jump and body surfing. Visit Beatles Ashram and Triveni Ghat Aarti.", "Breakfast & Dinner", "Shivpuri Jungle Camp"),
                    DaySchedule(3, "Neer Garh Waterfall & Return", "Morning trek to Neer Garh crystal waterfall, cafe hopping at Laxman Jhula, and return trip.", "Breakfast", "Home")
                )
            ),
            TourPackage(
                id = "pkg_ayodhya",
                name = "AYODHYA TOUR",
                tagLine = "Sacred Janmabhoomi & Shri Ram Mandir Darshan",
                days = 3,
                nights = 2,
                startingPrice = 5499,
                imageDrawableRes = R.drawable.img_bus_volvo,
                category = "Pilgrimage",
                highlights = listOf("Grand Shri Ram Janmabhoomi Mandir", "Hanuman Garhi Fort Temple", "Saryu River Aarti at Ram Ki Paidi", "Kanak Bhawan & Dashrath Mahal"),
                overview = "Embark on a soul-stirring pilgrimage to the birthplace of Bhagwan Ram. Witness the architectural wonder of the new Ram Mandir and the magical illuminated Saryu ghats.",
                itinerary = listOf(
                    DaySchedule(1, "Delhi/Lucknow to Ayodhya", "Arrival via Purvanchal Expressway in luxury AC Traveller. Hotel check-in. Evening Saryu River boat ride & grand laser light show at Ram Ki Paidi.", "Dinner", "Ayodhya Heritage Hotel"),
                    DaySchedule(2, "VIP Ram Mandir & Hanuman Garhi Darshan", "Morning VIP darshan at Ram Janmabhoomi Temple, prayer at Hanuman Garhi, and visit to Kanak Bhawan.", "Breakfast & Dinner", "Ayodhya Heritage Hotel"),
                    DaySchedule(3, "Surya Kund, Guptar Ghat & Return", "Visit Guptar Ghat and Surya Kund before return departure with sweet prasadam.", "Breakfast", "Home")
                )
            ),
            TourPackage(
                id = "pkg_vrindavan",
                name = "VRINDAVAN TOUR",
                tagLine = "Land of Divine Love & Krishna Bhakti",
                days = 2,
                nights = 1,
                startingPrice = 2499,
                imageDrawableRes = R.drawable.img_bus_maharaja,
                category = "Pilgrimage",
                highlights = listOf("Banke Bihari Ji Mangla/Shringar Darshan", "Prem Mandir Italian Marble Laser Show", "ISKCON Vrindavan", "Nidhivan Sacred Grove"),
                overview = "Experience the transcendent peace and devotional ecstasy in Vrindavan, where every lane echoes with 'Radhe Radhe'.",
                itinerary = listOf(
                    DaySchedule(1, "Delhi to Vrindavan via Yamuna Expressway", "Quick 3-hour journey. Visit Banke Bihari Ji temple, ISKCON temple, and breathtaking nighttime illumination at Prem Mandir.", "Dinner", "Vrindavan Devotional Stay"),
                    DaySchedule(2, "Nidhivan & Vaishno Devi Dham & Return", "Visit mystical Nidhivan, Chandrodaya temple site, enjoy local pedas, and drive back to Delhi.", "Breakfast", "Home")
                )
            ),
            TourPackage(
                id = "pkg_mathura",
                name = "MATHURA TOUR",
                tagLine = "Birthplace of Shri Krishna & Yamuna Ghats",
                days = 2,
                nights = 1,
                startingPrice = 2299,
                imageDrawableRes = R.drawable.img_bus_urbania,
                category = "Pilgrimage",
                highlights = listOf("Krishna Janmasthan Temple Complex", "Dwarkadhish Temple", "Vishram Ghat Yamuna Aarti", "Gokul & Raman Reti"),
                overview = "Explore the historic holy city of Mathura and experience authentic Braj culture, peda sweets, and serene boat rides on Yamuna Ji.",
                itinerary = listOf(
                    DaySchedule(1, "Delhi to Mathura & Janmabhoomi", "Expressway drive. Check-in. Visit Shri Krishna Janmasthan sanctum, Dwarkadhish temple and Vishram Ghat evening deepdan.", "Dinner", "Mathura City Hotel"),
                    DaySchedule(2, "Gokul, Mahavan & Raman Reti", "Morning excursion to Gokul childhood home of Krishna, roll on the holy sand of Raman Reti, and return.", "Breakfast", "Home")
                )
            ),
            TourPackage(
                id = "pkg_agra",
                name = "AGRA TOUR",
                tagLine = "City of the Taj & Mughal Splendour",
                days = 2,
                nights = 1,
                startingPrice = 2999,
                imageDrawableRes = R.drawable.img_bus_volvo,
                category = "Heritage",
                highlights = listOf("Taj Mahal Sunrise Darshan", "Agra Fort Red Sandstone Citadel", "Fatehpur Sikri & Buland Darwaza", "Famous Agra Petha Tasting"),
                overview = "Stand before one of the Seven Wonders of the World, the eternal symbol of love Taj Mahal, crafted entirely of white Makrana marble.",
                itinerary = listOf(
                    DaySchedule(1, "Delhi to Agra via Yamuna Expressway", "Morning drive in luxury coach. Visit majestic Agra Fort and Mehtab Bagh sunset view behind Taj Mahal.", "Dinner", "Agra 4-Star Hotel"),
                    DaySchedule(2, "Taj Mahal Sunrise & Fatehpur Sikri", "Early morning sunrise tour of Taj Mahal, visit Fatehpur Sikri royal complex and return drive to Delhi.", "Breakfast", "Home")
                )
            ),
            TourPackage(
                id = "pkg_jaipur",
                name = "JAIPUR TOUR",
                tagLine = "The Royal Pink City of Rajasthan",
                days = 3,
                nights = 2,
                startingPrice = 5499,
                imageDrawableRes = R.drawable.img_bus_urbania,
                category = "Heritage",
                highlights = listOf("Amber Fort Palace & Sheesh Mahal", "Hawa Mahal & City Palace", "Jantar Mantar Astronomical Observatory", "Chokhi Dhani Rajasthani Cultural Village"),
                overview = "Immerse yourself in regal grandeur with magnificent hill forts, royal palaces, gemstone markets, and mouthwatering Dal Baati Churma.",
                itinerary = listOf(
                    DaySchedule(1, "Delhi to Jaipur via Delhi-Mumbai Expressway", "Superfast expressway drive in luxury Traveller. Hotel check-in. Evening visit to Birla Temple and Chokhi Dhani ethnic resort.", "Dinner", "Jaipur Royal Palace Hotel"),
                    DaySchedule(2, "Forts & Royal City Tour", "Explore Amber Fort with Mirror Palace, Nahargarh Fort skyline, Jal Mahal water palace, and City Palace museum.", "Breakfast & Dinner", "Jaipur Royal Palace Hotel"),
                    DaySchedule(3, "Hawa Mahal, Bapu Bazaar & Return", "Morning photo op at Hawa Mahal, shopping for handicrafts and Jaipuri quilts, and return drive.", "Breakfast", "Home")
                )
            ),
            TourPackage(
                id = "pkg_kashmir",
                name = "KASHMIR TOUR",
                tagLine = "Paradise on Earth & Dal Lake Houseboats",
                days = 6,
                nights = 5,
                startingPrice = 14999,
                imageDrawableRes = R.drawable.img_hero_mountain,
                category = "Himalayan",
                highlights = listOf("Shikara Ride & Luxury Houseboat on Dal Lake", "Gulmarg Gondola World's Highest Cable Car", "Pahalgam Betaab & Aru Valleys", "Sonamarg Thajiwas Glacier"),
                overview = "Experience heaven on earth with snow-capped Pir Panjal ranges, colorful tulip & Mughal gardens, saffron fields, and magical shikara cruises on Dal Lake.",
                itinerary = listOf(
                    DaySchedule(1, "Arrival in Srinagar & Dal Lake Houseboat", "Pickup and transfer to handcrafted Cedarwood Luxury Houseboat. Sunset shikara ride.", "Dinner", "Dal Lake Deluxe Houseboat"),
                    DaySchedule(2, "Mughal Gardens & Old City", "Visit Shalimar Bagh, Nishat Bagh, Chashme Shahi and Shankaracharya Hill Temple.", "Breakfast & Dinner", "Srinagar 4-Star Hotel"),
                    DaySchedule(3, "Gulmarg Snow Excursion", "Full day trip to Gulmarg, ride Phase 1 & 2 Gondola up to 13,780 feet overlooking snowy peaks.", "Breakfast & Dinner", "Srinagar 4-Star Hotel"),
                    DaySchedule(4, "Pahalgam Valley of Shepherds", "Drive through Pampore saffron fields and apple orchards to scenic Pahalgam. Visit Betaab Valley and Chandanwari.", "Breakfast & Dinner", "Pahalgam Riverside Resort"),
                    DaySchedule(5, "Sonamarg Meadow of Gold", "Excursion to Sonamarg, enjoy pony ride to Thajiwas Glacier and Sindh river viewpoints.", "Breakfast & Dinner", "Srinagar 4-Star Hotel"),
                    DaySchedule(6, "Departure with Sweet Memories", "Breakfast, shopping for dry fruits & Pashmina shawls, and transfer for return trip.", "Breakfast", "Home")
                )
            ),
            TourPackage(
                id = "pkg_kedarnath",
                name = "KEDARNATH TOUR",
                tagLine = "Divine Abode of Lord Shiva in the Himalayas",
                days = 5,
                nights = 4,
                startingPrice = 9499,
                imageDrawableRes = R.drawable.img_hero_mountain,
                category = "Pilgrimage",
                highlights = listOf("Kedarnath Dham Mandir Darshan", "Guptkashi & Sonprayag Base", "Bhairavnath Temple & Mandakini River", "Gaurikund Holy Springs"),
                overview = "The most revered Jyotirlinga surrounded by towering glacier peaks. A transformative pilgrimage that stays etched in your heart forever.",
                itinerary = listOf(
                    DaySchedule(1, "Delhi/Haridwar to Guptkashi", "Scenic drive via Rishikesh, Devprayag, and Rudraprayag sangam. Check-in and acclimatization.", "Dinner", "Guptkashi Deluxe Hotel"),
                    DaySchedule(2, "Guptkashi to Kedarnath (Trek/Heli)", "Drive to Sonprayag/Gaurikund. Trek or take helicopter to Kedarnath. Check-in at GMVN/private stay. Evening Aarti.", "Breakfast & Dinner", "Kedarnath Dham Top Stay"),
                    DaySchedule(3, "Morning VIP Puja & Return to Guptkashi", "Early morning VIP Puja, trek down to Gaurikund, and vehicle pickup to Guptkashi hotel.", "Breakfast & Dinner", "Guptkashi Deluxe Hotel"),
                    DaySchedule(4, "Guptkashi to Rishikesh", "Relaxing drive back to Rishikesh. Evening free to attend Triveni Ghat Aarti.", "Breakfast & Dinner", "Rishikesh Riverside Resort"),
                    DaySchedule(5, "Rishikesh to Delhi", "Morning breakfast, visit Ram Jhula, and smooth drive back to Delhi NCR.", "Breakfast", "Home")
                )
            ),
            TourPackage(
                id = "pkg_badrinath",
                name = "BADRINATH TOUR",
                tagLine = "Sacred Vaikuntha on Earth & Alaknanda Valley",
                days = 5,
                nights = 4,
                startingPrice = 8999,
                imageDrawableRes = R.drawable.img_bus_volvo,
                category = "Pilgrimage",
                highlights = listOf("Badrinath Temple & Tapt Kund Hot Spring", "Mana Village - First Indian Village", "Vyas Gufa & Saraswati River Origin / Bheem Pul", "Joshimath & Pandukeshwar"),
                overview = "Visit the majestic seat of Lord Badri Vishal flanked by Nar and Narayan mountain ranges along the roaring turquoise waters of the Alaknanda.",
                itinerary = listOf(
                    DaySchedule(1, "Delhi/Haridwar to Joshimath/Pipalkoti", "Drive through Garhwal valleys via Karnaprayag and Nandaprayag.", "Dinner", "Joshimath Valley Hotel"),
                    DaySchedule(2, "Joshimath to Badrinath Dham", "Scenic drive to Badrinath. Holy dip in Tapt Kund natural hot sulfur springs and darshan of Badri Vishal.", "Breakfast & Dinner", "Badrinath Temple Stay"),
                    DaySchedule(3, "Mana Village & Vasudhara Excursion", "Visit historic Mana Village (First Village of India), Vyas Cave where Mahabharata was penned, and roaring Bheem Pul.", "Breakfast & Dinner", "Joshimath Valley Hotel"),
                    DaySchedule(4, "Joshimath to Rudraprayag/Srinagar", "Visit Auli ropeway viewpoint in morning and drive down to Rudraprayag.", "Breakfast & Dinner", "Rudraprayag Riverside Resort"),
                    DaySchedule(5, "Return to Delhi", "Breakfast and return drive to Delhi NCR with memories of the Himalayas.", "Breakfast", "Home")
                )
            ),
            TourPackage(
                id = "pkg_chardham",
                name = "CHAR DHAM YATRA",
                tagLine = "The Complete Himalayan Sacred Circuit (Yamunotri, Gangotri, Kedarnath, Badrinath)",
                days = 11,
                nights = 10,
                startingPrice = 24999,
                imageDrawableRes = R.drawable.img_bus_maharaja,
                category = "Pilgrimage",
                highlights = listOf("All 4 Sacred Dhams in Uttarakhand", "Yamunotri & Surya Kund", "Gangotri & Bhagirathi River", "Kedarnath Jyotirlinga", "Badrinath & Mana Village"),
                overview = "The ultimate Hindu pilgrimage covering the four divine Himalayan seats of liberation. Complete comfort with premium AC pushback buses, experienced mountain drivers, and curated hotel stays.",
                itinerary = listOf(
                    DaySchedule(1, "Delhi/Haridwar to Barkot", "Drive via Mussoorie and Kempty Falls to Barkot base camp.", "Dinner", "Barkot Apple Resort"),
                    DaySchedule(2, "Yamunotri Dham Darshan", "Drive to Janki Chatti and 6km trek to Yamunotri Temple. Cook rice in Surya Kund.", "Breakfast & Dinner", "Barkot Apple Resort"),
                    DaySchedule(3, "Barkot to Uttarkashi", "Drive to Uttarkashi. Visit Kashi Vishwanath Temple and Shakti Temple.", "Breakfast & Dinner", "Uttarkashi Riverside Hotel"),
                    DaySchedule(4, "Gangotri Dham Darshan", "Scenic drive along Bhagirathi gorge to holy Gangotri temple and submerged Shivalinga.", "Breakfast & Dinner", "Uttarkashi Riverside Hotel"),
                    DaySchedule(5, "Uttarkashi to Guptkashi", "Trans-Himalayan scenic drive through Tehri dam reservoir.", "Breakfast & Dinner", "Guptkashi Deluxe Hotel"),
                    DaySchedule(6, "Kedarnath Dham Trek & Darshan", "Trek to Kedarnath and participate in evening Aarti.", "Breakfast & Dinner", "Kedarnath Top Stay"),
                    DaySchedule(7, "Kedarnath to Guptkashi/Pipalkoti", "Morning Puja, trek down, and relax at Pipalkoti.", "Breakfast & Dinner", "Pipalkoti Valley Resort"),
                    DaySchedule(8, "Pipalkoti to Badrinath Dham", "Darshan of Badri Vishal and visit Mana village.", "Breakfast & Dinner", "Badrinath Hotel"),
                    DaySchedule(9, "Badrinath to Rudraprayag", "Drive via Joshimath and Vishnuprayag.", "Breakfast & Dinner", "Rudraprayag Resort"),
                    DaySchedule(10, "Rudraprayag to Haridwar", "Drive back to Haridwar via Devprayag Sangam. Attend Har Ki Pauri Aarti.", "Breakfast & Dinner", "Haridwar Hotel"),
                    DaySchedule(11, "Haridwar to Delhi Drop", "Morning leisure and drop at Delhi railway station/airport.", "Breakfast", "Home")
                )
            ),
            TourPackage(
                id = "pkg_muktinath",
                name = "MUKTINATH TOUR",
                tagLine = "Sacred Place of Salvation in Nepal Mustang",
                days = 7,
                nights = 6,
                startingPrice = 21999,
                imageDrawableRes = R.drawable.img_hero_mountain,
                category = "International",
                highlights = listOf("Muktinath 108 Sacred Waterspouts", "Jomsom Mountain Flight / 4x4 Trail", "Pokhara Phewa Lake", "Kathmandu Pashupatinath"),
                overview = "An extraordinary spiritual journey to the Annapurna region in Nepal, honoring both Lord Vishnu and Lord Shiva at an elevation of 3,710 meters.",
                itinerary = listOf(
                    DaySchedule(1, "Delhi/Gorakhpur to Pokhara", "Border crossing in luxury AC vehicle, scenic highway to Pokhara.", "Dinner", "Pokhara Lakeside Hotel"),
                    DaySchedule(2, "Pokhara to Jomsom via Tatopani", "Scenic mountain route into Mustang region.", "Breakfast & Dinner", "Jomsom Mountain Lodge"),
                    DaySchedule(3, "Muktinath Darshan & 108 Kunda", "Drive to Muktinath temple, bath in 108 holy spouts, darshan of eternal flame, and back to Jomsom.", "Breakfast & Dinner", "Jomsom Mountain Lodge"),
                    DaySchedule(4, "Jomsom to Pokhara & Sightseeing", "Return to Pokhara. Visit Davis Falls, Gupteshwor Cave and Phewa lake boating.", "Breakfast & Dinner", "Pokhara Lakeside Hotel"),
                    DaySchedule(5, "Pokhara to Kathmandu", "Scenic drive to capital Kathmandu with stopover at Manakamana Cable Car.", "Breakfast & Dinner", "Kathmandu Deluxe Hotel"),
                    DaySchedule(6, "Kathmandu Pashupatinath & Swayambhunath", "VIP Darshan at holy Pashupatinath temple and Boudhanath Stupa.", "Breakfast & Dinner", "Kathmandu Deluxe Hotel"),
                    DaySchedule(7, "Return Journey", "Breakfast and transfer for return trip with spiritual blessings.", "Breakfast", "Home")
                )
            ),
            TourPackage(
                id = "pkg_pokhara",
                name = "POKHARA TOUR",
                tagLine = "Jewel of Nepal & Annapurna Reflection",
                days = 5,
                nights = 4,
                startingPrice = 13999,
                imageDrawableRes = R.drawable.img_bus_urbania,
                category = "International",
                highlights = listOf("Sarangkot Sunrise over Annapurna & Machapuchare", "Phewa Lake Boating & Tal Barahi", "World Peace Pagoda", "Pari Adventure Zipflyer"),
                overview = "Relax in the calm lakeside paradise of Pokhara with direct views of snow-dusted fishtail peaks reflecting in turquoise waters.",
                itinerary = listOf(
                    DaySchedule(1, "Delhi/Gorakhpur to Pokhara", "Cross Nepal border smoothly in our sanitized luxury AC bus.", "Dinner", "Pokhara 4-Star Resort"),
                    DaySchedule(2, "Sarangkot Sunrise & City Tour", "Stunning 5 AM sunrise at Sarangkot, visit Bindhyabasini Temple and Seti River gorge.", "Breakfast & Dinner", "Pokhara 4-Star Resort"),
                    DaySchedule(3, "Adventure & Lake Peace", "Boating to Tal Barahi island temple, hike up to World Peace Pagoda, and lakeside cafe evening.", "Breakfast & Dinner", "Pokhara 4-Star Resort"),
                    DaySchedule(4, "Bat Cave & Pumdikot Shiva Statue", "Visit giant Pumdikot Shiva statue overlooking valley and International Mountain Museum.", "Breakfast & Dinner", "Pokhara 4-Star Resort"),
                    DaySchedule(5, "Return to India", "Morning breakfast and smooth transfer back to India.", "Breakfast", "Home")
                )
            ),
            TourPackage(
                id = "pkg_rajasthan",
                name = "RAJASTHAN TOUR",
                tagLine = "Royal Heritage Triangle (Jaipur, Jodhpur, Udaipur, Jaisalmer)",
                days = 8,
                nights = 7,
                startingPrice = 18999,
                imageDrawableRes = R.drawable.img_bus_volvo,
                category = "Heritage",
                highlights = listOf("Sam Sand Dunes Desert Safari & Camel Ride", "Mehrangarh Fort Jodhpur", "Udaipur City Palace & Lake Pichola", "Jaipur Amber Fort"),
                overview = "Live like royalty across the golden Thar Desert, majestic blue city Jodhpur, romantic city of lakes Udaipur, and royal Jaipur.",
                itinerary = listOf(
                    DaySchedule(1, "Delhi to Jaipur", "Expressway drive, Amber fort palace, Chokhi Dhani.", "Dinner", "Jaipur Royal Hotel"),
                    DaySchedule(2, "Jaipur to Jodhpur (Blue City)", "Visit Mehrangarh Fort and Jaswant Thada cenotaphs.", "Breakfast & Dinner", "Jodhpur Heritage Stay"),
                    DaySchedule(3, "Jodhpur to Jaisalmer Golden City", "Drive to Golden City. Visit Jaisalmer living fort and Patwon ki Haveli.", "Breakfast & Dinner", "Jaisalmer City Hotel"),
                    DaySchedule(4, "Sam Sand Dunes Desert Safari", "Thar Desert camel safari, dune bashing in 4x4, folk dance and camp night under stars.", "Breakfast & Dinner", "Sam Luxury Desert Camp"),
                    DaySchedule(5, "Jaisalmer to Udaipur", "Scenic drive to Venice of the East, Udaipur.", "Breakfast & Dinner", "Udaipur Lakeview Hotel"),
                    DaySchedule(6, "Udaipur City Palace & Lake Pichola Boat", "Visit City Palace, Saheliyon ki Bari and sunset boat ride on Lake Pichola.", "Breakfast & Dinner", "Udaipur Lakeview Hotel"),
                    DaySchedule(7, "Udaipur to Pushkar Holy Lake", "Visit Brahma Temple and sacred Pushkar Sarovar ghats.", "Breakfast & Dinner", "Pushkar Heritage Resort"),
                    DaySchedule(8, "Pushkar to Delhi Return", "Return highway cruise to Delhi NCR.", "Breakfast", "Home")
                )
            ),
            TourPackage(
                id = "pkg_himachal",
                name = "HIMACHAL TOUR",
                tagLine = "Grand Circuit: Shimla, Kullu, Manali, Dharamshala & Dalhousie",
                days = 9,
                nights = 8,
                startingPrice = 19999,
                imageDrawableRes = R.drawable.img_bus_maharaja,
                category = "Himalayan",
                highlights = listOf("Shimla Kufri", "Manali Solang Valley & Atal Tunnel", "Dharamshala Dalai Lama Temple", "Dalhousie Khajjiar Mini Switzerland"),
                overview = "The definitive Himachal Pradesh holiday taking you through lush valleys, pine forests, Tibetan monasteries, and snow peaks.",
                itinerary = listOf(
                    DaySchedule(1, "Delhi to Shimla", "Check-in and evening at Mall Road.", "Dinner", "Shimla Hotel"),
                    DaySchedule(2, "Shimla & Kufri", "Kufri snow park and Ridge.", "Breakfast & Dinner", "Shimla Hotel"),
                    DaySchedule(3, "Shimla to Manali via Kullu", "Pandoh Dam, Kullu Shawl factory and Beas river.", "Breakfast & Dinner", "Manali Hotel"),
                    DaySchedule(4, "Manali Solang & Atal Tunnel", "Adventure sports and snow fun.", "Breakfast & Dinner", "Manali Hotel"),
                    DaySchedule(5, "Manali to Dharamshala", "Palampur tea gardens and Kangra valley.", "Breakfast & Dinner", "Dharamshala Resort"),
                    DaySchedule(6, "McLeod Ganj & Dalai Lama Monastery", "Bhagsunag waterfall, St. John Church, and Tibetan market.", "Breakfast & Dinner", "Dharamshala Resort"),
                    DaySchedule(7, "Dharamshala to Dalhousie & Khajjiar", "Visit emerald meadow of Khajjiar with cedar forest.", "Breakfast & Dinner", "Dalhousie Resort"),
                    DaySchedule(8, "Dalhousie Dainkund Peak & Panchpula", "Panchpula waterfall, shopping at Gandhi Chowk.", "Breakfast & Dinner", "Dalhousie Resort"),
                    DaySchedule(9, "Return to Delhi", "Scenic drive back to Delhi NCR.", "Breakfast", "Home")
                )
            ),
            TourPackage(
                id = "pkg_uttarakhand",
                name = "UTTARAKHAND TOUR",
                tagLine = "Devbhoomi Special: Nainital, Kausani, Corbett, Mussoorie & Rishikesh",
                days = 7,
                nights = 6,
                startingPrice = 16499,
                imageDrawableRes = R.drawable.img_bus_urbania,
                category = "Himalayan",
                highlights = listOf("Jim Corbett Jungle Jeep Safari", "Nainital Naini Lake Boating", "Kausani Sunrise over Trishul Peak", "Rishikesh Ganga Aarti"),
                overview = "Experience the rich wildlife, tranquil lakes, majestic hill stations, and spiritual sanctuaries across the Land of the Gods.",
                itinerary = listOf(
                    DaySchedule(1, "Delhi to Jim Corbett National Park", "Check-in at wildlife resort. Evening jungle walk and pool time.", "Dinner", "Corbett Jungle Resort"),
                    DaySchedule(2, "Corbett 4x4 Tiger Safari & Nainital", "Early morning jungle safari to spot Royal Bengal Tigers. Drive to Nainital. Naini Lake boat ride.", "Breakfast & Dinner", "Nainital Lakeview Hotel"),
                    DaySchedule(3, "Nainital Lake Tour & Kausani", "Visit Bhimtal, Sattal, Naukuchiatal, and drive to Kausani Switzerland of India.", "Breakfast & Dinner", "Kausani Himalayan Resort"),
                    DaySchedule(4, "Kausani to Ranikhet & Haridwar", "Visit Ranikhet Golf Course and Apple orchards. Drive to Haridwar.", "Breakfast & Dinner", "Haridwar Hotel"),
                    DaySchedule(5, "Haridwar to Rishikesh & Mussoorie", "Visit Laxman Jhula, Beatles Ashram, and drive up to Mussoorie.", "Breakfast & Dinner", "Mussoorie Hilltop Hotel"),
                    DaySchedule(6, "Mussoorie Kempty Falls & Mall Road", "Splash in Kempty falls, visit Gun Hill and Mall Road.", "Breakfast & Dinner", "Mussoorie Hilltop Hotel"),
                    DaySchedule(7, "Mussoorie to Delhi Return", "Morning breakfast and drive back to Delhi.", "Breakfast", "Home")
                )
            )
        )
    }

    // ==========================================
    // VEHICLE FLEET WITH UPLOADED VEHICLE PHOTOS
    // ==========================================
    fun getFleetItems(): List<VehicleFleetItem> {
        return listOf(
            VehicleFleetItem(
                id = "fleet_urbania_luxury",
                name = "Luxury Force Urbania / Tempo Traveller",
                category = "LUXURY TRAVELLER",
                imageDrawableRes = R.drawable.img_bus_urbania,
                seatingCapacity = "12 to 17 Luxury Recliner Seats (1x1 & 2x1)",
                isAC = true,
                driverAvailability = "Uniformed Chauffeur + Hill Driving Certified",
                luggageCapacity = "Large Rear Boot & Roof Carrier for 20+ Bags",
                tourSuitability = "Chopta, Manali, Kedarnath, Shimla, Family & Group Tours",
                pricePerKmOrDay = "Starting ₹24/km or ₹6,500/day",
                description = "Our flagship luxury vehicle featuring aircraft-style pushback leather seats, individual AC vents, USB mobile charging on every seat, ambient LED mood lighting, and high-fidelity entertainment system.",
                features = listOf("Individual AC Vents", "Pushback 2x1 Leather Seats", "USB Charging at every seat", "32-inch LED Screen & Mic", "Air Suspension for smooth hill travel", "GPS Live Tracking")
            ),
            VehicleFleetItem(
                id = "fleet_volvo_coach",
                name = "Deluxe Volvo Multi-Axle Tourist Coach",
                category = "TOURIST BUS",
                imageDrawableRes = R.drawable.img_bus_volvo,
                seatingCapacity = "45 to 53 Semi-Sleeper / Reclining Seats",
                isAC = true,
                driverAvailability = "2 Professional Highway Captains + Helper",
                luggageCapacity = "Underbelly Cargo Hold (50+ Heavy Suitcases)",
                tourSuitability = "Char Dham Yatra, Rajasthan Circuit, Corporate & College Tours",
                pricePerKmOrDay = "Starting ₹48/km or ₹14,000/day",
                description = "World-class luxury tourist coach with ultra-smooth multi-axle air suspension, dual AC climate control, panoramic UV-cut tinted windows, onboard PA system, and premium reclining seats.",
                features = listOf("Dual-Zone Chilled AC", "Multi-Axle Air Suspension", "Panoramic Windows", "Underbelly Cargo Hold", "Emergency Exit & First Aid", "Public Address & Karaoke Mic")
            ),
            VehicleFleetItem(
                id = "fleet_maharaja_traveller",
                name = "Maharaja Modified AC Traveller",
                category = "TOURIST TRAVELLER",
                imageDrawableRes = R.drawable.img_bus_maharaja,
                seatingCapacity = "9 to 13 Maharaja Club Recliners",
                isAC = true,
                driverAvailability = "Dedicated Experienced Chauffeur Included",
                luggageCapacity = "Spacious Rear Trunk (15 Suitcases)",
                tourSuitability = "VIP Pilgrimage, Luxury Golden Triangle, Wedding & Family VIPs",
                pricePerKmOrDay = "Starting ₹28/km or ₹7,500/day",
                description = "Super-luxury customized executive van with ultra-wide sofa-style captain seats with footrests, woodgrain interior finish, mini refrigerator, and whisper-quiet cabin insulation.",
                features = listOf("Maharaja Club Captain Seats with Footrests", "Mini Fridge & Chiller", "Woodgrain Luxury Finish", "Reading Lamps & WiFi Hub", "Whisper Quiet Soundproof Cabin", "Curtains for Complete Privacy")
            ),
            VehicleFleetItem(
                id = "fleet_ac_traveller_standard",
                name = "Standard 17-Seater AC Tourist Traveller",
                category = "AC TRAVELLER",
                imageDrawableRes = R.drawable.img_bus_urbania,
                seatingCapacity = "17 Seats (2x1 Layout)",
                isAC = true,
                driverAvailability = "Courteous & Verified Driver",
                luggageCapacity = "Rooftop Luggage Carrier with Waterproof Cover",
                tourSuitability = "Haridwar, Rishikesh, Mathura, Vrindavan, Weekend Getaways",
                pricePerKmOrDay = "Starting ₹20/km or ₹5,000/day",
                description = "Reliable, economical and comfortable AC Traveller ideal for medium groups, office outings, and pilgrimage day trips.",
                features = listOf("High Power Dual AC", "Pushback Comfortable Seats", "Music System with Bluetooth", "Waterproof Luggage Cover", "Clean Sanitized Interiors")
            ),
            VehicleFleetItem(
                id = "fleet_non_ac_traveller",
                name = "Non-AC Budget Tourist Traveller",
                category = "NON-AC TRAVELLER",
                imageDrawableRes = R.drawable.img_bus_maharaja,
                seatingCapacity = "17 to 20 Seats",
                isAC = false,
                driverAvailability = "Experienced Route Expert Driver",
                luggageCapacity = "High-Capacity Roof Rack",
                tourSuitability = "Budget Hill Treks, Local Uttarakhand & Himachal Valley Tours",
                pricePerKmOrDay = "Starting ₹17/km or ₹4,200/day",
                description = "Affordable and robust vehicle with openable panoramic windows for enjoying fresh mountain breeze during high-altitude Himalayan tours.",
                features = listOf("Extra Legroom", "Sliding Mountain-Breeze Windows", "Roof Carrier for Heavy Gear", "Pocket-friendly Pricing", "Trained Mountain Driver")
            ),
            VehicleFleetItem(
                id = "fleet_innova_crysta",
                name = "Toyota Innova Crysta / Ertiga",
                category = "CAR",
                imageDrawableRes = R.drawable.img_bus_volvo,
                seatingCapacity = "6 to 7 Seats",
                isAC = true,
                driverAvailability = "Polite English & Hindi Speaking Chauffeur",
                luggageCapacity = "3 Large Bags + 2 Small Bags",
                tourSuitability = "Small Family, Couples, Luxury VIP Transfers, Airport Pickup",
                pricePerKmOrDay = "Starting ₹14/km or ₹3,500/day",
                description = "Top-tier premium MUV offering unmatched ride quality, plush captain seats, and swift acceleration on steep mountain hairpins.",
                features = listOf("Automatic Climate Control", "Plush Captain Chairs", "Fast Highway Cruiser", "Airport & Outstation Expert", "Clean Mineral Water Onboard")
            )
        )
    }

    // ==========================================
    // 11 DESTINATION SPOTS FOR 3D INTERACTIVE MAP
    // ==========================================
    fun getDestinationSpots(): List<DestinationSpot> {
        return listOf(
            DestinationSpot("dest_chopta", "Chopta", "Uttarakhand", R.drawable.img_hero_mountain, 0.48f, 0.28f, "Mini Switzerland of India with alpine meadows and gateway to Tungnath & Chandrashila.", "Apr to Jun & Sep to Dec", listOf("Tungnath Temple", "Chandrashila Peak", "Deoria Tal", "Alpine Meadows"), listOf("CHOPTA TOUR", "UTTARAKHAND TOUR"), "2,680m Altitude"),
            DestinationSpot("dest_shimla", "Shimla", "Himachal Pradesh", R.drawable.img_bus_urbania, 0.38f, 0.22f, "Colonial hill station surrounded by oak and pine forests with Ridge and Kufri.", "All Year Round (Snow in Dec-Feb)", listOf("Mall Road", "Kufri", "Jakhoo Temple", "Christ Church"), listOf("SHIMLA TOUR", "HIMACHAL TOUR"), "2,276m Altitude"),
            DestinationSpot("dest_haridwar", "Haridwar", "Uttarakhand", R.drawable.img_bus_volvo, 0.44f, 0.35f, "Sacred Ganga ghats with divine evening Maha Aarti at Har Ki Pauri.", "Sep to Apr", listOf("Har Ki Pauri", "Mansa Devi Temple", "Chandi Devi", "Ganga Ghats"), listOf("HARIDWAR TOUR", "CHAR DHAM YATRA"), "Spiritual Capital"),
            DestinationSpot("dest_manali", "Manali", "Himachal Pradesh", R.drawable.img_hero_mountain, 0.35f, 0.16f, "Adventure epicenter with snow activities in Solang, Atal Tunnel, and Old Manali.", "Oct to Jun", listOf("Solang Valley", "Atal Tunnel", "Hadimba Temple", "Sissu Valley"), listOf("MANALI TOUR", "HIMACHAL TOUR"), "2,050m Altitude"),
            DestinationSpot("dest_mussoorie", "Mussoorie", "Uttarakhand", R.drawable.img_bus_maharaja, 0.42f, 0.30f, "Picturesque hill station overlooking the dazzling Dehradun valley.", "Mar to Jun & Sep to Nov", listOf("Kempty Falls", "Lal Tibba", "Gun Hill", "Mall Road"), listOf("MUSSOORIE TOUR", "UTTARAKHAND TOUR"), "2,005m Altitude"),
            DestinationSpot("dest_rishikesh", "Rishikesh", "Uttarakhand", R.drawable.img_bus_urbania, 0.46f, 0.34f, "World yoga capital with thrilling white water rafting, cliff jumps, and riverside camps.", "Sep to Jun", listOf("River Rafting", "Triveni Ghat Aarti", "Beatles Ashram", "Ram Jhula"), listOf("RISHIKESH TOUR", "UTTARAKHAND TOUR"), "Yoga Capital"),
            DestinationSpot("dest_ayodhya", "Ayodhya", "Uttar Pradesh", R.drawable.img_bus_volvo, 0.68f, 0.48f, "Holy birthplace of Lord Rama featuring the magnificent new Ram Janmabhoomi Mandir.", "Oct to Mar", listOf("Shri Ram Mandir", "Hanuman Garhi", "Saryu Aarti", "Kanak Bhawan"), listOf("AYODHYA TOUR"), "Holy City"),
            DestinationSpot("dest_vrindavan", "Vrindavan", "Uttar Pradesh", R.drawable.img_bus_maharaja, 0.52f, 0.45f, "Sacred playground of Shri Krishna with Banke Bihari Ji and Prem Mandir.", "All Year Round", listOf("Banke Bihari Ji", "Prem Mandir", "ISKCON Vrindavan", "Nidhivan"), listOf("VRINDAVAN TOUR", "MATHURA TOUR"), "Braj Bhumi"),
            DestinationSpot("dest_kedarnath", "Kedarnath", "Uttarakhand", R.drawable.img_hero_mountain, 0.50f, 0.24f, "Holiest Shiva Jyotirlinga nestled beneath towering Himalayan glacier peaks.", "May to Oct", listOf("Kedarnath Mandir", "Bhairavnath", "Mandakini River", "Gaurikund"), listOf("KEDARNATH TOUR", "CHAR DHAM YATRA"), "3,583m Altitude"),
            DestinationSpot("dest_badrinath", "Badrinath", "Uttarakhand", R.drawable.img_bus_volvo, 0.54f, 0.22f, "Sacred Vishnu temple on the banks of Alaknanda with Mana Village.", "May to Oct", listOf("Badrinath Temple", "Mana Village", "Tapt Kund", "Vyas Gufa"), listOf("BADRINATH TOUR", "CHAR DHAM YATRA"), "3,133m Altitude"),
            DestinationSpot("dest_kashmir", "Kashmir", "Jammu & Kashmir", R.drawable.img_hero_mountain, 0.25f, 0.08f, "Paradise on earth with Dal Lake houseboats, Gulmarg gondola, and Pahalgam.", "Apr to Oct & Dec to Feb (Snow)", listOf("Dal Lake Shikara", "Gulmarg Gondola", "Betaab Valley", "Sonamarg"), listOf("KASHMIR TOUR"), "Paradise on Earth")
        )
    }

    // ==========================================
    // SPECIAL TRAVEL OFFERS
    // ==========================================
    fun getSpecialOffers(): List<SpecialOffer> {
        return listOf(
            SpecialOffer("off_summer_mountain", "Summer Mountain Escape", "LIMITED 25% OFF", 25, 7999, 5999, "Valid till 30 Sep 2026", "Special group discount on Manali & Shimla 5-Day Luxury Traveller package.", "Manali & Shimla", "SUMMER25"),
            SpecialOffer("off_family_holiday", "Family Holiday Package", "FAMILY SAVER", 20, 9499, 7599, "Valid till 15 Oct 2026", "Complimentary campfire + room upgrade for families travelling to Chopta & Rishikesh.", "Chopta & Rishikesh", "FAMILYFUN"),
            SpecialOffer("off_uttarakhand", "Uttarakhand Special", "BESTSELLER", 30, 6499, 4549, "Valid till 31 Oct 2026", "Flat discount on 4-Day Chopta Tungnath Chandrashila high-altitude tour.", "Chopta Tour", "DEVBHUMI30"),
            SpecialOffer("off_himachal", "Himachal Special", "HOT DEAL", 22, 19999, 15599, "Valid till 15 Nov 2026", "Grand 9-Day Himachal Circuit covering Shimla, Manali, Dharamshala & Dalhousie.", "Himachal Circuit", "HIMACHAL22"),
            SpecialOffer("off_pilgrimage", "Pilgrimage Tour Special", "DEVOTION DEAL", 18, 24999, 20499, "Valid for Oct 2026", "Priority darshan assistance + Deluxe AC Coach for complete 11-Day Char Dham Yatra.", "Char Dham Yatra", "YATRA18"),
            SpecialOffer("off_group_tour", "Group Tour Offer", "GROUP DISCOUNT", 35, 14999, 9749, "Valid on 10+ Bookings", "Special rate for college, corporate and large family group bookings on 17 & 26 seater travellers.", "All North India Tours", "GROUP35")
        )
    }

    // ==========================================
    // PHOTO GALLERY ITEMS
    // ==========================================
    fun getGalleryItems(): List<GalleryItem> {
        return listOf(
            GalleryItem("gal_urbania_front", "Luxury Urbania Fleet (Front View)", "FLEET", R.drawable.img_bus_urbania, "Our immaculate Force Urbania luxury tourist traveller ready for departure."),
            GalleryItem("gal_volvo_side", "Deluxe Volvo Coach (Highway View)", "FLEET", R.drawable.img_bus_volvo, "Multi-axle Volvo tourist coach cruising along scenic mountain highways."),
            GalleryItem("gal_maharaja_rear", "Maharaja Traveller (Rear 3/4 View)", "FLEET", R.drawable.img_bus_maharaja, "Premium Maharaja Traveller with heavy-duty roof carrier and tinted glass."),
            GalleryItem("gal_chopta_mountains", "Chopta & Chandrashila Snow Peaks", "DESTINATIONS", R.drawable.img_hero_mountain, "Majestic snow-capped Himalayan panoramas on our Chopta sunrise trek."),
            GalleryItem("gal_haridwar_aarti", "Sacred Har Ki Pauri Ganga Aarti", "PILGRIMAGE", R.drawable.img_bus_volvo, "Illuminated evening Ganga Aarti at Haridwar with thousands of floating lamps."),
            GalleryItem("gal_manali_highway", "Manali Mountain Valley Highway", "ROADS & NATURE", R.drawable.img_hero_mountain, "Winding scenic highway road through pine forests and misty clouds.")
        )
    }

    // ==========================================
    // FREQUENTLY ASKED QUESTIONS
    // ==========================================
    fun getFAQItems(): List<FAQItem> {
        return listOf(
            FAQItem(
                "How can I book a tour with Khushi Tour & Travels?",
                "You can book directly using our online booking form on this app, call us at 9891719744, or click any 'WhatsApp Us' button to chat with our travel executive instantly. We will confirm your itinerary, seat allotment, and send booking voucher immediately."
            ),
            FAQItem(
                "Do you provide doorstep pickup and drop?",
                "Yes! We provide doorstep pickup and drop across Delhi NCR (Delhi, Noida, Gurgaon, Ghaziabad, Faridabad), as well as Haridwar, Dehradun, Chandigarh, and major airport/railway terminals."
            ),
            FAQItem(
                "Do you provide AC vehicles on mountain roads?",
                "Yes, all our luxury travellers and coaches are equipped with powerful air conditioning. On steep hill climbs, AC may be operated at moderate levels for optimal vehicle performance and passenger comfort."
            ),
            FAQItem(
                "Can I customize my tour package?",
                "Absolutely! We specialize in tailor-made custom itineraries. You can modify the duration, add or remove destinations, upgrade hotels, and select your preferred vehicle type."
            ),
            FAQItem(
                "Do you provide group and corporate bookings?",
                "Yes, we cater to large family groups, corporate offsites, college tours, and wedding transportation with our fleet of 12, 17, 20, 26-seater Travellers and 45-53 seater Volvo buses at attractive group discounts."
            ),
            FAQItem(
                "Can I book a vehicle only without hotel package?",
                "Yes! You can hire any vehicle from our fleet on per-km or per-day basis with our experienced driver for your independent road trip across India."
            ),
            FAQItem(
                "How can I contact Khushi Tour & Travels?",
                "You can call or WhatsApp us 24/7 at +91 9891719744 or email us at kttravels@gmail.com. Our support team is always active to assist you before and during your journey."
            ),
            FAQItem(
                "What is your cancellation and refund policy?",
                "We offer 100% full refund for cancellations made 7 or more days prior to departure. 50% refund is provided for cancellations between 3 to 7 days. Changes in travel dates can be accommodated with prior notice."
            )
        )
    }
}

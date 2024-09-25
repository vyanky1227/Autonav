package com.example.autonav

class EventRepository(private val service: EventbriteApi) {

    suspend fun searchEvents(
        query: String,
        location: String? = null,
        category: String? = null
    ): List<Event> {
        // Call the API and handle optional parameters
        val response = service.getEvents(query, location ?: "", category ?: "")
        return response.events ?: emptyList() // Handle nullability
    }
}

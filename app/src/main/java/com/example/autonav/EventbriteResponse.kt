package com.example.autonav

data class EventbriteResponse(
    val events: List<Event> // Assuming the response contains a list of events
)

data class Event(
    val id: String,
    val name: Name,
    val description: Description,
    val start: Start,
    val end: End,
    val url: String,
    val venue: Venue?
)

data class Name(
    val text: String,
    val html: String
)

data class Description(
    val text: String,
    val html: String
)

data class Start(
    val utc: String,
    val local: String
)

data class End(
    val utc: String,
    val local: String
)

data class Venue(
    val name: String,
    val address: String,
    val city: String,
    val region: String,
    val country: String,
    val postal_code: String,
    val latitude: Double,
    val longitude: Double
)

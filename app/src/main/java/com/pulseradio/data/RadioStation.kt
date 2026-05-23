package com.pulseradio.data

data class RadioStation(
    val id: String,
    val name: String,
    val genre: String,
    val location: String,
    val streamUrl: String,
    val img: String
)

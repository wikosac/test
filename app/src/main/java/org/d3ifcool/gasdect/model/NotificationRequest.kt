package org.d3ifcool.gasdect.model

data class NotificationRequest(
    val token: String,
    val title: String,
    val body: String
)

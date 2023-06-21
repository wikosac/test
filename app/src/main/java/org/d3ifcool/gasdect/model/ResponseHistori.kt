package org.d3ifcool.gasdect.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseHistori(
    @Json(name = "kode")
    val kode: String,

    @Json(name = "pesan")
    val pesan: String,

    @Json(name = "result")
    val result: List<Histori>
)
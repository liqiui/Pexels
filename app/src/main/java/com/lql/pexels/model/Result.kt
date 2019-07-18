package com.lql.pexels.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@Parcelize
class Src(
    @JsonProperty("original") val original: String,
    @JsonProperty("large2x") val large2x: String,
    @JsonProperty("large") val large: String,
    @JsonProperty("medium") val medium: String,
    @JsonProperty("small") val small: String,
    @JsonProperty("portrait") val portrait: String,
    @JsonProperty("landscape") val landscape: String,
    @JsonProperty("tiny") val tiny: String
): Parcelable

@Parcelize
class Photo(
    @JsonProperty("id") val id: String,
    @JsonProperty("width") val width: Int,
    @JsonProperty("height") val height: Int,
    @JsonProperty("url") val url: String,
    @JsonProperty("photographer") val photographer: String,
    @JsonProperty("photographer_url") val photographer_url: String,
    @JsonProperty("src") val src: Src
): Parcelable

@Parcelize
class Result(
    @JsonProperty("total_results") val total_results: Int,
    @JsonProperty("page") val page: Int,
    @JsonProperty("per_page") val per_page: Int,
    @JsonProperty("photos") val photos: List<Photo>
): Parcelable

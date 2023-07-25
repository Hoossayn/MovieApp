package com.example.movieapp.data.remote.dto

import com.example.movieapp.common.Constants
import com.example.movieapp.domain.model.ProductionCompany
import com.google.gson.annotations.SerializedName


data class ProductionCompanyDto(
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String?,
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String
)

fun ProductionCompanyDto.toProdCompany(): ProductionCompany = ProductionCompany(
    logoPath =  Constants.PROFILE_AND_LOGO_IMAGE_URL.plus(logoPath),
    name = name
)

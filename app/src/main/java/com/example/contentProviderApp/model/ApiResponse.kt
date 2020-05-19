package com.example.contentProviderApp.model

import com.google.gson.annotations.SerializedName


class ApiResponse : ArrayList<ApiResponseItem>()

data class ApiResponseItem(
    @SerializedName("address")
    var address: Address? = Address(),
    @SerializedName("company")
    var company: Company? = Company(),
    @SerializedName("email")
    var email: String? = "",
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("phone")
    var phone: String? = "",
    @SerializedName("profile_image")
    var profileImage: String? = "",
    @SerializedName("username")
    var username: String? = "",
    @SerializedName("website")
    var website: String? = ""
)

data class Address(
    @SerializedName("city")
    var city: String? = "",
    @SerializedName("geo")
    var geo: Geo = Geo(),
    @SerializedName("street")
    var street: String? = "",
    @SerializedName("suite")
    var suite: String? = "",
    @SerializedName("zipcode")
    var zipcode: String? = ""
)

data class Company(
    @SerializedName("bs")
    var bs: String? = "",
    @SerializedName("catchPhrase")
    var catchPhrase: String? = "",
    @SerializedName("name")
    var name: String? = ""
)

data class Geo(
    @SerializedName("lat")
    var lat: String? = "",
    @SerializedName("lng")
    var lng: String? = ""
)
package com.example.cloneuber.domain.model

import android.net.Uri

data class User(
    val id: String?=null,
    var name:String?= null,
    var email: String?=null,
    var password:String?=null,
    var confirmPassword:String?=null,
    var type:String?=null,
    var phoneNumber: String?=null,
    var profileImage: String?=null,
    var vehicleType: String?=null,
    var vehicleLicensePlate: String?=null,
    var averageRating: Double?=null,
    var isActive: Boolean?=null,
    //val location: Location
    var favoriteDrivers: List<String>?=null,
    var paymentMethods: List<String>?=null,
){

}


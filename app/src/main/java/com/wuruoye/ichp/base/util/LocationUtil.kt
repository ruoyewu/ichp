package com.wuruoye.ichp.base.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager

/**
 * Created by wuruoye on 2018/1/28.
 * this file is to
 */
object LocationUtil {

    @SuppressLint("MissingPermission")
    fun getLocation(context: Context): Array<Double> {
        val locationManager = context
                .getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers = locationManager.getProviders(true)
        val locationProvider: String
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            locationProvider = LocationManager.GPS_PROVIDER
        }else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            locationProvider = LocationManager.NETWORK_PROVIDER
        }else {
            throw Exception("no available providers")
        }
        val location = locationManager.getLastKnownLocation(locationProvider)
        if (location != null) {
            return arrayOf(location.longitude, location.latitude)
        }else {
            throw Exception("no available location")
        }
    }
}
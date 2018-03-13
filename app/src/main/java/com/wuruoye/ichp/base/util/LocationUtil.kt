package com.wuruoye.ichp.base.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import com.wuruoye.ichp.base.model.Listener
import com.wuruoye.ichp.base.model.MainHandler

/**
 * Created by wuruoye on 2018/1/28.
 * this file is to
 */
object LocationUtil {

    @SuppressLint("MissingPermission")
    fun getLocation(context: Context, listener: Listener<Array<Double>>) {
        Thread(Runnable {
            Looper.prepare()
            val locationManager = context
                    .getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val providers = locationManager.getProviders(true)
            val locationProvider: String
            if (providers.contains(LocationManager.GPS_PROVIDER)) {
                locationProvider = LocationManager.GPS_PROVIDER
            }else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                locationProvider = LocationManager.NETWORK_PROVIDER
            }else {
                MainHandler.getInstance().post({
                    listener.onFail("no available providers")
                })
                return@Runnable
            }
            val location = locationManager.getLastKnownLocation(locationProvider)
            locationManager.requestLocationUpdates(locationProvider, 1000, 1F, object : LocationListener{
                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

                }

                override fun onProviderEnabled(provider: String?) {

                }

                override fun onProviderDisabled(provider: String?) {

                }

                override fun onLocationChanged(location: Location?) {
                    MainHandler.getInstance().post({
                        listener.onSuccess(arrayOf(location!!.latitude, location.longitude))
                    })
                }

            })
            if (location != null) {
                MainHandler.getInstance().post({
                    listener.onSuccess(arrayOf(location.latitude, location.longitude))
                })
            }else {
                MainHandler.getInstance().post({
                    listener.onFail("no available location")
                })
            }
        }).start()
    }
}
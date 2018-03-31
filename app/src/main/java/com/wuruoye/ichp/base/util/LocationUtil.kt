package com.wuruoye.ichp.base.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import com.wuruoye.ichp.base.model.Listener

/**
 * Created by wuruoye on 2018/1/28.
 * this file is to
 */
object LocationUtil {

    @SuppressLint("MissingPermission")
    fun getLocation(context: Context, listener: Listener<Array<Double>>) {
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers = lm.getProviders(true)
        if (providers.size > 0) {
            val provider =
                    if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                        LocationManager.NETWORK_PROVIDER
                    }else if (providers.contains(LocationManager.GPS_PROVIDER)) {
                        LocationManager.GPS_PROVIDER
                    }else {
                        providers[0]
                    }
//            val location = lm.getLastKnownLocation(provider)
//            if (location != null) {
//                listener.onSuccess(arrayOf(location.latitude, location.longitude))
//            }
            lm.requestLocationUpdates(provider, 10, 10F, object : LocationListener {
                override fun onLocationChanged(p0: Location?) {
                    listener.onSuccess(arrayOf(p0!!.latitude, p0.longitude))
                }

                override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

                }

                override fun onProviderEnabled(p0: String?) {

                }

                override fun onProviderDisabled(p0: String?) {

                }

            })
        }else {
            listener.onFail("error")
        }
    }
}
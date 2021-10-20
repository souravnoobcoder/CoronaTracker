package com.example.coronatracker.funtions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat

import androidx.preference.PreferenceManager
import com.example.coronatracker.dataClasses.LocationData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class Location(val context: Context) {
    private var fusedLocationProviderClient
            : FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    init {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            AlertDialog.Builder(context)
                .setTitle("Location Permission Needed")
                .setMessage("This app needs the Location permission, please accept to use location functionality")
                .setPositiveButton(
                    "OK"
                ) { _, _ ->
                    //Prompt the user once explanation has been shown
                    requestLocationPermission()
                }
                .create()
                .show()
        }
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener {
                    location : Location ->
                val geocoder= Geocoder(context, Locale.getDefault())
                val listOfAddress:List<Address> =
                    geocoder.getFromLocation(location.latitude, location.longitude,1)
                val address=listOfAddress[0]
                val country=address.countryName
                val state= address.adminArea
                setLocation(country, state)
            }
    }

    /**
     * function for Requesting location permission by user
     */
    private fun requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ),
                MY_PERMISSIONS_REQUEST_LOCATION
            )
        } else {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_LOCATION
            )
        }
    }

    /**
     * @param country
     * @param state
     * is added to sharedPreference
     */
    private fun setLocation(country: String, state: String) {
        val sharedPreference =
            PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance())
        val editor = sharedPreference.edit()
        editor.putString(COUNTRY, country)
        editor.putString(STATE, state)
        editor.apply()
    }

    /**
     * @return LocationData object
     * which carries country and state name
     */
    fun getLocation(): LocationData {
        val sharedPreference =
            PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance())
        return LocationData(
            sharedPreference.getString(COUNTRY, "India").toString(),
            sharedPreference.getString(STATE, "Uttarakhand").toString()
        )
    }

    companion object {
        private const val COUNTRY = "COUNTRY"
        private const val STATE = "STATE"
        private const val MY_PERMISSIONS_REQUEST_LOCATION = 99

        /**
         * @return LocationData object
         * which carries country and state name
         */
        fun getLocation(): LocationData {
            val sharedPreference= PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance())
            return LocationData(
                sharedPreference.getString(COUNTRY, "India").toString(),
                sharedPreference.getString(STATE, "Uttarakhand").toString()
            )
        }
    }
}
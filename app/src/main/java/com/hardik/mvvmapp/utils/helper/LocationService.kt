package com.hardik.mvvmapp.utils.helper

import android.app.Activity
import android.content.Context
import android.location.Location
import android.os.Bundle
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import java.text.DateFormat
import java.util.Date

class LocationService(private val locationGet: LocationGet?) : com.google.android.gms.location.LocationListener,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {
  internal lateinit var context: Context
  private var mGoogleApiClient: GoogleApiClient? = null
  var location: Location? = null
    private set
  private var mLastUpdateTime: String? = null
  private var mLocationRequest: LocationRequest? = null
  var isOneTime: Boolean = false
  private var hasClientConfig = false
  private var interval = (9 * 1000).toLong()
  private var fastestInterval = (5 * 1000).toLong()
  private var fusedLocationProviderClient: FusedLocationProviderClient? = null

  private//            GooglePlayServicesUtil.getErrorDialog(status, context, 0).show();
  val isGooglePlayServicesAvailable: Boolean
    get() {
      val status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context)
      return if (ConnectionResult.SUCCESS == status) {
        true
      } else {
        println(TAG + "isGooglePlayServicesAvailable ...............: " + status)
        false
      }
    }

  private fun createLocationRequest() {
    mLocationRequest = LocationRequest()

    if (hasClientConfig) {
      mLocationRequest!!.interval = interval
    } else {
      mLocationRequest!!.interval = INTERVAL
    }

    if (hasClientConfig) {
      mLocationRequest!!.fastestInterval = fastestInterval
    } else {
      mLocationRequest!!.fastestInterval = FASTEST_INTERVAL
    }

    val smallestDisplacementMeters = 10f
    mLocationRequest!!.smallestDisplacement = smallestDisplacementMeters
    mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
  }

  fun setInterval(interval: Long) {
    hasClientConfig = true
    this.interval = interval
  }

  fun setFastestInterval(fastestInterval: Long) {
    hasClientConfig = true
    this.fastestInterval = fastestInterval
  }

  fun init(context: Context) {
    this.context = context
    println(TAG + "Init location service ...............................")

    //show error dialog if GoolglePlayServices not available
    if (!isGooglePlayServicesAvailable) {
      println(TAG + "isGooglePlayServicesAvailable..........false")
      return
    }

    createLocationRequest()
    mGoogleApiClient = GoogleApiClient.Builder(context)
        .addApi(LocationServices.API)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .build()

    mGoogleApiClient!!.connect()
  }

  private fun stop() {
    println(TAG + "onStop fired ..............")
    mGoogleApiClient!!.disconnect()
    println(TAG + "isConnected ...............: " + mGoogleApiClient!!.isConnected)
  }

  override fun onConnected(bundle: Bundle?) {
    println(
        TAG + "onConnected - isConnected ...............: " + mGoogleApiClient!!.isConnected
    )
    startLocationUpdates()
  }

  private fun startLocationUpdates() {

    try {

      fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

      fusedLocationProviderClient!!.lastLocation
          .addOnSuccessListener(context as Activity) { location ->
            // Got last known location. In some rare situations, this can be null.
            if (location != null) {
              // Logic to handle location object
              onLocationChanged(location)
            }
          }

//      val location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)
//      if (location == null) {
//        LocationServices.FusedLocationApi.requestLocationUpdates(
//            mGoogleApiClient, mLocationRequest,
//            this
//        )
//      } else {
//        onLocationChanged(location)
//      }
    } catch (se: SecurityException) {
      Log.e(TAG, "Go into settings and find Gps Tracker app and enable Location.")
    }

  }

  override fun onConnectionSuspended(i: Int) {
    //
  }

  override fun onConnectionFailed(connectionResult: ConnectionResult) {
    println(TAG + "Connection failed: " + connectionResult.toString())
  }

  override fun onLocationChanged(location: Location) {
    println(
        TAG + "Firing onLocationChanged.............................................."
    )

    this.location = location
    mLastUpdateTime = DateFormat.getTimeInstance()
        .format(Date())

    StaticDataUtility.currentLatitude = location.latitude
    StaticDataUtility.currentLongitude = location.longitude

//    preferenceHelper.setString(
//        "currentLatitude", location.latitude.toString()
//    )

//    preferenceHelper.setString(
//        "currentLongitude", location.longitude.toString()
//    )

    locationGet?.onLocationGet()

    if (isOneTime) {
      stopLocationUpdates()
      stop()
    }
  }

  private fun stopLocationUpdates() {
    LocationServices.FusedLocationApi.removeLocationUpdates(
        mGoogleApiClient, this
    )
    println(TAG + "Location update stopped .......................")
  }

  interface LocationGet {
    fun onLocationGet()
  }

  companion object {

    private const val TAG = "LocationActivity"
    private const val INTERVAL = (60 * 60 * 1000).toLong()
    private const val FASTEST_INTERVAL = (1000 * 60).toLong()
  }
}
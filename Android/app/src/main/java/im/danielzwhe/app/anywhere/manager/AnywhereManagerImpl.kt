package im.danielzwhe.app.anywhere.manager

import android.app.AlarmManager
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.SystemClock
import im.danielzwhe.app.anywhere.util.Logger
import java.math.BigDecimal

class AnywhereManagerImpl : AnywhereManager {

    private val providers = mutableSetOf<String>()

    private var locationManager: LocationManager? = null

    override fun init(context: Context) {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
    }

    override fun addLocationTestProvider(provider: String) {
        locationManager?.apply {
            try {
                addTestProvider(
                    provider,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    1,
                    2
                )
                setTestProviderEnabled(provider, true)
                providers.add(provider)
            } catch (e: RuntimeException) {
                Logger.e("add $provider failed cause by ${e.message}")
            }
        } ?: run {
            Logger.d("add $provider failed, because locationManager is null")
        }
    }

    override fun removeLocationTestProvider() {
        locationManager?.apply {
            try {
                providers.forEach {
                    removeTestProvider(it)
                }
            } catch (e: RuntimeException) {
                Logger.e("remove failed cause by ${e.message}")
            }
        } ?: run {
            Logger.d("remove all provider failed, because locationManager is null")
        }
    }

    override fun updateLocation(lng: BigDecimal, lat: BigDecimal) {
        locationManager?.apply {
            providers.forEach {
                try {
                    val mockLocation = dummyLocation(it, lng, lat)
                    setTestProviderLocation(it, mockLocation)
                } catch (e: RuntimeException) {
                    Logger.e("update failed cause by ${e.message}")
                }
            }
        }
    }

    override fun startMocking() {
        TODO("Not yet implemented")
    }

    override fun stopMocking() {
        TODO("Not yet implemented")
    }

    private fun dummyLocation(provider: String, lng: BigDecimal, lat: BigDecimal): Location {
        return Location(provider).apply {
            latitude = lat.toDouble()
            longitude = lng.toDouble()
            altitude = 3.0
            time = System.currentTimeMillis();
            speed = 0.01F
            bearing = 1F
            accuracy = 3F
            elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                bearingAccuracyDegrees = 0.1F
                verticalAccuracyMeters = 0.1F
                speedAccuracyMetersPerSecond = 0.01F
            }
        }
    }
}
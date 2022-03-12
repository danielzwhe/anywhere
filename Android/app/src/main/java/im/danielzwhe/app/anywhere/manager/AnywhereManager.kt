package im.danielzwhe.app.anywhere.manager

import android.content.Context
import java.math.BigDecimal

interface AnywhereManager {
    fun init(context:Context)
    fun addLocationTestProvider(provider: String)
    fun removeLocationTestProvider()
    fun updateLocation(lng: BigDecimal, lat: BigDecimal)
    fun startMocking()
    fun stopMocking()
}
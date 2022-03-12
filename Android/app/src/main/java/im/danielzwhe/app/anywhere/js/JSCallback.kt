package im.danielzwhe.app.anywhere.js

import java.math.BigDecimal

interface JSCallback {
    fun onLongClickMap(longitude: BigDecimal, latitude: BigDecimal)
    fun getLatitude(): Double
    fun getLongitude(): Double

}
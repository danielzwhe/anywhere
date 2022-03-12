package im.danielzwhe.app.anywhere.js

import android.webkit.JavascriptInterface
import im.danielzwhe.app.anywhere.extension.toBigDecimalOrZero

class JavaScriptInterface(
    private val jsCallback: JSCallback
) {

    /**
     * Set position in GUI. This method is called by javascript when there is a long press in the map.
     *
     * @param latLng String containing lat and lng
     * @return Void
     */
    @JavascriptInterface
    fun setPosition(latLng: String) {
        val lat = latLng.substring(latLng.indexOf('(') + 1, latLng.indexOf(','))
        val lng = latLng.substring(latLng.indexOf(',') + 2, latLng.indexOf(')'))
        jsCallback.onLongClickMap(lng.toBigDecimalOrZero(), lat.toBigDecimalOrZero())
    }

    /**
     * Get last latitude. This method is called by javascript at page load.
     *
     * @return The last latitude or 0 if it haven't been set.
     */
    @JavascriptInterface
    fun getLat(): Double {
        return jsCallback.getLatitude()
    }

    /**
     * Get last longitude. This method is called by javascript at page load.
     *
     * @return The last longitude or 0 if it haven't been set.
     */
    @JavascriptInterface
    fun getLng(): Double {
        return jsCallback.getLongitude()
    }

    companion object {
        const val NAME = "Android"
    }
}
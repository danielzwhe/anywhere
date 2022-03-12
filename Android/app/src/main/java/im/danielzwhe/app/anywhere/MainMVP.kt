package im.danielzwhe.app.anywhere

import android.content.Context
import java.math.BigDecimal

interface MainMVP {

    interface View {
        fun syncLocationToMap()
        fun getContext(): Context
        fun getLongitudeFromInputField(): BigDecimal
        fun getLatitudeFromInputField(): BigDecimal
        fun getRepeatTime(): Int
        fun getInterval(): Int
        fun showStopButton()
        fun hideStopButton()
        fun showSubmitButtonWithChanges()
        fun showSubmitButtonNoChanges()
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun init()
        fun applyLocation(lng: BigDecimal, lat: BigDecimal)
        fun cancelLocation()
        fun getCurrentLng(): Double
        fun getCurrentLat(): Double
        fun startTimer()
        fun onTimerTicking()
        fun onTimerFinished()
        fun stopTimer()
        fun isInfinite(): Boolean
    }
}
package im.danielzwhe.app.anywhere

import android.os.CountDownTimer
import im.danielzwhe.app.anywhere.manager.AnywhereManager
import im.danielzwhe.app.anywhere.manager.AnywhereManagerImpl
import java.math.BigDecimal

class MainPresenter : MainMVP.Presenter {

    private var view: MainMVP.View? = null

    private var anywhereManager: AnywhereManager? = null

    private var timer: CountDownTimer? = null

    private var currentLng: BigDecimal = BigDecimal.ZERO
    private var currentLat: BigDecimal = BigDecimal.ZERO

    private val KEEP_GOING = 0
    private var howManyTimes = 0
    private var interval = 0

    override fun attachView(view: MainMVP.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun init() {
        view?.apply {
            anywhereManager = AnywhereManagerImpl().also {
                it.init(getContext())
            }

        }
    }

    override fun applyLocation(lng: BigDecimal, lat: BigDecimal) {
        this.currentLng = lng
        this.currentLat = lat
        anywhereManager?.apply {
            addLocationTestProvider(LocationProviderType.NETWORK)
            addLocationTestProvider(LocationProviderType.GPS)
        }
        startTimer()
        view?.syncLocationToMap()
    }

    override fun cancelLocation() {
        anywhereManager?.apply {
            removeLocationTestProvider()
        }
        stopTimer()
    }

    override fun getCurrentLng(): Double = currentLng.toDouble()

    override fun getCurrentLat(): Double = currentLat.toDouble()

    override fun startTimer() {
        val intervalMills = if (interval <= 1) 1 * 1000L else interval * 1000L
        val totalMillis =
            if (howManyTimes == KEEP_GOING) Long.MAX_VALUE else howManyTimes * intervalMills
        timer?.cancel()
        timer = object : CountDownTimer(totalMillis, intervalMills) {
            override fun onTick(remains: Long) {
                onTimerTicking()
            }

            override fun onFinish() {
                onTimerFinished()
            }

        }
        timer?.start()
        view?.showStopButton()
    }

    override fun onTimerTicking() {
        anywhereManager?.apply {
            updateLocation(currentLng, currentLat)
        }
    }

    override fun onTimerFinished() {
        if (isInfinite()) {
            startTimer()
        }
    }

    override fun stopTimer() {
        timer?.cancel()
        timer = null
        view?.hideStopButton()
    }

    override fun isInfinite(): Boolean {
        return howManyTimes == KEEP_GOING
    }
}
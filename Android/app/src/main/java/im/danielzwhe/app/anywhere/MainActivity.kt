package im.danielzwhe.app.anywhere

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import im.danielzwhe.app.anywhere.extension.getBigDecimal
import im.danielzwhe.app.anywhere.extension.getInt
import im.danielzwhe.app.anywhere.js.JSCallback
import im.danielzwhe.app.anywhere.js.JavaScriptInterface
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal

class MainActivity : AppCompatActivity(), MainMVP.View, JSCallback {

    lateinit var presenter: MainMVP.Presenter

    private var webView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        presenter = MainPresenter().also {
            it.attachView(this)
            it.init()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mapContainer?.removeAllViews()
        webView = null
        presenter.apply {
            cancelLocation()
            detachView()
        }
    }

    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    private fun initView() {
        mapContainer?.apply {
            webView = WebView(this@MainActivity).apply {
                webChromeClient = WebChromeClient()
                addJavascriptInterface(
                    JavaScriptInterface(this@MainActivity),
                    JavaScriptInterface.NAME
                )
                settings.javaScriptEnabled = true
                settings.javaScriptCanOpenWindowsAutomatically = true
                loadUrl("file:///android_asset/mapbox.html")
            }
            addView(webView)
        }

        longitudeInputField?.doAfterTextChanged {
            showSubmitButtonWithChanges()
        }
        latitudeInputField?.doAfterTextChanged {
            showSubmitButtonWithChanges()
        }
        repeatTimeInputField?.doAfterTextChanged {
            showSubmitButtonWithChanges()
        }
        intervalTimeInputField?.doAfterTextChanged {
            showSubmitButtonWithChanges()
        }

        applyButton?.setOnClickListener {
            presenter.applyLocation(getLongitudeFromInputField(), getLatitudeFromInputField())
            showSubmitButtonNoChanges()
        }
        stopButton?.setOnClickListener {
            presenter.cancelLocation()
        }
    }

    override fun syncLocationToMap() {
        val lng = getLongitudeFromInputField()
        val lat = getLatitudeFromInputField()
        webView?.loadUrl("javascript:setOnMap($lat, $lng);")
    }

    override fun getContext(): Context = this.applicationContext

    override fun getLongitudeFromInputField(): BigDecimal = longitudeInputField.getBigDecimal()

    override fun getLatitudeFromInputField(): BigDecimal = latitudeInputField.getBigDecimal()

    override fun getRepeatTime(): Int = repeatTimeInputField.getInt()

    override fun getInterval(): Int = intervalTimeInputField.getInt()

    override fun onLongClickMap(longitude: BigDecimal, latitude: BigDecimal) {
        runOnUiThread {
            longitudeInputField?.apply {
                text = Editable.Factory.getInstance().newEditable(longitude.toPlainString())
            }
            latitudeInputField?.apply {
                text = Editable.Factory.getInstance().newEditable(latitude.toPlainString())
            }
        }
    }

    override fun getLatitude(): Double = presenter.getCurrentLat()

    override fun getLongitude(): Double = presenter.getCurrentLng()

    override fun showStopButton() {
        stopButton?.apply {
            visibility = View.VISIBLE
        }
    }

    override fun hideStopButton() {
        stopButton?.apply {
            visibility = View.GONE
        }
    }

    override fun showSubmitButtonWithChanges() {
        applyButton?.apply {
            setImageResource(R.drawable.ic_send)
        }
    }

    override fun showSubmitButtonNoChanges() {
        applyButton?.apply {
            setImageResource(R.drawable.ic_send_active)
        }
    }
}
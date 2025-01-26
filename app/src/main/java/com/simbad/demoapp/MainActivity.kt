package com.simbad.demoapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.simbad.demoapp.databinding.ActivityMainBinding
import org.prebid.mobile.AdSize
import org.prebid.mobile.Host
import org.prebid.mobile.PrebidMobile
import org.prebid.mobile.api.data.InitializationStatus
import org.prebid.mobile.api.exceptions.AdException
import org.prebid.mobile.api.rendering.BannerView
import org.prebid.mobile.api.rendering.listeners.BannerViewListener

class MainActivity : AppCompatActivity() {

    var adButton: Button? = null
    var SdkTAG = "simbad"
    var SdkOK = false

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        adButton = findViewById(R.id.button)
        adButton!!.setOnClickListener {
            if (SdkOK) {
                showAds320x50()
            }
        }
        initSDK()
    }

    // load ad to placement
    private fun showAds320x50() {

        // define ad placement
        val adUnit = BannerView(this, "mybl_android_slot4_commerce_home_320x50", AdSize(320, 50))

        // bidding
        binding.banner32050.addView(adUnit)

        // implement custom event callback
        adUnit.setBannerListener(object : BannerViewListener {
            override fun onAdLoaded(bannerView: BannerView?) {
                Toast.makeText(applicationContext, "onAdLoaded", Toast.LENGTH_LONG).show()
            }
            override fun onAdDisplayed(bannerView: BannerView?) {
                Toast.makeText(applicationContext, "onAdDisplayed", Toast.LENGTH_LONG).show()
            }
            override fun onAdFailed(bannerView: BannerView?, exception: AdException?) {
                Toast.makeText(applicationContext, "onAdFailed", Toast.LENGTH_LONG).show()
            }
            override fun onAdClicked(bannerView: BannerView?) {
                Toast.makeText(applicationContext, "onAdClicked", Toast.LENGTH_LONG).show()
            }
            override fun onAdClosed(bannerView: BannerView?) {
                Toast.makeText(applicationContext, "onAdClosed", Toast.LENGTH_LONG).show()
            }
        })

        // try load ad to placement
        adUnit.loadAd()
    }

    // Init SDK
    private fun initSDK() {
        // account name
        PrebidMobile.setPrebidServerAccountId("com.arena.banglalinkmela.app")
        // prebid address endpoint
        PrebidMobile.setPrebidServerHost(Host.createCustomHost("https://prebid.bangladsp.com/openrtb2/auction"))
        // prebid status endpoint
        PrebidMobile.setCustomStatusEndpoint("https://prebid.bangladsp.com/status")
        // timeout bidrequest
        PrebidMobile.setTimeoutMillis(100000)
        // share geo information
        PrebidMobile.setShareGeoLocation(true)
        // need use external browser for deep/app link resolve
        PrebidMobile.useExternalBrowser = true

        // init
        PrebidMobile.initializeSdk(applicationContext) { status ->
            if (status == InitializationStatus.SUCCEEDED) {
                SdkOK = true
                Log.d(SdkTAG, "initialized status: ok")
            } else {
                SdkOK = false
                Log.e(SdkTAG, "initialization status: $status\n${status.description}")
            }
        }
    }
}
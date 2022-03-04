package com.bob.wanandroid.webArticle.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
import androidx.fragment.app.FragmentActivity
import com.bob.base.ui.BaseActivity
import com.bob.wanandroid.R
import com.tencent.smtt.export.external.interfaces.IX5WebSettings
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.tencent.smtt.sdk.*
import kotlinx.android.synthetic.main.activity_web_article.*

class WebArticleActivity : BaseActivity() {

    companion object {
        private const val KEY_URL = "key_url"

        fun launch(activity: FragmentActivity, url: String) = activity.apply {
            val intent = Intent(this, WebArticleActivity::class.java)
            intent.putExtra(KEY_URL, url)
            startActivity(intent)
        }
    }

    override val layoutId: Int = R.layout.activity_web_article

    override fun initView() {
        initWebView()
    }

    override fun subscribeUi() {
        val url = intent.getStringExtra(KEY_URL)
        url?.let { wv.loadUrl(it) }
    }

    override fun onResume() {
        super.onResume()
        wv.onResume()
    }

    override fun onPause() {
        super.onPause()
        wv.onPause()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        wv.setBackgroundColor(Color.TRANSPARENT)
        wv.view.setBackgroundColor(Color.TRANSPARENT)
        wv.overScrollMode = WebView.OVER_SCROLL_NEVER
        wv.view.overScrollMode = WebView.OVER_SCROLL_NEVER
        val webSetting = wv.settings
        webSetting.allowFileAccess = true
        webSetting.setAppCacheEnabled(true)
        webSetting.cacheMode = WebSettings.LOAD_DEFAULT
        webSetting.domStorageEnabled = true
        webSetting.setGeolocationEnabled(true)
        webSetting.javaScriptEnabled = true
        webSetting.loadWithOverviewMode = true
        webSetting.setSupportZoom(true)
        webSetting.displayZoomControls = false
        webSetting.useWideViewPort = true
        webSetting.mixedContentMode = MIXED_CONTENT_ALWAYS_ALLOW
        CookieManager.getInstance().setAcceptThirdPartyCookies(wv, true)
        wv.settingsExtension?.apply {
            setContentCacheEnable(true)
            setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY)
        }

        wv.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
                return super.shouldInterceptRequest(view, request)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }
        }

        wv.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
            }
        }
    }
}
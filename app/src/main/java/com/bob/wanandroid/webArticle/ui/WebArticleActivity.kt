package com.bob.wanandroid.webArticle.ui

import android.annotation.SuppressLint
import android.content.Context
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

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        wv.setBackgroundColor(Color.TRANSPARENT)
        wv.overScrollMode = WebView.OVER_SCROLL_NEVER
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
//                if (view != null && request != null) {
//                    when {
//                        canAssetsResource(request) -> {
//                            return assetsResourceRequest(view.context, request)
//                        }
//                        canCacheResource(request) -> {
//                            return cacheResourceRequest(view.context, request)
//                        }
//                    }
//                }
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

    private fun canAssetsResource(webRequest: WebResourceRequest): Boolean {
        val url = webRequest.url.toString()
        return url.startsWith("file:///android_asset/")
    }

    private fun assetsResourceRequest(
        context: Context,
        webRequest: WebResourceRequest
    ): WebResourceResponse? {
        try {
            val url = webRequest.url.toString()
            val filenameIndex = url.lastIndexOf("/") + 1
            val filename = url.substring(filenameIndex)
            val suffixIndex = url.lastIndexOf(".")
            val suffix = url.substring(suffixIndex + 1)
            val webResourceResponse = WebResourceResponse()
            webResourceResponse.mimeType = getMimeTypeFromUrl(url)
            webResourceResponse.encoding = "UTF-8"
            webResourceResponse.data = context.assets.open("$suffix/$filename")
            webResourceResponse.responseHeaders = mapOf("access-control-allow-origin" to "*")
            return webResourceResponse
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun getMimeTypeFromUrl(url: String): String? {
        try {
            val extension = getExtensionFromUrl(url)
            if (extension.isNotBlank() && extension != "null") {
                if (extension == "json") {
                    return "application/json"
                }
                return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "*/*"
    }

    private fun getExtensionFromUrl(url: String): String {
        try {
            if (url.isNotBlank() && url != "null") {
                val extension = url
                    .substringBeforeLast('#') // Strip the fragment.
                    .substringBeforeLast('?') // Strip the query.
                    .substringAfterLast('/') // Get the last path segment.
                    .substringAfterLast('.', missingDelimiterValue = "") // Get the file extension.
                return MimeTypeMap.getFileExtensionFromUrl(extension)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}
package com.softbl.advokatmob

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.webkit.WebView
import android.webkit.WebViewClient
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fullscreen settings
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)

        // Standard settings
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.setSupportZoom(false)
        webView.settings.allowFileAccess = true
        webView.webViewClient = WebViewClient()

        // --- ADD DOWNLOAD LISTENER START ---
        webView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            try {
                val request = DownloadManager.Request(Uri.parse(url))

                // Use cookies so the DownloadManager can access the protected PDF
                val cookies = CookieManager.getInstance().getCookie(url)
                request.addRequestHeader("cookie", cookies)
                request.addRequestHeader("User-Agent", userAgent)

                request.setDescription("Preuzimanje fajla...")
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype))
                request.allowScanningByMediaScanner()
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

                // Save to the public Downloads folder
                request.setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    URLUtil.guessFileName(url, contentDisposition, mimetype)
                )

                val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                dm.enqueue(request)

                Toast.makeText(applicationContext, "Preuzimanje započeto...", Toast.LENGTH_SHORT).show()
                Toast.makeText(applicationContext, "Provjerite 'Downloads' folder", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Greška pri preuzimanju: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
        // --- ADD DOWNLOAD LISTENER END ---

        // ... inside onCreate, after webView initialization ...

        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(webView, true)

// This ensures cookies are saved to the device disk
        cookieManager.flush()

// Optional: For older Android versions, you might need this:
 android.webkit.CookieSyncManager.createInstance(this)
 android.webkit.CookieSyncManager.getInstance().startSync()


        webView.loadUrl("https://mobile.advokat-ostojic.com/")
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
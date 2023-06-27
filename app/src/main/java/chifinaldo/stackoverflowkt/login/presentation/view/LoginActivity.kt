package chifinaldo.stackoverflowkt.login.presentation.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import chifinaldo.stackoverflowkt.databinding.ActivityLoginBinding
import chifinaldo.stackoverflowkt.login.presentation.viewmodel.LoginViewModel
import chifinaldo.stackoverflowkt.profile.presentation.view.ProfileActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    private val stackOverflowClientId = "26611"
    private val stackOverflowRedirectUri = "https://chifinaldo.stackoverflowkt/stackoverkt"
    private val stackOverflowClientSecret = "U7hMElYMUP5FtGDqz9lV1Q(("

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initObservables()
        setupWebView()

        binding.button.setOnClickListener {
            openAuthUrl(getAuthUrl())
        }
    }

    override fun onResume() {
        super.onResume()
        val data: Uri? = intent.data
        if (data != null && data.fragment != null) {
            val code = extractAccessToken(data.toString())
            if (code != null) {
                viewModel.getAccessToken(code)
            } else {
                Log.e("LoginActivity", "Error al obtener el código de autorización")
            }
        }
    }

    private fun setupWebView() {
        binding.LoginWebView.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            domStorageEnabled = true
            setSupportMultipleWindows(false)
            CookieManager.getInstance().setAcceptThirdPartyCookies(binding.LoginWebView, true)
        }

        binding.LoginWebView.webViewClient = object : WebViewClient() {
            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null && url.contains("chifinaldo.stackoverflowkt")) {
                    val code = Uri.parse(url).getQueryParameter("code")

                    if (code != null) {
                        viewModel.getAccessToken(code)
                    }
                    return true
                }
                return false
            }
        }
        binding.LoginWebView.loadUrl(getAuthUrl())
    }

    private fun openAuthUrl(authUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(authUrl))
        startActivity(intent)
    }

    private fun initObservables() = with(viewModel) {
        redirectToHome.observe(this@LoginActivity) { redirectToHome(it) }
    }

    private fun getAuthUrl(): String {
        val authEndpoint = "https://stackoverflow.com/oauth/"
        val scope = "read_inbox,no_expiry"

        return "$authEndpoint?client_id=$stackOverflowClientId&redirect_uri=$stackOverflowRedirectUri&scope=$scope"
    }

    private fun redirectToHome(accessToken: String) {
        val intent = Intent(this@LoginActivity, ProfileActivity::class.java)
        intent.putExtra("accessToken", accessToken)

        startActivity(intent)
    }

    private fun extractAccessToken(url: String): String? {
        val accessTokenKeyword = "access_token="
        val ampersand = "&"

        val startIndex = url.indexOf(accessTokenKeyword)
        if (startIndex != -1) {
            val endIndex = url.indexOf(ampersand, startIndex)
            if (endIndex != -1) {
                return url.substring(startIndex + accessTokenKeyword.length, endIndex)
            }
        }
        return null
    }
}
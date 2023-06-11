package chifinaldo.stackoverflowkt.login.presentation.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import chifinaldo.stackoverflowkt.databinding.ActivityLoginBinding
import chifinaldo.stackoverflowkt.login.presentation.viewmodel.LoginViewModel
import chifinaldo.stackoverflowkt.login.presentation.viewmodel.WebViewInterface

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.setup()
        launchWebView()
    }

    fun launchWebView() {
        binding.apply {
            LoginWebView.settings.apply {
                javaScriptCanOpenWindowsAutomatically = true
                domStorageEnabled = true
                setSupportMultipleWindows(true)
            }
            LoginWebView.addJavascriptInterface(WebViewInterface(applicationContext), "Android")
            LoginWebView.loadUrl("https://stackoverflow.com/users/login")
        }
    }

}
package chifinaldo.stackoverflowkt.login.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import chifinaldo.stackoverflowkt.base.domain.domain.Result
import chifinaldo.stackoverflowkt.login.domain.service.LoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private var repository: LoginRepository = LoginRepository()

    private val scopeRecovery by lazy { CoroutineScope(Job() + Dispatchers.Main) }

    private val urlMLD: MutableLiveData<String> = MutableLiveData()
    val url get() = urlMLD as LiveData<String>

    private val redirectToHomeMLD: MutableLiveData<String> = MutableLiveData()
    val redirectToHome get() = redirectToHomeMLD as LiveData<String>

    fun setup() {
        scopeRecovery.launch {
            when (val result = repository.launchLogin()) {
                is Result.Success -> {
                    urlMLD.value = result.data
                    Log.d("Servicio:", "$result")
                }

                is Result.Error -> {
                    Log.d("Servicio:", "$result")
                }
            }
        }

    }

    fun getAccessToken(code: String) {
        scopeRecovery.launch {
            when (val result = repository.getAccessToken(code)) {
                is Result.Success -> {
                    Log.d("Servicio:", "$result")
                    redirectToHomeMLD.value = result.data.accessToken
                }

                is Result.Error -> {
                    Log.d("Servicio:", "$result")
                }
            }
        }
    }
}
package chifinaldo.stackoverflowkt.login.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import chifinaldo.stackoverflowkt.login.domain.service.LoginRepository
import chifinaldo.stackoverflowkt.base.domain.domain.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private var repository: LoginRepository = LoginRepository()

    private val scopeRecovery by lazy { CoroutineScope(Job() + Dispatchers.Main) }

    fun setup() {
        scopeRecovery.launch {
            when (val result = repository.launchLogin()) {
                is Result.Success -> {
                    Log.d("Servicio:", "$result")
                }
                is Result.Error -> {
                    Log.d("Servicio:", "$result")
                }
            }
        }

    }
}
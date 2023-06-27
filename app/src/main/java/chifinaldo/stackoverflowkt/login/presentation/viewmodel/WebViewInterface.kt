package chifinaldo.stackoverflowkt.login.presentation.viewmodel

import android.content.Context
import chifinaldo.stackoverflowkt.base.domain.domain.Result
import chifinaldo.stackoverflowkt.base.domain.service.BaseRepository
import chifinaldo.stackoverflowkt.login.domain.service.LoginRepository
import chifinaldo.stackoverflowkt.login.domain.service.LoginService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WebViewInterface(private val mContext: Context) :
    BaseRepository<LoginService>(LoginService::class.java) {

    private var repository: LoginRepository = LoginRepository()

    private val scopeRecovery by lazy { CoroutineScope(Job() + Dispatchers.Main) }

    fun setup() {
        scopeRecovery.launch {
            when (val result = repository.launchLogin()) {
                is Result.Success -> {
                }

                is Result.Error -> {
                }
            }
        }

    }
}
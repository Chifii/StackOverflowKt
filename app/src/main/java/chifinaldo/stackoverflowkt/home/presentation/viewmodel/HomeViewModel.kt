package chifinaldo.stackoverflowkt.home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import chifinaldo.stackoverflowkt.home.domain.service.HomeRepository
import chifinaldo.stackoverflowkt.base.domain.domain.Result
import chifinaldo.stackoverflowkt.home.domain.models.BadgesList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private var repository: HomeRepository = HomeRepository()

    private val scopeRecovery by lazy { CoroutineScope(Job() + Dispatchers.Main) }

    private var badgesListMDL: MutableLiveData<BadgesList> = MutableLiveData()
    val badgesList get() = badgesListMDL as LiveData<BadgesList>

    private var showButtonsMLD: MutableLiveData<Boolean> = MutableLiveData()
    val showButtons get() = showButtonsMLD as LiveData<Boolean>

    fun setup() {
        scopeRecovery.launch {
            when (val result = repository.getBadges()) {
                is Result.Success -> {
                    Log.d("Servicio:", "$result")

                    badgesListMDL.value = result.data
                }

                is Result.Error -> {
                    Log.d("Servicio:", "$result")
                }
            }
        }
    }

    fun configButtons() {
        if (showButtonsMLD.value == null) {
            showButtonsMLD.value = true
        } else {
            showButtonsMLD.value = !showButtonsMLD.value!!
        }
    }
}
package chifinaldo.stackoverflowkt.badges.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import chifinaldo.stackoverflowkt.badges.domain.models.BadgesList
import chifinaldo.stackoverflowkt.badges.domain.models.FilterManager
import chifinaldo.stackoverflowkt.badges.domain.service.BadgesRepository
import chifinaldo.stackoverflowkt.base.domain.domain.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BadgesViewModel : ViewModel() {

    private var repository: BadgesRepository = BadgesRepository()
    private val scopeRecovery by lazy { CoroutineScope(Job() + Dispatchers.Main) }
    private var lastFilters: FilterManager = FilterManager()

    private var badgesListMDL: MutableLiveData<BadgesList> = MutableLiveData()
    val badgesList get() = badgesListMDL as LiveData<BadgesList>

    private var showButtonsMLD: MutableLiveData<Boolean> = MutableLiveData()
    val showButtons get() = showButtonsMLD as LiveData<Boolean>

    private var showErrorToastMLD: MutableLiveData<Unit> = MutableLiveData()
    val showErrorToast get() = showErrorToastMLD as LiveData<Unit>

    fun setup() {
        scopeRecovery.launch {
            when (val result = repository.getBadges()) {
                is Result.Success -> {
                    Log.d("Home ViewModel:", "$result")
                    badgesListMDL.value = result.data
                }

                is Result.Error -> {
                    Log.d("Home ViewModel:", "$result")
                }
            }
        }
    }

    private fun applyFilters() {
        val currentFilters = lastFilters.createCombinedFilter()

        scopeRecovery.launch {
            when (val result = repository.getBadges(
                sort = currentFilters.second,
                order = currentFilters.first ?: true
            )) {
                is Result.Success -> {
                    Log.d("Home ViewModel:", "$result")

                    badgesListMDL.value = result.data
                }

                is Result.Error -> {
                    Log.d("Home ViewModel:", "$result")
                    showErrorToastMLD.value = Unit
                }
            }
        }
    }

    fun setSortFilter(sort: String) {
        lastFilters.applyFilters(sort = sort)
        applyFilters()
    }

    fun setOrderFilter(order: Boolean) {
        lastFilters.applyFilters(order = order)
        applyFilters()
    }

    fun configButtons() {
        if (showButtonsMLD.value == null) {
            showButtonsMLD.value = true
        } else {
            showButtonsMLD.value = !showButtonsMLD.value!!
        }
    }
}
package chifinaldo.stackoverflowkt.profile.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import chifinaldo.stackoverflowkt.base.domain.domain.Result
import chifinaldo.stackoverflowkt.profile.domain.models.UserBadge
import chifinaldo.stackoverflowkt.profile.domain.service.ProfileRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private var repository = ProfileRepository()
    private val scopeRecovery by lazy { CoroutineScope(Job() + Dispatchers.Main) }

    private val userBadgeInfoMLD = MutableLiveData<UserBadge>()
    val userBadgeInfo get() = userBadgeInfoMLD as LiveData<UserBadge>

    private val badgeInfoMLD = MutableLiveData<UserBadge>()
    val badgeInfo get() = badgeInfoMLD as LiveData<UserBadge>

    private val errorMLD = MutableLiveData<Boolean>()
    val error get() = errorMLD as LiveData<Boolean>

    private var showButtonsMLD: MutableLiveData<Boolean> = MutableLiveData()
    val showButtons get() = showButtonsMLD as LiveData<Boolean>

    fun getUserInfo(userId: String?) {
        if (userId.isNullOrEmpty()) {
            errorMLD.value = true
        } else {
            scopeRecovery.launch {
                when (val result = repository.getUserInfo(userId)) {
                    is Result.Success -> {
                        userBadgeInfoMLD.value = result.data
                    }

                    is Result.Error -> {
                        errorMLD.value = true
                    }
                }
            }
        }
    }

    private fun applyFilters(sort: String, userId: String) {
        userBadgeInfoMLD.value?.items?.let { items ->
            val filteredItems = items.filter { it.rank == sort }
            badgeInfoMLD.value = UserBadge(
                items = filteredItems
            )
        }
    }

    fun setSortFilter(sort: String) {
        applyFilters(sort, userBadgeInfo.value?.items?.get(0)?.user?.userId.toString())
    }

    fun configButtons() {
        if (showButtonsMLD.value == null) {
            showButtonsMLD.value = true
        } else {
            showButtonsMLD.value = !showButtonsMLD.value!!
        }
    }

}
package chifinaldo.stackoverflowkt.profile.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import chifinaldo.stackoverflowkt.profile.domain.service.ProfileRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import chifinaldo.stackoverflowkt.base.domain.domain.Result
import chifinaldo.stackoverflowkt.profile.domain.models.UserBadge

class ProfileViewModel : ViewModel() {

    private var repository = ProfileRepository()
    private val scopeRecovery by lazy { CoroutineScope(Job() + Dispatchers.Main) }

    private val userBadgeInfoMLD = MutableLiveData<UserBadge>()
    val userBadgeInfo get() = userBadgeInfoMLD as LiveData<UserBadge>

    private val errorMLD = MutableLiveData<Boolean>()
    val error get() = errorMLD as LiveData<Boolean>

    fun getUserInfo(userId: String?) {
        if (userId.isNullOrEmpty()) {
            errorMLD.value = true
        } else {
            scopeRecovery.launch {
                when (val result = repository.getUserInfo(userId)) {
                    is Result.Success -> {
                        Log.d("Home ViewModel:", "$result")
                        userBadgeInfoMLD.value = result.data
                    }

                    is Result.Error -> {
                        Log.d("Home ViewModel:", "$result")
                        errorMLD.value = true
                    }
                }
            }
        }
    }

}
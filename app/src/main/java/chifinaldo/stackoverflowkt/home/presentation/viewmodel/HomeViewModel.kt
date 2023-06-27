package chifinaldo.stackoverflowkt.home.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import chifinaldo.stackoverflowkt.base.domain.domain.Result
import chifinaldo.stackoverflowkt.home.domain.models.UserList
import chifinaldo.stackoverflowkt.home.domain.service.HomeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HomeRepository = HomeRepository()
) : ViewModel() {
    private val scopeRecovery by lazy { CoroutineScope(Job() + Dispatchers.Main) }

    private val usersListMDL: MutableLiveData<UserList> = MutableLiveData()
    val usersList get() = usersListMDL as LiveData<UserList>

    private val errorMLD = MutableLiveData<Boolean>()
    val error get() = errorMLD as LiveData<Boolean>

    fun searchUser(userName: String?) {
        if (userName.isNullOrEmpty()) {
            errorMLD.value = true
            return
        } else {
            scopeRecovery.launch {
                when (val result = repository.searchUser(userName = userName)) {
                    is Result.Success -> {
                        usersListMDL.value = result.data
                    }

                    is Result.Error -> {
                        errorMLD.value = true
                    }
                }
            }
        }
    }
}
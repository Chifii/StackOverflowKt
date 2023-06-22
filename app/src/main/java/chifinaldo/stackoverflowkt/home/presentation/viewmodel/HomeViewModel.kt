package chifinaldo.stackoverflowkt.home.presentation.viewmodel

import android.util.Log
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

class HomeViewModel : ViewModel() {
    private val repository: HomeRepository = HomeRepository()
    private val scopeRecovery by lazy { CoroutineScope(Job() + Dispatchers.Main) }

    private val usersListMDL: MutableLiveData<UserList> = MutableLiveData()
    val usersList get() = usersListMDL as LiveData<UserList>

    fun searchUser(userName: String?) {
        scopeRecovery.launch {
            when (val result = repository.searchUser(userName)) {
                is Result.Success -> {
                    Log.d("Home ViewModel:", "$result")
                    usersListMDL.value = result.data
                }

                is Result.Error -> {
                    Log.d("Home ViewModel:", "$result")
                }
            }
        }
    }
}
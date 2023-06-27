package chifinaldo.stackoverflowkt.home.domain.service

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import chifinaldo.stackoverflowkt.testutils.userListMock
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import chifinaldo.stackoverflowkt.base.domain.domain.Result
import chifinaldo.stackoverflowkt.testutils.MainDispatcherRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Rule
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class HomeRepositoryTest {

    @get:Rule
    private var mainDispatcherRule = MainDispatcherRule()

    @Mock
    private var service = Mockito.mock(HomeService::class.java)

    private lateinit var repository: HomeRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = Mockito.spy(HomeRepository())
        Dispatchers.setMain(mainDispatcherRule.testDispatcher)
    }

    @Test
    fun getUserSuccess() = runTest {
        val userName = "name"

        Mockito.`when`(
            service.searchUser(
                order = "desc",
                sort = "name",
                inName = userName,
                site = "stackoverflow"
            )
        ).thenReturn(userListMock)


        val result = repository.searchUser(userName)
        assertEquals((result as Result.Success).data, userListMock)

    }

    @After
    fun tearDown() {
        MockitoAnnotations.openMocks(this).close()
        Dispatchers.resetMain()
    }

}
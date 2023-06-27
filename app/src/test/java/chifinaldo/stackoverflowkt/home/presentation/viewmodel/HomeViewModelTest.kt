package chifinaldo.stackoverflowkt.home.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import chifinaldo.stackoverflowkt.home.domain.service.HomeRepository
import chifinaldo.stackoverflowkt.testutils.MainDispatcherRule
import chifinaldo.stackoverflowkt.testutils.genericResultSuccessMock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    private var mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    private var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private var repository = Mockito.mock(HomeRepository::class.java)

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = Mockito.spy(HomeViewModel(repository))
        Dispatchers.setMain(mainDispatcherRule.testDispatcher)
    }

    @Test
    fun searchUser() = runTest() {
        Mockito.`when`(
            repository.searchUser("name")
        ).thenReturn(genericResultSuccessMock)

        launch {
            viewModel.searchUser("name")
        }

        assertEquals(viewModel.usersList.value, genericResultSuccessMock.data)
    }
}
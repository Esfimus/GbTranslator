package com.esfimus.gbtranslator

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.esfimus.gbtranslator.view.interactor.MainInteractor
import com.esfimus.gbtranslator.view.viewmodel.MainViewModel
import com.esfimus.model.data.AppState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private lateinit var mainViewModel: MainViewModel

    @Mock
    private lateinit var interactor: MainInteractor

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        mainViewModel = MainViewModel(interactor)
    }

    @Test
    fun viewModel_Test() {
        val observer = Observer<AppState> {}
        val liveData = mainViewModel.subscribe()

        try {
            liveData.observeForever(observer)
            mainViewModel.getData("word", true)
            Assert.assertNotNull(liveData.value)
        } finally {
            liveData.removeObserver(observer)
        }
    }

    @Test
    fun viewModel_Error() {
        val observer = Observer<AppState> {}
        val liveData = mainViewModel.subscribe()
        val error = AppState.Loading(progress = null)

        try {
            liveData.observeForever(observer)
            mainViewModel.getData("word", false)
            Assert.assertEquals(liveData.value, error)
        } finally {
            liveData.removeObserver(observer)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun coroutines_Test() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<AppState> {}
            val liveData = mainViewModel.subscribe()

            try {
                liveData.observeForever(observer)
                mainViewModel.getData("word", true)
                Assert.assertNotNull(liveData.value)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun coroutines_Error() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<AppState> {}
            val liveData = mainViewModel.subscribe()
            val error = AppState.Loading(progress = null)

            try {
                liveData.observeForever(observer)
                mainViewModel.getData("word", false)
                Assert.assertEquals(liveData.value, error)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }
}
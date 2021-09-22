package network

import app.cash.turbine.test
import com.architectcoders.arquitectomarvel.ui.common.NetworkLogicViewModel
import com.architectcoders.usecases.ClearNetworks
import com.architectcoders.usecases.HandleNetworkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub
import kotlin.time.ExperimentalTime

@RunWith(MockitoJUnitRunner::class)
class NetworkLogicViewModelTest {

    @Mock
    private lateinit var handleNetworkManager: HandleNetworkManager

    @Mock
    private lateinit var clearNetworks: ClearNetworks

    private lateinit var networkLogicViewModel: NetworkLogicViewModel

    @Before
    fun setup() {
        networkLogicViewModel =
            NetworkLogicViewModel(
                Dispatchers.Unconfined,
                handleNetworkManager,
                clearNetworks
            )
    }

    @ExperimentalTime
    @Test
    fun `confirm if the first uiNetworkModel state is the Refresh value`() = runBlocking {
        networkLogicViewModel.uiNetworkModel.test {
            assertEquals(awaitItem(), NetworkLogicViewModel.UiNetworkModel.Refresh)
            cancelAndConsumeRemainingEvents()
        }
    }

    @ExperimentalTime
    @Test
    fun `confirm with success response from service`() = runBlocking {

        val lambdaCaptor = argumentCaptor<(Boolean) -> Unit>()

        handleNetworkManager.stub {
            onBlocking { invoke(lambdaCaptor.capture()) }.doReturn(Unit)
        }

        networkLogicViewModel.uiNetworkModel.test {
            lambdaCaptor.firstValue.invoke(true)
            assertEquals(
                (networkLogicViewModel.uiNetworkModel.value
                        as NetworkLogicViewModel.UiNetworkModel.SetNetworkAvailability).isAvailable,
                NetworkLogicViewModel.UiNetworkModel.SetNetworkAvailability(true).isAvailable
            )
            cancelAndConsumeRemainingEvents()
        }
    }
}

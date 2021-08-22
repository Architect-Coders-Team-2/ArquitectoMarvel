import app.cash.turbine.test
import com.architectcoders.arquitectomarvel.ui.common.NetworkLogicViewModel
import com.architectcoders.usecases.HandleNetworkManager
import com.architectcoders.usecases.UnregisterNetworkCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.time.ExperimentalTime

@RunWith(MockitoJUnitRunner::class)
class NetworkLogicViewModelTest {

    @Mock
    private lateinit var handleNetworkManager: HandleNetworkManager

    @Mock
    private lateinit var unregisterNetworkCallback: UnregisterNetworkCallback

    private lateinit var networkLogicViewModel: NetworkLogicViewModel

    @Before
    fun setup() {
        networkLogicViewModel =
            NetworkLogicViewModel(
                Dispatchers.Unconfined,
                handleNetworkManager,
                unregisterNetworkCallback
            )
    }

    @ExperimentalTime
    @Test
    fun `confirm if the first UiNetworkModel has the Refresh value`() = runBlocking {
        networkLogicViewModel.uiNetworkModel.test {
            assertEquals(awaitItem(), NetworkLogicViewModel.UiNetworkModel.Refresh)
            cancelAndConsumeRemainingEvents()
        }
    }
}

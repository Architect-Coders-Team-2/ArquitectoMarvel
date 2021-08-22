import com.architectcoders.data.repository.NetworkRepository
import com.architectcoders.usecases.UnregisterNetworkCallback
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class UnregisterNetworkCallbackTest {

    @Mock
    lateinit var networkRepository: NetworkRepository

    lateinit var unregisterNetworkCallback: UnregisterNetworkCallback

    @Before
    fun setUp() {
        unregisterNetworkCallback = UnregisterNetworkCallback(networkRepository)
    }

    @Test
    fun `verify if unregisterNetworkCallback is invoked`() {
        unregisterNetworkCallback.invoke()
        verify(networkRepository, times(1)).unregisterNetworkCallback()
    }
}

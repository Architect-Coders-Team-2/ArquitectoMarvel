import com.architectcoders.data.repository.NetworkRepository
import com.architectcoders.usecases.HandleNetworkManager
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class HandleNetworkManagerTest {

    @Mock
    lateinit var networkRepository: NetworkRepository

    @Mock
    lateinit var listener: (Boolean) -> Unit

    lateinit var handleNetworkManager: HandleNetworkManager

    @Before
    fun setUp() {
        handleNetworkManager = HandleNetworkManager(networkRepository)
    }

    @Test
    fun `verify handleNetworkManager is invoked`(): Unit = runBlocking {
        handleNetworkManager.invoke(listener)
        verify(networkRepository).handleNetworkManager(any())
    }
}

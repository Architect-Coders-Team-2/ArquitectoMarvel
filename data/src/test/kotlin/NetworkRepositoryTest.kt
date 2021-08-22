import com.architectcoders.data.repository.NetworkRepository
import com.architectcoders.data.source.NetworkDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class NetworkRepositoryTest {

    @Mock
    lateinit var listener: (Boolean) -> Unit

    @Mock
    lateinit var networkDataSource: NetworkDataSource

    lateinit var networkRepository: NetworkRepository

    @Before
    fun setUp() {
        networkRepository = NetworkRepository(networkDataSource)
    }

    @Test
    fun `verify if handleNetworkManager is invoked`(): Unit = runBlocking {
        networkRepository.handleNetworkManager(listener)
        verify(networkDataSource).handleNetworkManager(any())
    }

    @Test
    fun `verify if unregisterNetworkCallback is invoked`() {
        networkRepository.unregisterNetworkCallback()
        verify(networkDataSource).unregisterNetworkCallback()
    }
}

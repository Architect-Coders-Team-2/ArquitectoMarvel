import com.architectcoders.data.repository.NetworkRepository
import com.architectcoders.usecases.ClearNetworks
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class ClearNetworksTest {

    @Mock
    lateinit var networkRepository: NetworkRepository

    lateinit var clearNetworks: ClearNetworks

    @Before
    fun setUp() {
        clearNetworks = ClearNetworks(networkRepository)
    }

    @Test
    fun `verify if clearNetworks is invoked`() {
        clearNetworks.invoke()
        verify(networkRepository, times(1)).clearNetworks()
    }
}

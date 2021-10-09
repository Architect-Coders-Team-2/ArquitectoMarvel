import com.architectcoders.data.repository.BiometricRepository
import com.architectcoders.usecases.SaveAuthenticationState
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class SaveAuthenticationStateTest {

    @Mock
    lateinit var biometricRepository: BiometricRepository

    lateinit var saveAuthenticationState: SaveAuthenticationState

    @Before
    fun setUp() {
        saveAuthenticationState = SaveAuthenticationState(biometricRepository)
    }

    @Test
    fun `verify if saveAuthenticationState is invoked`() {
        saveAuthenticationState.invoke(true)
        verify(biometricRepository).saveAuthenticationState(true)
    }
}

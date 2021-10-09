import com.architectcoders.data.repository.BiometricRepository
import com.architectcoders.usecases.CheckAuthenticationState
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class CheckAuthenticationStateTest {

    @Mock
    lateinit var biometricRepository: BiometricRepository

    lateinit var checkAuthenticationState: CheckAuthenticationState

    @Before
    fun setUp() {
        checkAuthenticationState = CheckAuthenticationState(biometricRepository)
    }

    @Test
    fun `verify if checkAuthenticationState is invoked`() {
        checkAuthenticationState.invoke()
        verify(biometricRepository).checkAuthenticationState()
    }
}

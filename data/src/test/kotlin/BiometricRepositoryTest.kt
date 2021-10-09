import com.architectcoders.data.repository.BiometricRepository
import com.architectcoders.data.source.AuthenticationStateDataSource
import com.architectcoders.data.source.BiometricPromptDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class BiometricRepositoryTest {

    @Mock
    lateinit var firstListener: () -> Unit

    @Mock
    lateinit var secondListener: () -> Unit

    @Mock
    lateinit var authenticationStateDataSource: AuthenticationStateDataSource

    @Mock
    lateinit var biometricPromptDataSource: BiometricPromptDataSource

    lateinit var biometricRepository: BiometricRepository

    @Before
    fun setUp() {
        biometricRepository =
            BiometricRepository(authenticationStateDataSource, biometricPromptDataSource)
    }

    @Test
    fun `verify if checkAuthenticationState is invoked`() {
        biometricRepository.checkAuthenticationState()
        verify(authenticationStateDataSource).checkAuthenticationState()
    }

    @Test
    fun `verify if checkIfUserIsAlreadyAuthenticated is invoked`() {
        biometricRepository.checkIfUserIsAlreadyAuthenticated()
        verify(authenticationStateDataSource).checkIfUserIsAlreadyAuthenticated()
    }

    @Test
    fun `verify saveAuthenticationState is invoked`() {
        biometricRepository.saveAuthenticationState(true)
        verify(authenticationStateDataSource).saveAuthenticationState(true)
    }

    @Test
    fun `verify if canUserUseBiometricAuthentication is invoked`(): Unit = runBlocking {
        biometricRepository.canUserUseBiometricAuthentication()
        verify(biometricPromptDataSource).canUserUseBiometricAuthentication()
    }

    @Test
    fun `verify if setBiometricAuthentication is invoked`() {
        biometricRepository.setBiometricAuthentication(firstListener, secondListener)
        verify(biometricPromptDataSource).setBiometricAuthentication(any(), any())
    }
}

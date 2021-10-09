import com.architectcoders.data.repository.BiometricRepository
import com.architectcoders.usecases.CanUserUseBiometricAuthentication
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class CanUserUseBiometricAuthenticationTest {

    @Mock
    lateinit var biometricRepository: BiometricRepository

    lateinit var canUserUseBiometricAuthentication: CanUserUseBiometricAuthentication

    @Before
    fun setUp() {
        canUserUseBiometricAuthentication = CanUserUseBiometricAuthentication(biometricRepository)
    }

    @Test
    fun `verify if canUserUseBiometricAuthentication is invoked`() {
        canUserUseBiometricAuthentication.invoke()
        verify(biometricRepository).canUserUseBiometricAuthentication()
    }
}

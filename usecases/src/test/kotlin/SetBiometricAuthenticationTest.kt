import com.architectcoders.data.repository.BiometricRepository
import com.architectcoders.usecases.SetBiometricAuthentication
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class SetBiometricAuthenticationTest {

    @Mock
    lateinit var biometricRepository: BiometricRepository

    @Mock
    lateinit var listener1: () -> Unit

    @Mock
    lateinit var listener2: () -> Unit

    lateinit var setBiometricAuthentication: SetBiometricAuthentication

    @Before
    fun setUp() {
        setBiometricAuthentication = SetBiometricAuthentication(biometricRepository)
    }

    @Test
    fun `verify if setBiometricAuthentication is invoked`(): Unit = runBlocking {
        setBiometricAuthentication.invoke(listener1, listener2)
        verify(biometricRepository, times(1)).setBiometricAuthentication(listener1, listener2)
    }
}

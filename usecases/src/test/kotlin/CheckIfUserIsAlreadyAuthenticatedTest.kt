import com.architectcoders.data.repository.BiometricRepository
import com.architectcoders.usecases.CheckIfUserIsAlreadyAuthenticated
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class CheckIfUserIsAlreadyAuthenticatedTest {

    @Mock
    lateinit var biometricRepository: BiometricRepository

    lateinit var checkIfUserIsAlreadyAuthenticated: CheckIfUserIsAlreadyAuthenticated

    @Before
    fun setUp() {
        checkIfUserIsAlreadyAuthenticated = CheckIfUserIsAlreadyAuthenticated(biometricRepository)
    }

    @Test
    fun `verify if checkIfUserIsAlreadyAuthenticated is invoked`(): Unit = runBlocking {
        checkIfUserIsAlreadyAuthenticated.invoke()
        verify(biometricRepository).checkIfUserIsAlreadyAuthenticated()
    }
}

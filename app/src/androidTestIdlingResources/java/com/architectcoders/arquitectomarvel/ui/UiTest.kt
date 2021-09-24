package com.architectcoders.arquitectomarvel.ui

import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.data.database.RoomDataSource
import com.architectcoders.arquitectomarvel.data.server.RetrofitDataSource
import com.architectcoders.arquitectomarvel.di.AppModuleForEndPointBaseUrl
import com.architectcoders.arquitectomarvel.fakeModules.FakeCharacterDetailModuleForCharacterId
import com.architectcoders.arquitectomarvel.fakeModules.FakeDataModuleBinderForRetrofit
import com.architectcoders.arquitectomarvel.fakeModules.FakeDataModuleBinderForRoom
import com.architectcoders.arquitectomarvel.ui.common.EspressoIdlingResource
import com.architectcoders.arquitectomarvel.ui.main.MainActivity
import com.architectcoders.arquitectomarvel.mockWebServer.MockWebserverDispatcher
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.data.source.RemoteDataSource
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import java.lang.Thread.sleep
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@LargeTest
@UninstallModules(
    FakeDataModuleBinderForRoom::class,
    FakeDataModuleBinderForRetrofit::class,
    AppModuleForEndPointBaseUrl::class,
    FakeCharacterDetailModuleForCharacterId::class
)
@RunWith(AndroidJUnit4::class)
class UiTest {

    private val hiltRule = HiltAndroidRule(this)

    private lateinit var mockWebServer: MockWebServer

    @ExperimentalPagingApi
    @get:Rule
    val testRule: RuleChain =
        RuleChain
            .outerRule(hiltRule)
            .around(ActivityScenarioRule(MainActivity::class.java))

    @BindValue
    @JvmField
    @Named("baseUrl")
    var baseUrl: String = "http://localhost:8080"

    @BindValue
    @JvmField
    @Named("characterId")
    var characterId: Int = -1

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Before
    fun setUp() {
        hiltRule.inject()

        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = MockWebserverDispatcher().RequestDispatcher()
        mockWebServer.start(8080)

        val okHttp3IdlingResource =
            OkHttp3IdlingResource.create("OkHttp", okHttpClient)

        IdlingRegistry.getInstance()
            .register(okHttp3IdlingResource, EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun test1_findOnTheListTheSecondCharacterABombHAS_checkItIsTrue() {
        onView(withId(R.id.main_character_list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1,
                    scrollTo()
                )
            )
            .check(matches(hasDescendant(withText("A-Bomb (HAS)"))))
    }

    @Test
    fun test2_clickSecondCharacterABombHAS_NavigatesToItsDetail() {
        characterId = 1017100

        onView(withId(R.id.main_character_list)).check(matches(isDisplayed()))

        onView(withId(R.id.main_character_list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1,
                    click()
                )
            )

        onView(withId(R.id.toolbar))
            .check(matches(hasDescendant(withText("A-Bomb (HAS)"))))
    }

    @Test
    fun test3_withNoBiometricPattern_clickOnFAB_setPasswordAndHint_save() {
        onView(withId(R.id.favorite_fab)).perform(click())

        onView(withId(R.id.password))
            .perform(typeText("password"))
            .check(matches(withText("password")))

        onView(withId(R.id.repeat_password))
            .perform(scrollTo())
            .perform(typeText("password"))
            .check(matches(withText("password")))

        onView(isRoot()).perform(closeSoftKeyboard())

        onView(withId(R.id.recovery_hint))
            .perform(scrollTo())
            .perform(typeText("hint"))
            .check(matches(withText("hint")))

        onView(withId(android.R.id.button1)).perform(click())
    }

    @Test
    fun test4_disableInternet_checkIfCloudIconIsDisplayed_enableInternet_checkIfCloudIconIsGone() {
        InstrumentationRegistry.getInstrumentation().uiAutomation
            .executeShellCommand("svc wifi disable")
        InstrumentationRegistry.getInstrumentation().uiAutomation
            .executeShellCommand("svc data disable")

        sleep(1000)

        onView(withId(1)).check(matches(isDisplayed()))

        InstrumentationRegistry.getInstrumentation().uiAutomation
            .executeShellCommand("svc wifi enable")
        InstrumentationRegistry.getInstrumentation().uiAutomation
            .executeShellCommand("svc data enable")

        sleep(1000)

        onView(withId(1)).check(doesNotExist())
    }

    @Test
    fun test5_disableInternet_clickOnAIM_checkIfNoInternetMessageIsDisplayed_enableInternet_checkIfNoIntrntMsgIsGone() {
        characterId = 1009144

        InstrumentationRegistry.getInstrumentation().uiAutomation
            .executeShellCommand("svc wifi enable")
        InstrumentationRegistry.getInstrumentation().uiAutomation
            .executeShellCommand("svc data enable")

        onView(withId(R.id.main_character_list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    2,
                    click()
                )
            )

        onView(withId(R.id.offline_status)).check(matches(withEffectiveVisibility(Visibility.GONE)))

        InstrumentationRegistry.getInstrumentation().uiAutomation
            .executeShellCommand("svc wifi disable")
        InstrumentationRegistry.getInstrumentation().uiAutomation
            .executeShellCommand("svc data disable")

        sleep(1000)

        onView(withId(R.id.offline_status)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun test6_clickOn3DMan_clickOnCharacterFabButton_goBackToTheMainList_clickOnTheFabButton_checkIf3DManIsThere() {
        characterId = 1011334

        onView(withId(R.id.main_character_list)).check(matches(isDisplayed()))

        onView(withId(R.id.main_character_list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )

        onView(withId(R.id.fab)).perform(click())

        onView(withContentDescription("Navigate up")).perform(click())

        onView(withId(R.id.main_character_list)).check(matches(isDisplayed()))

        onView(withId(R.id.favorite_fab)).perform(click())

        onView(withId(R.id.favorite_character_list))
            .check(matches(hasDescendant(withText("3-D Man"))))
    }

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class DataModuleBinderForRoom {

        @Singleton
        @Binds
        abstract fun bindsRoomDataSource(roomDataSource: RoomDataSource): LocalDataSource
    }

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class DataModuleBinderForRetrofit {

        @Singleton
        @Binds
        abstract fun bindsRetrofitDataSource(retrofitDataSource: RetrofitDataSource): RemoteDataSource
    }
}

package com.brianmartone.comicdisplay

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.brianmartone.comicdisplay.di.MOCK_WEBSERVER_PORT
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
class ExampleInstrumentedTest {
    private lateinit var mockServer: MockWebServer
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityScenario: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        mockServer = MockWebServer()
        mockServer.enqueue(MockResponse().setBody("foo"))
        mockServer.start(MOCK_WEBSERVER_PORT)
        hiltRule.inject()
    }

    @After
    fun teardown() {
        mockServer.shutdown()
    }

    @Test
    fun appShouldCallMarvelApiOnStartup() {
        // When I start the app
        activityScenario.scenario

        // Then the app will call the Marvel API for a hardcoded comic ID
        val requestMade = mockServer.takeRequest()
        assertEquals("/v1/public/comics/79809?apikey=myPublicKey&ts=now&hash=nowmyPublicKeymySecretKey", requestMade.path)
    }
}

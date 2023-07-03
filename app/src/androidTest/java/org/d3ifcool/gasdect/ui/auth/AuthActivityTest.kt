package org.d3ifcool.gasdect.ui.auth

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Test
import org.d3ifcool.gasdect.R
import org.d3ifcool.gasdect.ui.MainActivity
import org.junit.Before


class AuthActivityTest {


    @Before
    fun setup() {
        ActivityScenario.launch(AuthActivity::class.java)
    }

    @Test
    fun registerTest() {
        onView(withId(R.id.tv_register)).perform(click())
        val email = "testo1@mail.com"
        val password = "123456"
        onView(withId(R.id.ed_email_reg)).perform(ViewActions.typeText(email))
        onView(withId(R.id.ed_password_reg)).perform(ViewActions.typeText(password))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.btn_register)).perform(click())

        onView(ViewMatchers.withId(R.id.tv_title))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun loginTest() {
        val email = "testo1@mail.com"
        val password = "123456"
        onView(withId(R.id.ed_email)).perform(ViewActions.typeText(email))
        onView(withId(R.id.ed_password)).perform(ViewActions.typeText(password))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.btn_login)).perform(click())

        ActivityScenario.launch(MainActivity::class.java)

        onView(ViewMatchers.withId(R.id.logout_button))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
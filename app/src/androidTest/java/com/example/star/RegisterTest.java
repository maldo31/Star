package com.example.star;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class RegisterTest {

    @Rule
    public ActivityScenarioRule<Register> mActivityTestRule = new ActivityScenarioRule<Register>(Register.class);

    private String mEmail = "maldok31@gmail.com";
    private String nPass = "tester";


    @Test
    public void pageObjectTest()
    {
//        Espresso.onView(withId(R.id.logoutBtn)).perform(click());
//        Espresso.onView(withId(R.id.createText)).perform(click());
        Espresso.onView(withId(R.id.editTextTextEmailAddress)).perform(typeText(mEmail));
        Espresso.onView(withId(R.id.editTextTextEmailAddress)).check(matches(withText(mEmail)));
        Espresso.onView(withId(R.id.editTextTextPassword2)).perform(typeText(nPass));
        Espresso.onView(withId(R.id.ConfirmPassword)).perform(typeText(nPass));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.registerBtn)).perform(click());


    }


}
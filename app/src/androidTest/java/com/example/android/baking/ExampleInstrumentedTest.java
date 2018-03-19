package com.example.android.baking;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;



/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private BakingMainActivity mainActivity;

    @Rule
    public ActivityTestRule<BakingMainActivity> mActivityRule = new ActivityTestRule<>(
            BakingMainActivity.class);

    @Before
    public void setmActivityRule(){
        mainActivity = mActivityRule.getActivity();
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.android.baking", appContext.getPackageName());
    }

    @Test
    public void button_click() throws Exception {
        mainActivity = mActivityRule.getActivity();
        //test recycle view
        Espresso.onView(withId(R.id.recipe_recycle)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
    }

}

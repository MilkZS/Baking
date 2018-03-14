package com.example.android.baking;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
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
        Espresso.onView(ViewMatchers.withId(R.id.recipe_recycle)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        /*
        onData(Matchers.allOf())
                .inAdapterView(ViewMatchers.withId(R.id.recipe_recycle)) // listview的id
                .atPosition(1)                              // 所在位置
                .onChildView(ViewMatchers.withId(R.id.steps_button))  // item中子控件id
                .perform(click());
*/
        //Espresso.onView(ViewMatchers.withId(R.id.recipe_recycle))
           //     .perform(RecyclerViewActions.scrollToPosition(0));
        //Espresso.onView(ViewMatchers.withId(R.id.steps_button)).perform(click());


    }

}

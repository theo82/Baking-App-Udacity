package theo.tziomakas.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.RelativeLayout;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import theo.tziomakas.bakingapp.network.RecipeLoader;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by theodosiostziomakas on 17/03/2018.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {

    public static final String RECIPE = "Graham Cracker crumbs";

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mMainActivityTestRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void checkText_MainActivity(){

        onView(ViewMatchers.withId(R.id.recipe_recycler_view)).perform(RecyclerViewActions.scrollToPosition(0));
        onView(withText("Nutella Pie")).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.recipe_recycler_view)).perform(RecyclerViewActions.scrollToPosition(1));
        onView(withText("Brownies")).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.recipe_recycler_view)).perform(RecyclerViewActions.scrollToPosition(2));
        onView(withText("Yellow Cake")).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.recipe_recycler_view)).perform(RecyclerViewActions.scrollToPosition(3));
        onView(withText("Cheesecake")).check(matches(isDisplayed()));
    }

    @Test
    public void clickRecyclerViewItem() {

        onView(withId(R.id.recipe_recycler_view)).perform(actionOnItemAtPosition(0,click()));

        onView(withId(R.id.recipe_detail_text)).check(matches(withText(containsString("unsalted butter, melted"))));

        onView(ViewMatchers.withId(R.id.steps_recycler_view)).perform(RecyclerViewActions.scrollToPosition(0));
        onView(withText("Recipe Introduction")).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.steps_recycler_view)).perform(RecyclerViewActions.scrollToPosition(1));
        onView(withText("Starting prep")).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.steps_recycler_view)).perform(RecyclerViewActions.scrollToPosition(2));
        onView(withText("Prep the cookie crust.")).check(matches(isDisplayed()));


    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}

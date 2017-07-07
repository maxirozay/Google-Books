package com.example.max.googlebooks;

import android.app.SearchManager;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;

import com.example.max.googlebooks.booklist.view.BookListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SearchableTest {

    private static final String QUERY = "query";
    private static final String PACKAGE_NAME = "com.example.max.googlebooks";

    // Instantiate an IntentsTestRule object.
    @Rule
    public IntentsTestRule<BookListActivity> mIntentsRule =
            new IntentsTestRule<>(BookListActivity.class);

    @Test
    public void search() throws Exception {
        search(QUERY);

        // Fail because the searchable view send 2 intents when it should send only one.
        intended(allOf(
                hasComponent(hasShortClassName(".view.BookListActivity")),
                toPackage(PACKAGE_NAME),
                hasExtra(SearchManager.QUERY, QUERY)));
    }

    public void search(String query) {
        onView(withId(R.id.search)).perform(click());

        KeyCharacterMap keymap = KeyCharacterMap.load(KeyCharacterMap.VIRTUAL_KEYBOARD);
        for (KeyEvent key : keymap.getEvents(query.toCharArray())) {
            getInstrumentation().sendKeySync(key);
        }

        getInstrumentation().sendCharacterSync(KeyEvent.KEYCODE_ENTER);
    }
}

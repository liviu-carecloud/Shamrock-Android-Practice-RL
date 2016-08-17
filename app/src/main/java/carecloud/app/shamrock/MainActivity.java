package carecloud.app.shamrock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

import carecloud.app.shamrock.model.Json;
import carecloud.app.shamrock.model.MainResponse;
import carecloud.app.shamrock.model.Option;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            // parse the json
            MainResponse objMainResponse =   readFileAndGetArray();
            Option[] langOptions = new Option[0];
            String screenName = (String) getTitle();
            if (objMainResponse != null) {
                // get the array of lang options
                Json selLangScreenJsonObj = objMainResponse.getScreens().get(0).getJson().get(0);
                langOptions = selLangScreenJsonObj.getLanguage().get(0).getOptionsArray();

                // get the screen name
                screenName = selLangScreenJsonObj.getScreenName();
            }


            // create the fragment and pass the lang options and the title
            SelectLanguageFragment fragment = new SelectLanguageFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArray(SelectLanguageFragment.LANG_OPTIONS, langOptions);
            bundle.putString(SelectLanguageFragment.SCREEN_TITLE, screenName);
            fragment.setArguments(bundle);

            // add select language fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_holder, fragment, null)
                    .commit();
        }
    }

    /**
     * Parses the json
     * @return The main model object
     */
    private MainResponse readFileAndGetArray() {
        try {
            String json;

            InputStream is = getAssets().open("deta.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            Log.e(LOG_TAG,json.toString());

            LanguageParser lParser= new LanguageParser();
            return lParser.getParsedObjectFromJSON(json);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}

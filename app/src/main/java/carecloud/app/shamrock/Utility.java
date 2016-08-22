package carecloud.app.shamrock;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Utils
 */
public class Utility {

    private static final String SHARED_PREF_SEL_LANG_KEY = "sel_lang";

    public static void saveSelectedLanguageId(Activity activity, int selLangId) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(SHARED_PREF_SEL_LANG_KEY, selLangId);
        editor.apply();
    }

    public static int retrieveSelectedLanguageId(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getInt(SHARED_PREF_SEL_LANG_KEY, Constants.LANG_EN);
    }

    // todo make function to extract all language codes
    /**
     * Maps language to its code as per json
     */
    public static int getLanguageCode(String l) {
        if(l.equals("Español")) {
            return Constants.LANG_ES;
        }
        return Constants.LANG_EN; // english id by default


    }
}
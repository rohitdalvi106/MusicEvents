package Util;

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs {
    SharedPreferences preferences;

    public Prefs(Activity activity) {
        preferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }


    //If user has n ot chose a city, return default!
    public String getZip() {
        return preferences.getString("zip", "06604");
    }

    public void setZip(String zip) {

        preferences.edit().putString("zip", zip).commit();
    }

}

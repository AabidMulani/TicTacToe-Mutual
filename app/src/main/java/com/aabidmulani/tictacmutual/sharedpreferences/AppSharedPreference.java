package com.aabidmulani.tictacmutual.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPreference {


    private static final String USER_EMAIL = "user_email";
    private final String APP_PREF = "tictac_app_preference";
    private final SharedPreferences sharedPreferences;

    private Context context;

    public AppSharedPreference(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
    }

    public void setUserEmail(String value) {
        sharedPreferences.edit().putString(USER_EMAIL, value).apply();
    }

    public String getUserEmail() {
        return sharedPreferences.getString(USER_EMAIL, null);
    }

    public String getUserEmailPath() {
        return sharedPreferences.getString(USER_EMAIL, null).replace(".", "").replace("@", "");
    }

}

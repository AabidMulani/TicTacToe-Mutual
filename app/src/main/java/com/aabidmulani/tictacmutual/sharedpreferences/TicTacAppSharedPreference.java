package com.aabidmulani.tictacmutual.sharedpreferences;

import android.content.SharedPreferences;

import com.aabidmulani.tictacmutual.utils.AppConstants;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class TicTacAppSharedPreference {

    private final SharedPreferences sharedPreferences;

    @Inject
    public TicTacAppSharedPreference(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void setUserEmail(String value) {
        sharedPreferences.edit().putString(AppConstants.PREF_USER_EMAIL, value).apply();
    }

    public String getUserEmail() {
        return sharedPreferences.getString(AppConstants.PREF_USER_EMAIL, null);
    }

    public String getUserEmailPath() {
        String email = sharedPreferences.getString(AppConstants.PREF_USER_EMAIL, null);
        if (email != null) {
            return email.replace(".", "").replace("@", "");
        }
        return null;
    }

}

package com.example.practicaltestfazal.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {

    private static final String PREFERENCE_NAME = "MY_PREF";
    private static final String IS_LOGIN = "isLogin";

    private final SharedPreferences _sharedPrefs;
    private SharedPreferences.Editor _prefsEditor;
    public Context context;

    public AppPreferences(Context context) {
        this.context = context;
        _sharedPrefs = context.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
    }

    public void setLoggedIn(String isLogin) {
        _prefsEditor = _sharedPrefs.edit();
        _prefsEditor.putString(IS_LOGIN, isLogin);
        _prefsEditor.apply();
    }

    public String isLoggedIn() {
        return _sharedPrefs.getString(IS_LOGIN, "False");
    }
}
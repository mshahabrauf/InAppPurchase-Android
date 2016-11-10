package com.appswallet.rosie.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

/**
 * Created by Muhammad Shahab on 7/29/16.
 */
public class SharedPreference {

    private static final String PREMIUM_UPGRADE = "PREMIUM_UPGRADE";
    private SharedPreferences mPrefs;
    private Context mContext;
    private String USER = "USER";

    public SharedPreference(Context mContext) {

        this.mContext = mContext;
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public boolean setUser()
    {
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(User.getInstance());
        prefsEditor.putString(USER, json);
        return prefsEditor.commit();
    }
    public User getUser()
    {
        Gson gson = new Gson();
        String json = mPrefs.getString(USER, "");
        return gson.fromJson(json, User.class);
    }
    public boolean setPremiumUpgrade(boolean premiumUpgrade)
    {
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putBoolean(PREMIUM_UPGRADE, premiumUpgrade);
        return prefsEditor.commit();
    }

    public boolean isPremiumUpgrade()
    {
        return mPrefs.getBoolean(PREMIUM_UPGRADE, false);
    }
}

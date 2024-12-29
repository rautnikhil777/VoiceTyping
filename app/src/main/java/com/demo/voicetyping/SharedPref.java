package com.demo.voicetyping;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPref {
    private static final String PREF_NAME = "SharedPref";
    public static SharedPref sSharedPref;
    int PRIVATE_MODE = 0;
    Context _context;
    SharedPreferences.Editor editor;
    SharedPreferences pref;

    private SharedPref(Context context) {
        Context applicationContext = context.getApplicationContext();
        this._context = applicationContext;
        SharedPreferences sharedPreferences = applicationContext.getSharedPreferences(PREF_NAME, this.PRIVATE_MODE);
        this.pref = sharedPreferences;
        this.editor = sharedPreferences.edit();
    }

    public static SharedPref getInstance(Context context) {
        if (sSharedPref == null) {
            sSharedPref = new SharedPref(context);
        }
        return sSharedPref;
    }

    public boolean getBooleanPref(String str, boolean z) {
        return this.pref.getBoolean(str, z);
    }

    public int getIntPref(String str, int i) {
        return this.pref.getInt(str, i);
    }

    public void savePref(String str, int i) {
        this.editor.putInt(str, i);
        this.editor.commit();
    }
}

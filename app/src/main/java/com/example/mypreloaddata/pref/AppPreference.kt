package com.example.mypreloaddata.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class AppPreference(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    var firstRun: Boolean?
        get() = prefs.getBoolean(APP_FIRST_RUN, true)
        set(input) {
            prefs.edit {
                putBoolean(APP_FIRST_RUN, input as Boolean)
            }
        }

    companion object {
        private const val PREF_NAME = "MahasiswaPref"
        private const val APP_FIRST_RUN = "app_first_run"
    }

}
package uz.khusinov.iqchallenge.utills

import android.content.Context
import android.content.SharedPreferences

object Pref {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var pref: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        pref = sharedPreferences
    }

    var isLogin: Boolean
        get() = pref.getBoolean("login", false)
        set(value) {
            pref.edit().putBoolean("login", value).apply()
        }

    var name: String
        get() = pref.getString("name", "")!!
        set(value) {
            pref.edit().putString("name", value).apply()
        }

    var id: String
        get() = pref.getString("id", "")!!
        set(value) {
            pref.edit().putString("id", value).apply()
        }

    var level: String
        get() = pref.getString("level", "")!!
        set(value) {
            pref.edit().putString("level", value).apply()
        }
}
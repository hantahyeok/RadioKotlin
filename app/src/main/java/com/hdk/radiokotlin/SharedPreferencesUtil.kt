package com.hdk.radiokotlin

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPreferencesUtil {

    private const val PREF_NAME = "MyPrefs"
    private const val KEY_ITEMS = "items"

    fun saveData(context: Context, items: List<ItemData>) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val gson = Gson()
        val jsonItems = gson.toJson(items)
        editor.putString(KEY_ITEMS, jsonItems)
        editor.apply()
    }

    fun loadData(context: Context): List<ItemData> {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val jsonItems = sharedPreferences.getString(KEY_ITEMS, null)
        val itemType = object : TypeToken<List<ItemData>>() {}.type
        return gson.fromJson(jsonItems, itemType) ?: emptyList()
    }
}

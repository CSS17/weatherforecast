package com.example.weatherforecast.sharedpreferences

import android.content.Context
import android.content.SharedPreferences

object UserSearchHistory {

    private const val SEARCH_HISTORY = "SEARCH HISTORY"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(SEARCH_HISTORY, Context.MODE_PRIVATE)
    }

    fun getSearchHistory(context: Context): MutableList<String> {
        val historySet = getSharedPreferences(context).getStringSet("searchHistory", setOf()) ?: setOf()
        return historySet.toMutableList()
    }

    fun addSearchTerm(context: Context, searchTerm: String) {
        val currentHistory = getSearchHistory(context)

        // En fazla 7 öğe saklanacak şekilde güncelleme
        if (currentHistory.size >= 7) {
            currentHistory.removeAt(0)
        }

        currentHistory.add(searchTerm)

        // SharedPreferences'a güncellenmiş geçmişi kaydet
        getSharedPreferences(context).edit().putStringSet("searchHistory", currentHistory.toSet()).apply()
    }

}
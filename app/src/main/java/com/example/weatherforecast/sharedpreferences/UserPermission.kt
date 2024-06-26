package com.example.weatherforecast.sharedpreferences

import android.content.Context
import android.content.SharedPreferences

object UserPermission {

    private const val PERMISSION = "PERMISSION"
    private const val FIRST_START_FLAG= "true"
    private const val RETURN_FLAG="false"
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PERMISSION, Context.MODE_PRIVATE)
    }

    fun savePermission(context: Context, permission: Boolean) {
        getSharedPreferences(context).edit().putString(PERMISSION, permission.toString()).apply()
    }

    fun saveFlagcontext(context: Context, permission: Boolean){
        getSharedPreferences(context).edit().putString(FIRST_START_FLAG, permission.toString()).apply()
    }

    fun saveReturnFlagcontext(context: Context, permission: Boolean){
        getSharedPreferences(context).edit().putString(RETURN_FLAG, permission.toString()).apply()
    }

    fun getReturnFlagcontext(context: Context):String?{
        return getSharedPreferences(context).getString(RETURN_FLAG,null)
    }

    fun getFlagcontext(context: Context):String?{
        return getSharedPreferences(context).getString(FIRST_START_FLAG,null)
    }

    fun getPermission(context: Context): String? {
        return getSharedPreferences(context).getString(PERMISSION, null)
    }



}
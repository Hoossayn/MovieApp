package com.example.movieapp.data.local.entities

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

@ProvidedTypeConverter
class ListConverter(private val gson: Gson) {

    @TypeConverter
    fun fromStringList(value: List<String>): String = gson.toJson(value)


    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }


}
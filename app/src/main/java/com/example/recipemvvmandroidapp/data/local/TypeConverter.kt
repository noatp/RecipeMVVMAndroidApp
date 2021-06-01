package com.example.recipemvvmandroidapp.data.local

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class TypeConverter{
    @TypeConverter
    fun fromListStringToJsonString(listString: List<String>?): String?{
        listString?.let{
            return Json.encodeToString(listString)
        }
        return null
    }

    @TypeConverter
    fun fromJsonStringToListString(string: String?): List<String>?{
        string?.let{
            return Json.decodeFromString(string)
        }
        return null
    }
}
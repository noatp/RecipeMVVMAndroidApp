package com.example.recipemvvmandroidapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipemvvmandroidapp.domain.model.Recipe

@Database(entities = [Recipe::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class RecipeRoomDatabase: RoomDatabase(){
    abstract fun recipeDAO(): RecipeDAO
}
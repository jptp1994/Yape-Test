package com.yape.cache.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.yape.cache.models.RecipeCacheIngredientEntity
import java.lang.reflect.Type

@ProvidedTypeConverter
class Converters {

        @TypeConverter
        fun fromTransactionList(transaction: List<RecipeCacheIngredientEntity?>?): String? {
            if (transaction == null) {
                return null
            }
            val gson = Gson()
            val type: Type = object : TypeToken<List<RecipeCacheIngredientEntity?>?>() {}.type
            return gson.toJson(transaction, type)
        }


        @TypeConverter
        fun toTransactionList(transactionString: String?): List<RecipeCacheIngredientEntity>? {
            if (transactionString == null) {
                return null
            }
            val gson = Gson()
            val type =
                object : TypeToken<List<RecipeCacheIngredientEntity?>?>() {}.type
            return gson.fromJson<List<RecipeCacheIngredientEntity>>(transactionString, type)
        }
}
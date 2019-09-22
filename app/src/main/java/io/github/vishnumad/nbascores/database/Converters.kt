package io.github.vishnumad.nbascores.database

import androidx.room.TypeConverter
import io.github.vishnumad.nbascores.di.Injector
import io.github.vishnumad.nbascores.remote.entities.PlayerStatline

/*
* Type converters for Room for saving non-primitive data types
*/
class Converters {

    @TypeConverter
    fun playerStatlinesToJson(statlines: List<PlayerStatline>?): String {
        return Injector.get().playerStatlineJsonAdapter().toJson(statlines)
    }

    @TypeConverter
    fun playerStatlineFromJson(statlinesJson: String): List<PlayerStatline>? {
        return Injector.get().playerStatlineJsonAdapter().fromJson(statlinesJson)
//            ?: throw IllegalArgumentException("Invalid Player Statline JSON: $statlinesJson")
    }
}
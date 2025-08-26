package hannah.bd.getitwrite.modals

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Stat::class, WIP::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun statDao(): StatDao
    abstract fun wipDao(): WIPDao
}
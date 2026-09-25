package hannah.bd.shelfify.modals

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hannah.bd.shelfify.modals.Converters
import hannah.bd.shelfify.modals.Stat
import hannah.bd.shelfify.modals.StatDao

@Database(entities = [Stat::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun statDao(): StatDao
}
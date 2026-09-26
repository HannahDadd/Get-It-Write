package hannah.bd.getitwrite.modals

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hannah.bd.getitwrite.modals.Converters
import hannah.bd.getitwrite.modals.Stat
import hannah.bd.getitwrite.modals.StatDao

@Database(entities = [Stat::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun statDao(): StatDao
}
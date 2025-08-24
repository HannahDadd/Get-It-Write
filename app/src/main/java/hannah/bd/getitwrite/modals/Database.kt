package hannah.bd.getitwrite.modals

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Stat::class, WIP::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun statDao(): StatDao
    abstract fun wipDao(): WIPDao
}
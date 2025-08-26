package hannah.bd.getitwrite.modals

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.TypeConverter
import java.util.Date

@Entity
data class Stat(
    @PrimaryKey val id: Int,
    val wordsWritten: Int,
    val date: Date,
    val wipId: Int?,
    val minutes: Int
)

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}

@Dao
interface StatDao {
    @Query("SELECT * FROM stat")
    fun getAll(): List<Stat>

    @Query("SELECT * FROM stat WHERE wipId = :wipId")
    fun getStatsForWIPId(wipId: Int): List<Stat>

    @Insert
    fun insertAll(stats: Array<Stat>)
}
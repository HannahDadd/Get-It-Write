package hannah.bd.getitwrite.modals

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity
data class WIP(
    @PrimaryKey val id: Int,
    val title: String,
    val count: Int,
    val goal: Int
)

@Dao
interface WIPDao {
    @Query("SELECT * FROM wip")
    fun getAll(): List<WIP>

    @Insert
    fun insertAll(wips: Array<WIP>)

    @Delete
    fun delete(wip: WIP)
}
package hannah.bd.shelfify.modals

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.datatransport.runtime.dagger.Provides
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object PreferencesKeys {
    val HAS_SEEN_ONBOARDING = booleanPreferencesKey("has_seen_onboarding")
    val WORDS_WRITTEN = intPreferencesKey("words_written")
}

private val Context.dataStore by preferencesDataStore("settings")

class UserPreferences(private val context: Context) {

    val hasSeenOnboarding: Flow<Boolean> =
        context.dataStore.data.map {
            it[PreferencesKeys.HAS_SEEN_ONBOARDING] ?: false
        }

    suspend fun completeOnboarding() {
        context.dataStore.edit {
            it[PreferencesKeys.HAS_SEEN_ONBOARDING] = true
        }
    }

    val wordsWritten: Flow<Int> =
        context.dataStore.data.map {
            it[PreferencesKeys.WORDS_WRITTEN] ?: 0
        }

    suspend fun updateWordCount(by: Int) {
        context.dataStore.edit {
            it[PreferencesKeys.WORDS_WRITTEN] = it[PreferencesKeys.WORDS_WRITTEN]?.plus(by) ?: by
        }
    }
}
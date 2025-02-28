package hannah.bd.getitwrite.views.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hannah.bd.getitwrite.MainApplication
import hannah.bd.getitwrite.modals.User
import hannah.bd.getitwrite.repository.FirebaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class ProfileUiState(
    val user: User,
    val bio: String,
    val sendAction: (ProfileActions) -> Unit
)

sealed class ProfileActions {
    data object SignOut : ProfileActions()
    data class ChangeName(val newName: String) : ProfileActions()
    data class ModifyBio(val newBio: String) : ProfileActions()
}

class ProfileViewModel(private val firebaseRepo: FirebaseRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ProfileUiState(
            user = User("", emptyMap()),
            bio = "",
            sendAction = ::handleAction
        )
    )
    val uiState: StateFlow<ProfileUiState> = _uiState

    private fun handleAction(actions: ProfileActions) {
        when(actions) {
            ProfileActions.SignOut -> signOut() // repository.signOut
            is ProfileActions.ChangeName -> changeName(actions.newName)
            is ProfileActions.ModifyBio -> modifyBio(actions.newBio)
        }
    }

    private fun signOut() {
        firebaseRepo.signOut()
    }

    private fun changeName(newName: String) {

    }

    private fun modifyBio(newBio: String) {
        _uiState.update { it.copy(bio = newBio) }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            val applicationKey = ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY
            initializer {
                val firebaseRepo = (this[applicationKey] as MainApplication).firebaseRepository
                ProfileViewModel(firebaseRepo)
            }
        }
    }
}

package com.raxors.photobooth.ui.screens.user

import androidx.lifecycle.SavedStateHandle
import com.raxors.photobooth.core.base.BaseViewModel
import com.raxors.photobooth.domain.AppRepository
import com.raxors.photobooth.domain.models.Relationship
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repo: AppRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<UserUiState, UserUiEvent>() {

    override fun initialState(): UserUiState = UserUiState(isLoading = true)

    private val userId = checkNotNull(savedStateHandle.get<String>("userId"))

    init {
        getUser()
    }

    fun onEvent(event: UserUiEvent) {
        when (event) {
            is UserUiEvent.OnAddUser -> {
                addUser()
            }

            is UserUiEvent.OnDeleteUser -> {
                deleteUser()
            }
        }
    }

    private fun getUser() {
        launch({
            val user = repo.getUser(userId)
            setState { copy(isLoading = false, user = user) }
        }, onError = {
            //TODO handle errors
        })
    }

    private fun addUser() {
        launch({
            repo.addUser(userId)
            setState { copy(user = this.user?.copy(relationship = Relationship.FRIEND)) }
        }, onError = {
            //TODO handle errors
        })
    }

    private fun deleteUser() {
        launch({
            repo.deleteUser(userId)
            setState { copy(user = this.user?.copy(relationship = Relationship.STRANGER)) }
        }, onError = {
            //TODO handle errors
        })
    }

}
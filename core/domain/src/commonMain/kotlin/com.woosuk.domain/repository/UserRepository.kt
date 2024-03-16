package com.woosuk.domain.repository

import com.woosuk.domain.model.ErrorState
import com.woosuk.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getCurrentUser(onError: (ErrorState) -> Unit): Flow<User>
}

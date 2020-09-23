package dev.mirror.kt.kaguya.domain

import java.util.*

data class User(
    val id: UUID,
    val name: String,
    val icon: String?,
)

data class PreRegisteredGroup(
    val members: List<User>
)

data class Group(
    val id: UUID,
    val members: List<User>,
)

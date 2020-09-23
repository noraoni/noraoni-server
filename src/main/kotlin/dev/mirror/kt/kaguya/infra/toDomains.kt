package dev.mirror.kt.kaguya.infra

import dev.mirror.kt.kaguya.domain.Group
import dev.mirror.kt.kaguya.domain.GroupMember
import dev.mirror.kt.kaguya.domain.Location
import dev.mirror.kt.kaguya.domain.User
import dev.mirror.kt.kaguya.infra.model.GroupEntity
import dev.mirror.kt.kaguya.infra.model.GroupMemberEntity
import dev.mirror.kt.kaguya.infra.model.LocationEntity
import dev.mirror.kt.kaguya.infra.model.UserEntity

fun UserEntity.toDomain(): User = User(
    this.id.value,
    this.name,
    this.icon
)

fun GroupEntity.toDomain(): Group = Group(
    this.id.value,
    this.members.map { it.toDomain() },
    this.joinId
)

fun GroupMemberEntity.toDomain(): GroupMember = GroupMember(
    this.member.toDomain(),
    this.isOni
)

fun LocationEntity.toDomain(): Location = Location(
    this.member.toDomain(),
    this.location
)

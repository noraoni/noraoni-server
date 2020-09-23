package dev.mirror.kt.kaguya.infra.model

import dev.mirror.kt.kaguya.infra.table.GroupMembers
import dev.mirror.kt.kaguya.infra.table.Groups
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class GroupEntity(id: EntityID<UUID>): UUIDEntity(id) {
    companion object: UUIDEntityClass<GroupEntity>(Groups)

    val members by UserEntity referrersOn  GroupMembers.group
}

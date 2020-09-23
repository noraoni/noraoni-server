package dev.mirror.kt.kaguya.domain.model

import dev.mirror.kt.kaguya.domain.table.GroupMembers
import dev.mirror.kt.kaguya.domain.table.Groups
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class Group(id: EntityID<UUID>): UUIDEntity(id) {
    companion object: UUIDEntityClass<Group>(Groups)

    val members by User referrersOn  GroupMembers.group
}

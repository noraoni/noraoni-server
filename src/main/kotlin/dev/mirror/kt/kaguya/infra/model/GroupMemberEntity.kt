package dev.mirror.kt.kaguya.infra.model

import dev.mirror.kt.kaguya.infra.table.GroupMembers
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class GroupMemberEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<GroupMemberEntity>(GroupMembers)

    var group by GroupEntity referencedOn GroupMembers.group
    var member by UserEntity referencedOn GroupMembers.member
}

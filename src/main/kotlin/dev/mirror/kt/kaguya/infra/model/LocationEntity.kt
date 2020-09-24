package dev.mirror.kt.kaguya.infra.model

import dev.mirror.kt.kaguya.infra.table.Locations
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class LocationEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<LocationEntity>(Locations)

    var member by GroupMemberEntity referencedOn Locations.member
    var x by Locations.x
    var y by Locations.y
}

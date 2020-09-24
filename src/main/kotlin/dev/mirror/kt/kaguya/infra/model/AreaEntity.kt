package dev.mirror.kt.kaguya.infra.model

import dev.mirror.kt.kaguya.infra.table.Areas
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class AreaEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<AreaEntity>(Areas)

    var group by GroupEntity referencedOn Areas.group
    var p1x by Areas.p1x
    var p1y by Areas.p1y
    var p2x by Areas.p2x
    var p2y by Areas.p2y
    var type by Areas.type
}

package dev.mirror.kt.kaguya.domain.model

import dev.mirror.kt.kaguya.domain.table.Users
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class User(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object: UUIDEntityClass<User>(Users)

    var name by Users.name
    var icon by Users.icon
}

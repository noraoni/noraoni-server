package dev.mirror.kt.kaguya.domain.table

import org.jetbrains.exposed.dao.id.UUIDTable

object Users : UUIDTable() {
    val name = varchar("name", 10)
    val icon = varchar("icon", 200).nullable()
}

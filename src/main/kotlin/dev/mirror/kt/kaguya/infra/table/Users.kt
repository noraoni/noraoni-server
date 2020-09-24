package dev.mirror.kt.kaguya.infra.table

import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val id = uuid("id").uniqueIndex()
    val name = varchar("name", 10)
    val icon = varchar("icon", 200).nullable()

    override val primaryKey = PrimaryKey(id)
}

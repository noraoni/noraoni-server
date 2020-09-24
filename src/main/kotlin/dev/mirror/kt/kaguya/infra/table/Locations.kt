package dev.mirror.kt.kaguya.infra.table

import org.jetbrains.exposed.sql.Table

object Locations : Table() {
    val id = uuid("id").uniqueIndex()
    val member = reference("member", GroupMembers.member)
    val x = double("location_x")
    val y = double("location_y")

    override val primaryKey = PrimaryKey(id)
}

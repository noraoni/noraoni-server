package dev.mirror.kt.kaguya.infra.table

import org.jetbrains.exposed.dao.id.UUIDTable

object Locations : UUIDTable() {
    val member = reference("member", GroupMembers)
    val x = double("location_x")
    val y = double("location_y")
}

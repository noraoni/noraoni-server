package dev.mirror.kt.kaguya.infra.table

import dev.mirror.kt.kaguya.domain.AreaType
import org.jetbrains.exposed.dao.id.UUIDTable

object Areas : UUIDTable() {
    val group = reference("group", Groups)

    val p1x = double("p1x")
    val p1y = double("p1y")
    val p2x = double("p2x")
    val p2y = double("p2y")

    val type = enumeration("type", AreaType::class)
}

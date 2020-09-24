package dev.mirror.kt.kaguya.infra.table

import dev.mirror.kt.kaguya.domain.AreaType
import org.jetbrains.exposed.sql.Table

object Areas : Table() {
    val id = uuid("id").uniqueIndex()

    val group = reference("group", Groups.id)

    val p1x = double("p1x")
    val p1y = double("p1y")
    val p2x = double("p2x")
    val p2y = double("p2y")

    val type = enumeration("type", AreaType::class)

    override val primaryKey = PrimaryKey(id)
}

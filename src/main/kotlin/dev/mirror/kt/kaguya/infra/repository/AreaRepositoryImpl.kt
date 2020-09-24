package dev.mirror.kt.kaguya.infra.repository

import dev.mirror.kt.kaguya.domain.AreaType
import dev.mirror.kt.kaguya.domain.Point
import dev.mirror.kt.kaguya.domain.repository.AreaRepository
import dev.mirror.kt.kaguya.infra.table.Areas
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class AreaRepositoryImpl : AreaRepository {
    override fun registerArea(groupId: UUID, p1: Point, p2: Point, type: AreaType) {
        transaction {
            Areas.insert {
                it[group] = groupId
                it[p1x] = p1.x
                it[p1y] = p1.y
                it[p2x] = p2.x
                it[p2y] = p2.y
                it[Areas.type] = type
            }
        }
    }
}

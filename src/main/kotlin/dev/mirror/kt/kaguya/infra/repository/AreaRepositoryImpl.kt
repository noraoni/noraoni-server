package dev.mirror.kt.kaguya.infra.repository

import dev.mirror.kt.kaguya.domain.AreaType
import dev.mirror.kt.kaguya.domain.Point
import dev.mirror.kt.kaguya.domain.repository.AreaRepository
import dev.mirror.kt.kaguya.infra.model.AreaEntity
import dev.mirror.kt.kaguya.infra.model.GroupEntity
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class AreaRepositoryImpl : AreaRepository {
    override fun registerArea(groupId: UUID, p1: Point, p2: Point, type: AreaType) {
        transaction {
            val group = GroupEntity.findById(groupId) ?: return@transaction

            AreaEntity.new {
                this.group = group
                this.p1x = p1.x
                this.p1y = p1.y
                this.p2x = p2.x
                this.p2y = p2.y
                this.type = type
            }
        }
    }
}

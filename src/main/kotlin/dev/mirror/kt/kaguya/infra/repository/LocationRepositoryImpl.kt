package dev.mirror.kt.kaguya.infra.repository

import dev.mirror.kt.kaguya.domain.Location
import dev.mirror.kt.kaguya.domain.Point
import dev.mirror.kt.kaguya.domain.repository.LocationRepository
import dev.mirror.kt.kaguya.infra.table.GroupMembers
import dev.mirror.kt.kaguya.infra.table.Groups
import dev.mirror.kt.kaguya.infra.table.Locations
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class LocationRepositoryImpl : LocationRepository {
    override fun updateLocation(memberId: UUID, point: Point) {
        transaction {
            val memberEntity =
                Groups.select { GroupMembers.member eq memberId }.singleOrNull() ?: return@transaction
            Locations.deleteWhere {
                Locations.member eq memberEntity[GroupMembers.id]
            }

            Locations.insert {
                it[member] = memberEntity[id]
                it[x] = point.x
                it[y] = point.y
            }
        }
    }

    override fun listLocations(): List<Location> {
        TODO()
    }
}

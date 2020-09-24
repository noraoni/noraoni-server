package dev.mirror.kt.kaguya.infra.repository

import dev.mirror.kt.kaguya.domain.Location
import dev.mirror.kt.kaguya.domain.Point
import dev.mirror.kt.kaguya.domain.repository.LocationRepository
import dev.mirror.kt.kaguya.infra.model.GroupMemberEntity
import dev.mirror.kt.kaguya.infra.model.LocationEntity
import dev.mirror.kt.kaguya.infra.table.GroupMembers
import dev.mirror.kt.kaguya.infra.table.Locations
import dev.mirror.kt.kaguya.infra.toDomain
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class LocationRepositoryImpl : LocationRepository {
    override fun updateLocation(memberId: UUID, point: Point) {
        transaction {
            val memberEntity =
                GroupMemberEntity.find { GroupMembers.member eq memberId }.singleOrNull() ?: return@transaction
            val preEntities = LocationEntity.find {
                Locations.member eq memberEntity.id
            }
            if (preEntities.empty()) {
                preEntities.forEach {
                    it.delete()
                }
            }

            LocationEntity.new(UUID.randomUUID()) {
                this.member = memberEntity
                this.x = point.x
                this.y = point.y
            }
        }
    }

    override fun listLocations(): List<Location> {
        return transaction {
            LocationEntity.all()
                .map { it.toDomain() }
        }
    }
}

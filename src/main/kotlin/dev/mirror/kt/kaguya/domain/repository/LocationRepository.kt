package dev.mirror.kt.kaguya.domain.repository

import dev.mirror.kt.kaguya.domain.Location
import dev.mirror.kt.kaguya.domain.Point
import java.util.*

interface LocationRepository {
    fun updateLocation(memberId: UUID, point: Point)
    fun listLocations(): List<Location>
}

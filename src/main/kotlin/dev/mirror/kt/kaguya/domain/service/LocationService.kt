package dev.mirror.kt.kaguya.domain.service

import dev.mirror.kt.kaguya.domain.repository.LocationRepository
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class LocationService(
    private val locationRepository: LocationRepository
) {
    fun nearParticipants(callback: () -> Unit) {
        val locations = locationRepository.listLocations()
        val oni = locations.find { it.member.isOni } ?: return
        val players = locations.filterNot { it.member.isOni }

        players.forEach { pl ->
            val diff = sqrt(abs(oni.point.x - pl.point.x).pow(2) + abs(oni.point.y - pl.point.y).pow(2))
            val y = abs(oni.point.y - pl.point.y)
        }
    }
}

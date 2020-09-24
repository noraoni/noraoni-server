package dev.mirror.kt.kaguya.domain.service

import dev.mirror.kt.kaguya.domain.GroupMember
import dev.mirror.kt.kaguya.domain.Point
import dev.mirror.kt.kaguya.domain.User
import dev.mirror.kt.kaguya.domain.repository.LocationRepository
import kotlin.math.pow
import kotlin.math.sqrt

class LocationService(
    private val locationRepository: LocationRepository
) {
    fun locationOverlap(): List<Pair<GroupMember, GroupMember>> {
        val locations = locationRepository.listLocations()
        val oni = locations.find { it.member.isOni }!!
        val players = locations.filterNot { it.member.isOni }

        val overlaps = mutableListOf<Pair<GroupMember, GroupMember>>()

        //緯度
        val a = 0.000089606
        //経度
        val b = 0.000011111
        val LIMIT = sqrt(a.pow(2) + b.pow(2))

        overlaps += players.filter {
            sqrt((it.point.x - oni.point.x).pow(2) + (it.point.y - oni.point.y).pow(2)) < LIMIT
        }.map { oni.member to it.member }

        return overlaps
    }

    fun updateLocation(member: User, loc: Point) {
        locationRepository.updateLocation(member.id, loc)
    }
}

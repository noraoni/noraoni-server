package dev.mirror.kt.kaguya.domain.repository

import dev.mirror.kt.kaguya.domain.AreaType
import dev.mirror.kt.kaguya.domain.Point
import java.util.*

interface AreaRepository {
    fun registerArea(groupId: UUID, p1: Point, p2: Point, type: AreaType)
}

package dev.mirror.kt.kaguya.domain.repository

import dev.mirror.kt.kaguya.domain.Group
import dev.mirror.kt.kaguya.domain.PreRegisteredGroup

interface GroupRepository {
    fun register(group: PreRegisteredGroup): Group
}

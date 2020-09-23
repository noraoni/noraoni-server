package dev.mirror.kt.kaguya.domain.service

import dev.mirror.kt.kaguya.domain.Group
import dev.mirror.kt.kaguya.domain.PreRegisteredGroup
import dev.mirror.kt.kaguya.domain.repository.GroupRepository
import java.util.*

class GroupService(
    private val userService: UserService,
    private val groupRepository: GroupRepository
) {
    fun register(host: UUID): Group {
        val hostUser = userService.find(host) ?: throw IllegalArgumentException()
        val group = PreRegisteredGroup(listOf(hostUser))
        return groupRepository.register(group)
    }
}

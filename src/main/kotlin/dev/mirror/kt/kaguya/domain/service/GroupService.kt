package dev.mirror.kt.kaguya.domain.service

import dev.mirror.kt.kaguya.domain.Group
import dev.mirror.kt.kaguya.domain.GroupMember
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

    fun join(joinId: Int, userId: UUID): Group? {
        return groupRepository.join(joinId, userId)
    }

    fun swapOni(oni: GroupMember, player: GroupMember) {
        groupRepository.changeMember(oni.copy(isOni = false))
        groupRepository.changeMember(player.copy(isOni = true))
    }
}

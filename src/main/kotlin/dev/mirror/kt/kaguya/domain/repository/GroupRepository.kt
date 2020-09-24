package dev.mirror.kt.kaguya.domain.repository

import dev.mirror.kt.kaguya.domain.Group
import dev.mirror.kt.kaguya.domain.GroupMember
import dev.mirror.kt.kaguya.domain.PreRegisteredGroup
import java.util.*

interface GroupRepository {
    fun register(group: PreRegisteredGroup): Group
    fun join(joinId: Int, userId: UUID): Group?
    fun changeMember(member: GroupMember): GroupMember?
}

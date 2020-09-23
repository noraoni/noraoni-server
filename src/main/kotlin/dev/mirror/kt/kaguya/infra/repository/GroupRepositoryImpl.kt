package dev.mirror.kt.kaguya.infra.repository

import dev.mirror.kt.kaguya.domain.Group
import dev.mirror.kt.kaguya.domain.PreRegisteredGroup
import dev.mirror.kt.kaguya.domain.repository.GroupRepository
import dev.mirror.kt.kaguya.infra.model.GroupEntity
import dev.mirror.kt.kaguya.infra.model.GroupMemberEntity
import dev.mirror.kt.kaguya.infra.model.UserEntity
import dev.mirror.kt.kaguya.infra.toDomain
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class GroupRepositoryImpl : GroupRepository {
    override fun register(group: PreRegisteredGroup): Group {
        return transaction {
            val groupEntity = GroupEntity.new {}

            val members = group.members
                .map {
                    UserEntity.findById(it.id)
                }

            for (member in members) {
                GroupMemberEntity.new(UUID.randomUUID()) {
                    this.group = groupEntity
                    this.member = member!!
                }
            }

            groupEntity.toDomain()
        }
    }
}

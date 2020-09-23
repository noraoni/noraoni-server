package dev.mirror.kt.kaguya.infra.repository

import dev.mirror.kt.kaguya.domain.Group
import dev.mirror.kt.kaguya.domain.PreRegisteredGroup
import dev.mirror.kt.kaguya.domain.repository.GroupRepository
import dev.mirror.kt.kaguya.infra.model.GroupEntity
import dev.mirror.kt.kaguya.infra.model.GroupMemberEntity
import dev.mirror.kt.kaguya.infra.model.UserEntity
import dev.mirror.kt.kaguya.infra.table.Groups
import dev.mirror.kt.kaguya.infra.toDomain
import org.jetbrains.exposed.sql.transactions.transaction
import java.security.SecureRandom
import java.util.*

class GroupRepositoryImpl : GroupRepository {
    companion object {
        private const val JOIN_ID_SIZE = 1000000
    }

    private val random = SecureRandom.getInstanceStrong()

    override fun register(group: PreRegisteredGroup): Group {
        return transaction {
            var joinId: Int
            do {
                joinId = random.nextInt(JOIN_ID_SIZE)
                val preEntity = GroupEntity.find { Groups.joinId eq joinId }
            } while (!preEntity.empty())

            val groupEntity = GroupEntity.new {
                this.joinId = joinId
            }

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

    override fun join(joinId: Int, userId: UUID): Group? {
        return transaction {
            val group = GroupEntity.find { Groups.joinId eq joinId }.singleOrNull() ?: return@transaction null
            val user = UserEntity.findById(userId) ?: return@transaction null

            GroupMemberEntity.new {
                this.group = group
                this.member = user
            }

            group.toDomain()
        }
    }
}

package dev.mirror.kt.kaguya.infra.repository

import dev.mirror.kt.kaguya.domain.Group
import dev.mirror.kt.kaguya.domain.GroupMember
import dev.mirror.kt.kaguya.domain.PreRegisteredGroup
import dev.mirror.kt.kaguya.domain.User
import dev.mirror.kt.kaguya.domain.repository.GroupRepository
import dev.mirror.kt.kaguya.infra.table.GroupMembers
import dev.mirror.kt.kaguya.infra.table.Groups
import dev.mirror.kt.kaguya.infra.table.Users
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
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
                val preEntity = Groups.select { Groups.joinId eq joinId }
            } while (!preEntity.empty())

            val groupId = Groups.insert {
                it[Groups.joinId] = joinId
            } get Groups.id

            val members = group.members
                .map {
                    it.id
                }

            for (member in members) {
                GroupMembers.insert {
                    it[id] = UUID.randomUUID()
                    it[this.group] = groupId
                    it[this.member] = member
                }
            }

            Group(
                groupId,
                group.members.map { GroupMember(it, false) },
                joinId
            )
        }
    }

    override fun join(joinId: Int, userId: UUID): Group? {
        return transaction {
            val group = Groups.select { Groups.joinId eq joinId }.singleOrNull() ?: return@transaction null
            val user = Users.select { Users.id eq userId }.singleOrNull() ?: return@transaction null

            GroupMembers.insert {
                it[this.group] = group[id]
                it[member] = user[id]
            }

            Group(
                group[Groups.id],
                Groups.innerJoin(GroupMembers)
                    .select { Groups.id eq group[Groups.id] }
                    .map {
                        GroupMember(
                            User(it[Users.id], it[Users.name], it[Users.icon]),
                            false
                        )
                    },
                joinId
            )
        }
    }
}

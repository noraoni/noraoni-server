package dev.mirror.kt.kaguya.infra.table

import org.jetbrains.exposed.sql.Table

object Groups : Table() {
    val id = uuid("id").uniqueIndex()
    val joinId = integer("join_id")

    override val primaryKey = PrimaryKey(id)
}

object GroupMembers : Table() {
    val id = uuid("id").uniqueIndex()

    val member = reference("member", Users.id).index()
    val group = reference("group", Groups.id).index()
    val isOni = bool("is_oni").default(false)

    override val primaryKey = PrimaryKey(id)
}

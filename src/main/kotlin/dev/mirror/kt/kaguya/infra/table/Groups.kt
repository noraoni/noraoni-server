package dev.mirror.kt.kaguya.infra.table

import org.jetbrains.exposed.dao.id.UUIDTable

object Groups: UUIDTable() {
}

object GroupMembers: UUIDTable() {
    val member = reference("member", Users).index()
    val group = reference("group", Groups).index()
}

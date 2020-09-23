@file:UseSerializers(UUIDSerializer::class)

package dev.mirror.kt.kaguya.domain

import dev.mirror.kt.kaguya.serialize.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.json.JsonObject
import java.util.*

@Serializable
data class User(
    val id: UUID,
    val name: String,
    val icon: String?,
)

data class PreRegisteredGroup(
    val members: List<User>,
)

@Serializable
data class Group(
    val id: UUID,
    val members: List<User>,
    val joinId: Int
)

@Serializable
data class WebSocketRequest(
    val type: Type,
    val arguments: JsonObject
) {
    @Serializable
    enum class Type {
        @SerialName("register_account")
        REGISTER_ACCOUNT,

        @SerialName("register_group")
        REGISTER_GROUP,

        @SerialName("join_group")
        JOIN_GROUP
    }
}

@Serializable
data class WebSocketResponse(
    val type: Type,
    val arguments: JsonObject
) {
    @Serializable
    enum class Type {
        @SerialName("success")
        SUCCESS,

        @SerialName("error")
        ERROR
    }
}

package dev.mirror.kt.kaguya

import dev.mirror.kt.kaguya.domain.WebSocketRequest
import dev.mirror.kt.kaguya.domain.WebSocketResponse
import dev.mirror.kt.kaguya.domain.repository.GroupRepository
import dev.mirror.kt.kaguya.domain.repository.UserRepository
import dev.mirror.kt.kaguya.domain.service.GroupService
import dev.mirror.kt.kaguya.domain.service.UserService
import dev.mirror.kt.kaguya.infra.repository.GroupRepositoryImpl
import dev.mirror.kt.kaguya.infra.repository.UserRepositoryImpl
import io.ktor.application.*
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.SendChannel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import java.util.*

val mainModule = module {
    single<GroupRepository> { GroupRepositoryImpl() }
    single<UserRepository> { UserRepositoryImpl() }
    single { GroupService(get(), get()) }
    single { UserService(get()) }
}

fun main() {
    embeddedServer(Netty, 8080) {
        install(WebSockets)
        install(Koin) {
            modules(mainModule)
        }

        routing {
            installApi()
        }
    }
}

fun Route.installApi() {
    val userService: UserService by application.inject()
    val groupService: GroupService by application.inject()

    webSocket("/") {
        for (frame in incoming) {
            if (frame !is Frame.Text) continue

            val req = Json.decodeFromString<WebSocketRequest>(frame.readText())
            when (req.type) {
                WebSocketRequest.Type.REGISTER_ACCOUNT -> registerAccount(userService, req.arguments, outgoing)
                WebSocketRequest.Type.REGISTER_GROUP -> registerGroup(groupService, req.arguments, outgoing)
                WebSocketRequest.Type.JOIN_GROUP -> joinGroup(groupService, req.arguments, outgoing)
            }
        }
    }
}

suspend fun registerAccount(service: UserService, arguments: JsonObject, outgoing: SendChannel<Frame>) {
    val name = arguments["name"]?.toString()
    if (name == null) {
        val res = WebSocketResponse(WebSocketResponse.Type.ERROR, buildJsonObject {
            put("content", "Name must not be null.")
        })
        outgoing.send(Frame.Text(Json.encodeToString(res)))
        return
    }
    val iconUrl = arguments["icon_url"]?.toString()

    val registered = service.register(name, iconUrl)
    outgoing.send(Frame.byType(false, FrameType.TEXT, Json.encodeToString(registered).toByteArray()))
}

suspend fun registerGroup(service: GroupService, arguments: JsonObject, outgoing: SendChannel<Frame>) {
    val uuid = arguments["host"]?.toString()
        ?.runCatching {
            UUID.fromString(this)
        }
    if (uuid == null || uuid.isFailure) {
        val res = WebSocketResponse(WebSocketResponse.Type.ERROR, buildJsonObject {
            put("content", "Host uuid invalid.")
        })
        outgoing.send(Frame.Text(Json.encodeToString(res)))
        return
    }

    val group = service.register(uuid.getOrNull()!!)
    outgoing.send(Frame.byType(false, FrameType.TEXT, Json.encodeToString(group).toByteArray()))
}

suspend fun joinGroup(service: GroupService, arguments: JsonObject, outgoing: SendChannel<Frame>) {
    val joinId = arguments["join_id"]?.jsonPrimitive?.intOrNull
    val userId = arguments["user_id"]?.toString()
        ?.runCatching {
            UUID.fromString(this)
        }
    if (joinId == null || userId == null || userId.isFailure) {
        val res = WebSocketResponse(WebSocketResponse.Type.ERROR, buildJsonObject {
            put("content", "Invalid join id or userId")
        })
        outgoing.send(Frame.Text(Json.encodeToString(res)))
        return
    }

    val group = service.join(joinId, userId.getOrNull()!!)
    outgoing.send(Frame.byType(false, FrameType.TEXT, Json.encodeToString(group).toByteArray()))
}

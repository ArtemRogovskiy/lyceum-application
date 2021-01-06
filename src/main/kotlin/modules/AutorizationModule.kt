package modules

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.sessions.*


data class UserSession(val name: String, val roles: Set<String> = emptySet()) : Principal
data class OriginalRequestURI(val uri: String)

@Suppress("unused")
fun Application.autorizationModule() {

    install(StatusPages) {
        exception<Exception> {
            call.response.status(HttpStatusCode.Forbidden)
        }
    }

    install(Sessions) {
        cookie<UserSession>("ktor_session_cookie", SessionStorageMemory())
        cookie<OriginalRequestURI>("original_request_cookie")
    }

    install(Authentication) {

        session<UserSession> {
            challenge {
                call.sessions.set(OriginalRequestURI(call.request.uri))
                call.respondRedirect("/login")
            }
            validate { session: UserSession ->
                session
            }
        }

    }


    routing {

//        get("/login") { call.showContent("login.ftl") }

        post("/login") {
            val params = call.receiveParameters()
            val username = params["username"]
            val password = params["password"]
            val role = params["role_id"]
            if (username != null && password == "secret") {
//                call.sessions.set(UserSession(username, role))
//                val redirectURL = call.sessions.get<OriginalRequestURI>()?.also {
//                    call.sessions.clear<OriginalRequestURI>()
//                }
//                call.respondRedirect(redirectURL?.uri ?: "/")
            } else {
                call.respondRedirect("/login")
            }
        }

        get("/logout") {
            call.sessions.clear<UserSession>()
            call.respondRedirect("/")
        }

        authenticate {
            get("/login-required") {
                call.respondRedirect("/")
            }
        }


        static("static") {
            resources("static")
        }
    }

}

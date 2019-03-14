package fr.iim.iwm.a5.kotlin

import com.sun.tools.javac.file.Locations
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.*
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.freemarker.FreeMarker
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.html.respondHtml
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.request.receiveMultipart
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.html.*
import java.util.*

class App

data class Article(val id: Int, val title: String, val text: String? = null)
data class Comment(val id: Int, val idArticle: Int, val text: String? = null)
data class IndexData(val articles: List<Article>)

val schema = "homestead"

fun Application.cmsApp(
    articleListController: ArticleListController,
    articleController: ArticleControllerImpl,
    commentController: CommentControllerImpl
) {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(App::class.java.classLoader, "templates")
    }
    install(DefaultHeaders)
    install(CallLogging)
    install(Authentication) {
        basic(name = "admin") {
            realm = "Ktor Server"
            validate { credentials ->
                if (credentials.name == "admin" && credentials.password == "root") {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
    }

    routing {
        authenticate("admin") {
            route("/admin") {
                get("/") {
                    val content = articleListController.startAdminHD()
                    call.respond(content)
                }

                get("/article/{articleId}") {
                    val articleId = call.parameters["articleId"]!!.toInt()
                    val content = articleController.startAdminHD(articleId)
                    call.respond(content)
                }

                post("/article/add") {
                    val requestBody = call.receiveParameters()
                    val title = requestBody["title"]
                    val text = requestBody["text"]
                    print(title)
                    print(text)
                    val result = articleController.createArticle(schema, "articles", title.toString(), text.toString())
                    print(result)
                    call.respondRedirect("/admin")
                    /*
                    val content = articleController.startAdminHD(idArticle.toInt())
                    call.respond(content)

                    val multipart = call.receiveMultipart()
                    var title = ""
                    var text = ""
                    while (true) {
                        val part = multipart.readPart() ?: break
                        when (part) {
                            is PartData.FormItem ->
                                if (part.name == "title") {
                                    title = part.value
                                }else if(part.name == "text"){
                                    text = part.value
                                }
                        }

                        part.dispose()
                    }
                    articleController.createArticle(title , text)
                    call.respondRedirect("/admin")*/
                }

                get("/article/{articleId}/delete") {
                    val articleId = call.parameters["articleId"]!!.toInt()
                    articleController.deleteArticle(schema, "articles", articleId)
                    call.respondRedirect("/admin")
                }

                get("/comment/{articleId}/{commentId}") {
                    val commentId = call.parameters["commentId"]!!.toInt()
                    val articleId = call.parameters["articleId"]!!.toInt()
                    commentController.deleteComment(schema, "comments", commentId)
                    call.respondRedirect("/article/$articleId")
                }
            }
          }

        get("/") {
            val content = articleListController.startHD()
            call.respond(content)
        }

        get("/article/{id}") {
            val id = call.parameters["id"]!!.toInt()
            val content = articleController.startHD(id)
            call.respond(content)
        }

        post("/comments/add") {
            val requestBody = call.receiveParameters()
            val text = requestBody["text"]
            val idArticle = requestBody["idArticle"]
            val result = commentController.createComment(schema, "comments", idArticle!!.toInt(), text.toString())
            print(result)
            val content = articleController.startHD(idArticle.toInt())
            call.respond(content)

        }
        post("/comments/delete") {
            val requestBody = call.receiveParameters()
            val idArticle = requestBody["idArticle"]
            val result = commentController.deleteComment(schema, "comments", idArticle!!.toInt())
            print(result)
            val content = articleController.startHD(idArticle.toInt())
            call.respond(content)

        }
    }
}

fun main() {
    val model = MysqlModel("jdbc:mysql://0.0.0.0:6000/$schema", "root", "root")

    val articleListController = ArticleListControllerImpl(model)
    val articleController = ArticleControllerImpl(model)
    val commentController = CommentControllerImpl(model)

    embeddedServer(Netty, 8888) {
        cmsApp(articleListController, articleController, commentController)
    }.start(true)
}
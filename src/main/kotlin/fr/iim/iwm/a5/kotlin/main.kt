package fr.iim.iwm.a5.kotlin

import freemarker.cache.ClassTemplateLoader
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.freemarker.FreeMarker
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.html.respondHtml
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.html.*

class App

data class IndexData(val articles: List<Article>)


fun main() {
    val model = MysqlModel()
    val articleListControler = ArticleListControler(model)
    val articleControler = ArticleControler(model)

    embeddedServer(Netty, 8080) {
        install(FreeMarker) {
            templateLoader = ClassTemplateLoader(App::class.java.classLoader, "templates")
        }

        routing {
            get("/") {
                val content = articleListControler.startFM()
                call.respond(content)

                /*val articles = model.getArticleList()
                 val str = buildString {
                     while (results.next()) {
                         val id = results.getInt("id")
                         val title = results.getString("title")
                         append("<p><a href='/article/$id'>$title</a></p>")
                     }
                 }

                // Response FMC
                //call.respond(FreeMarkerContent("index.ftl",IndexData(articles)))

                // Response HTML
                call.respondHtml {
                    head {
                        title("List des articles")
                    }
                    body {
                        articles.forEach {
                            p {
                                a(href = "/articles/${it.id}") {
                                    +it.title
                                }
                            }
                        }
                    }
                }

                // Response Text
                //call.respondText(str, ContentType.Text.Html)
                */
            }
            get("/articles/{id}") {
                val id = call.parameters["id"]!!.toInt()
                val content = articleControler.startFM(id)
                call.respond(content)
            }
        }
    }.start(true)
}

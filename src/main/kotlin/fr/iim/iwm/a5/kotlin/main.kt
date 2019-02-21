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

    embeddedServer(Netty, 8080) {
        install(FreeMarker) {
            templateLoader = ClassTemplateLoader(App::class.java.classLoader, "templates")
        }
        val connectionPool = ConnectionPool("jdbc:mysql://0.0.0.0:3306/homestead", "root", "secret")
        routing {
            get("/") {
                connectionPool.use { connection ->
                    val stmt = connection.prepareStatement("SELECT * FROM articles")
                    val results = stmt.executeQuery()

                    val articles = ArrayList<Article>()
                    while(results.next()){
                        articles.add(Article(results.getInt("id"), results.getString("title")))
                    }

                   /* val str = buildString {
                        while (results.next()) {
                            val id = results.getInt("id")
                            val title = results.getString("title")
                            append("<p><a href='/article/$id'>$title</a></p>")
                        }
                    }*/

                    // Response FMC
                    call.respond(FreeMarkerContent("index.ftl",IndexData(articles)))

                    // Response HTML
                    /*call.respondHtml{
                        head{
                            title("List des articles")
                        }
                        body{
                            articles.forEach{
                                p{
                                    a(href="/articcle/${it.id}") {
                                        +it.title
                                    }
                                }
                            }
                        }
                    }*/

                    // Response Text
                    //call.respondText(str, ContentType.Text.Html)
                }
            }
            get("/articles/{id}") {
                connectionPool.use { connection ->

                    val stmt = connection.prepareStatement("SELECT * FROM articles WHERE id = ?")
                    stmt.setString(1, call.parameters["id"])
                    val results = stmt.executeQuery()
                    val found = results.next()
                    if (found) {
                        val article = Article(
                                results.getInt("id"),
                                results.getString("title"),
                                results.getString("text")
                                )
                        //call.respondText("<h1>$title</h1><p>$text</p>", ContentType.Text.Html)
                        call.respond(FreeMarkerContent("article.ftl", mapOf("article" to article)))

                    }
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }
    }.start(true)
}

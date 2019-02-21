package fr.iim.iwm.a5.kotlin

import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.HttpStatusCode

class MysqlModel {

    val connectionPool = ConnectionPool("jdbc:mysql://0.0.0.0:3306/homestead", "root", "secret")


    fun getArticleList(): List<Article> {
        val articles = ArrayList<Article>()
        connectionPool.use { connection ->
            val stmt = connection.prepareStatement("SELECT * FROM articles")
            val results = stmt.executeQuery()

            while (results.next()) {
                articles.add(Article(results.getInt("id"), results.getString("title")))
            }
        }
        return articles
    }

    fun getArticle(id: Int): Article? {

        connectionPool.use { connection ->

            val stmt = connection.prepareStatement("SELECT * FROM articles WHERE id = ?")
            stmt.setInt(1, id)
            val results = stmt.executeQuery()
            val found = results.next()
            if (found) {
                return Article(
                    results.getInt("id"),
                    results.getString("title"),
                    results.getString("text")
                )
                //call.respondText("<h1>$title</h1><p>$text</p>", ContentType.Text.Html)
                //call.respond(FreeMarkerContent("article.ftl", mapOf("article" to article)))
            }
        }
            return null
            //call.respond(HttpStatusCode.NotFound)
    }
}
package fr.iim.iwm.a5.kotlin

import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.HttpStatusCode

class MysqlModel(val url: String, val user: String?, val password: String?): Model {

    val connectionPool = ConnectionPool(url, user, password)


    override fun getArticleList(): List<Article> {
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

    override fun getArticle(id: Int): Article? {
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
            }
        }
        return null
    }
}
package fr.iim.iwm.a5.kotlin

import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.HttpStatusCode

class MysqlModel(val url: String, val user: String?, val password: String?) : Model {

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

    // ARTICLES

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

    override fun createArticle(schema: String, table: String, title: String, text: String): Any {
        connectionPool.use { connection ->
            connection.prepareStatement("INSERT INTO $schema.$table(title, text) VALUES (?,?)").use { stmt ->
                stmt.setString(1, title)
                stmt.setString(2, text)
                stmt.executeUpdate()
            }
        }
        return true
    }
    override fun deleteArticle(schema: String, table: String, id: Int) {
        connectionPool.use { connection ->
            connection.prepareStatement("DELETE FROM comments where idArticle = ?").use { stmt ->
                stmt.setInt(1, id)
                stmt.executeUpdate()
            }
        }
        connectionPool.use { connection ->
            connection.prepareStatement("DELETE FROM articles where id = ?").use { stmt ->
                stmt.setInt(1, id)
                stmt.executeUpdate()
            }
        }
    }
   /* override fun createArticle(title: String, text: String) {
        connectionPool.use { connection ->
            connection.prepareStatement("INSERT INTO articles (title, text) VALUES (?, ?)").use { stmt ->
                stmt.setString(1, title)
                stmt.setString(2, text)
                stmt.executeUpdate()
            }
        }
    }

    override fun deleteArticle(id: Int) {
        connectionPool.use { connection ->
            connection.prepareStatement("DELETE FROM comments where idArticle = ?").use { stmt ->
                stmt.setInt(1, id)
                stmt.executeUpdate()
            }
        }
        connectionPool.use { connection ->
            connection.prepareStatement("DELETE FROM articles where id = ? ").use { stmt ->
                stmt.setInt(1, id)
                stmt.executeUpdate()
            }
        }
    }*/

    // COMMENTS

    override fun getComments(idArticle: Int): List<Comment> {
        val comments = ArrayList<Comment>()
        connectionPool.use { connection ->
            val stmt = connection.prepareStatement("SELECT * FROM comments WHERE idArticle = ?")
            stmt.setInt(1, idArticle)

            val results = stmt.executeQuery()
            while (results.next()) {
                comments.add(
                    Comment(
                        results.getInt("id"),
                        results.getInt("idArticle"),
                        results.getString("text")
                    )
                )
            }
        }
        return comments
    }

    override fun createComment(schema: String, table: String, idArticle: Int, text: String): Any {
        connectionPool.use { connection ->
            connection.prepareStatement("INSERT INTO $schema.$table(idArticle, text) VALUES (?, ?)").use { stmt ->
                stmt.setInt(1, idArticle)
                stmt.setString(2, text)
                stmt.executeUpdate()
            }
        }
        return true
    }
    override fun deleteComment(schema: String, table: String, idArticle: Int) {
        connectionPool.use { connection ->
            connection.prepareStatement("DELETE FROM comments where id = ?").use { stmt ->
                stmt.setInt(1, idArticle)
                stmt.executeUpdate()
            }
        }
    }
}
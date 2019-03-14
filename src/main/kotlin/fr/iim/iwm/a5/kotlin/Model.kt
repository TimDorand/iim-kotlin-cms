package fr.iim.iwm.a5.kotlin

interface Model {
    fun getArticleList(): List<Article>
    fun getArticle(id: Int): Article?
    fun getComments(idArticle: Int): List<Comment>
    fun createComment(schema: String, table: String, idArticle: Int, text: String): Any
    fun deleteComment(schema: String, table: String, idArticle: Int): Any

    fun createArticle(schema: String, table: String, title: String, text: String): Any
    fun deleteArticle(schema: String, table: String, id: Int): Any

}
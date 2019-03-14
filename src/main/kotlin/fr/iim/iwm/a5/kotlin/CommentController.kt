package fr.iim.iwm.a5.kotlin

interface CommentController {
    fun createComment(schema: String, table: String, idArticle: Int, text: String): Any
    fun deleteComment(schema: String, table: String, idArticle: Int): Any
}
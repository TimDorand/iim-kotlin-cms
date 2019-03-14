package fr.iim.iwm.a5.kotlin

import io.ktor.freemarker.FreeMarkerContent
import io.ktor.html.HtmlContent
import io.ktor.http.HttpStatusCode

interface ArticleController {
    //fun  startFM(id: Int): Any
    fun  startHD(id: Int): Any
    fun  startAdminHD(id: Int): Any

    fun createArticle(schema: String, table: String, title: String, text: String): Any
    fun deleteArticle(schema: String, table: String, id: Int): Any
}
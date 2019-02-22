package fr.iim.iwm.a5.kotlin

import io.ktor.freemarker.FreeMarkerContent
import io.ktor.html.HtmlContent
import io.ktor.http.HttpStatusCode

interface ArticleController {
    fun  startFM(id: Int): Any
    fun  startHD(id: Int): Any
}
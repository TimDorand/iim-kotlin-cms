package fr.iim.iwm.a5.kotlin

import io.ktor.freemarker.FreeMarkerContent
import io.ktor.html.HtmlContent
import io.ktor.http.HttpStatusCode

interface ArticleListController {
    fun  startFM(): Any
    fun  startHD(): Any
    fun  startAdminHD(): Any
}
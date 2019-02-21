package fr.iim.iwm.a5.kotlin

import io.ktor.freemarker.FreeMarkerContent
import io.ktor.html.HtmlContent
import io.ktor.http.HttpStatusCode

class ArticleListControler(private val model: MysqlModel) {
    // FreeMaker
    fun startFM(): Any {
        val articles = model.getArticleList()
        return FreeMarkerContent("index.ftl", mapOf("articles" to articles))
    }

    // html
    /*fun startHD(id: Int): Any {
        val article = model.getArticle(id)
        if(article !== null) return HtmlContent {articleTemplate(article)}
        return HttpStatusCode.NotFound
    }*/

}
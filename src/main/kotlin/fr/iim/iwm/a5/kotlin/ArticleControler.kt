package fr.iim.iwm.a5.kotlin

import io.ktor.freemarker.FreeMarkerContent
import io.ktor.html.HtmlContent
import io.ktor.http.HttpStatusCode

class ArticleControler(private val model: MysqlModel) {

    // FreeMaker
    fun startFM(id: Int): Any {
        val article = model.getArticle(id)
        if(article !== null) return FreeMarkerContent("article.ftl", article)
        return HttpStatusCode.NotFound
    }

    // html
    /*fun startHD(id: Int): Any {
        val article = model.getArticle(id)
        if(article !== null) return HtmlContent {articleTemplate(article)}
        return HttpStatusCode.NotFound
    }*/

}
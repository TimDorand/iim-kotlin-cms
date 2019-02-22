package fr.iim.iwm.a5.kotlin

import fr.iim.iwm.a5.kotlin.templates.ArticleTemplate
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.html.HtmlContent
import io.ktor.http.HttpStatusCode

class ArticleControllerImpl(private val model: Model) : ArticleController {

    override fun startFM(id: Int): Any {
        val article = model.getArticle(id)
        if (article !== null) {
            return FreeMarkerContent("article.ftl", article)
        }
        return HttpStatusCode.NotFound
    }

    override fun startHD(id: Int): Any {
        val article = model.getArticle(id)
        if (article !== null) {
            return HtmlContent { ArticleTemplate(article) }
        }
        return HttpStatusCode.NotFound
    }
}
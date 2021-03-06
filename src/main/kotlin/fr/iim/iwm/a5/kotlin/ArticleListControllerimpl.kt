package fr.iim.iwm.a5.kotlin

import fr.iim.iwm.a5.kotlin.templates.admin.adminIndexTemplate
import fr.iim.iwm.a5.kotlin.templates.indexTemplate
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.html.HtmlContent
import io.ktor.http.HttpStatusCode

class ArticleListControllerImpl(private val model: Model) : ArticleListController {

    override fun startFM(): Any {
        val articles = model.getArticleList()
        if (articles !== null) {
            return FreeMarkerContent("index.ftl", articles)
        }
        return HttpStatusCode.NotFound
    }

    override fun startHD(): Any {
        val articles = model.getArticleList()
        if (articles !== null) {
            return HtmlContent { indexTemplate(articles) }
        }
        return HttpStatusCode.NotFound
    }
    override fun startAdminHD(): Any {
        val articles = model.getArticleList()
        if (articles !== null) {
            return HtmlContent { adminIndexTemplate(articles) }
        }
        return HttpStatusCode.NotFound
    }
}
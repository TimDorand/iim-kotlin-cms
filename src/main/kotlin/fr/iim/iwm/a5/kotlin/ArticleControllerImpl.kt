package fr.iim.iwm.a5.kotlin

import fr.iim.iwm.a5.kotlin.templates.ArticleTemplate
import fr.iim.iwm.a5.kotlin.templates.admin.adminArticleTemplate
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.html.HtmlContent
import io.ktor.http.HttpStatusCode

class ArticleControllerImpl(private val model: Model) : ArticleController {

  /*  override fun startFM(id: Int): Any {
        val article = model.getArticle(id)
        if (article !== null) {
            return FreeMarkerContent("article.ftl", article)
        }
        return HttpStatusCode.NotFound
    }
*/
    override fun startHD(id: Int): Any {
        val article = model.getArticle(id)
        val comments = model.getComments(id)
        if (article !== null) {
            return HtmlContent { ArticleTemplate(article, comments) }
        }
        return HttpStatusCode.NotFound
    }
    override fun startAdminHD(id: Int): Any {
        val article = model.getArticle(id)
        val comments = model.getComments(id)
        if (article !== null) {
            return HtmlContent { adminArticleTemplate(article, comments) }
        }
        return HttpStatusCode.NotFound
    }

    override fun createArticle(schema: String, table: String, title: String, text: String): Any {
        val article = model.createArticle(schema, table, title, text)
        return article
    }

    override fun deleteArticle(schema: String, table: String, id: Int): Any {
        val article = model.deleteArticle(schema, table, id)
        return article
    }

}
package fr.iim.iwm.a5.kotlin


class CommentControllerImpl(private val model: Model) : CommentController {

    override fun createComment(schema: String, table: String, idArticle: Int, text: String): Any {
        val comments = model.createComment(schema, table, idArticle, text)
        return comments
    }
    override fun deleteComment(schema: String, table: String, idArticle: Int): Any {
        val comments = model.deleteComment(schema, table, idArticle)
        return comments
    }

//    override fun createComment(idArticle: Int, text: String?): Any {
//   }
  /*  override fun startFM(id: Int): Any {
        val article = model.getArticle(id)
        if (article !== null) {
            return FreeMarkerContent("article.ftl", article)
        }
        return HttpStatusCode.NotFound
    }

    override fun startHD(id: Int): Any {
        val article = model.getArticle(id)
        val comments = model.getComments(id)
        if (article !== null) {
            return HtmlContent { ArticleTemplate(article, comments) }
        }
        return HttpStatusCode.NotFound
    }
    */
}
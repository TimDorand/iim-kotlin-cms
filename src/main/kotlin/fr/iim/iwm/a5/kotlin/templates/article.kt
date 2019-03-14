package fr.iim.iwm.a5.kotlin.templates

import fr.iim.iwm.a5.kotlin.Article
import fr.iim.iwm.a5.kotlin.Comment
import kotlinx.html.*

fun HTML.ArticleTemplate(article: Article, comments: List<Comment>) {
    head {
        title("Article")
    }
    body {
        a { href="/"; +"Liste des articles"}
        h1 { +article.title }
        p { +article.text!! }

        hr()
        h2 { +"Commentaires" }

        ul {
            comments.forEach {
                li {
                    +it.text!!
                }
            }
        }
        hr()
        h3 { +"Ajouter un commentaire sur l'article " ; +article.title }
        form(action = "/comments/add", encType = FormEncType.multipartFormData,
            method = FormMethod.post) {
            hiddenInput { name = "idArticle"; value = article.id.toString() }
            textInput { name = "text" }
            submitInput { value = "Envoyer" }
        }
    }
}
package fr.iim.iwm.a5.kotlin.templates.admin

import fr.iim.iwm.a5.kotlin.Article
import kotlinx.html.*

fun HTML.adminIndexTemplate(articles: List<Article>) {
    head{
        title("Liste des articles")
    }

    body {
        h1{+"Administration"}
        h2{+"Ajout d'article"}
        form(action = "admin/article/add", encType = FormEncType.multipartFormData,
            method = FormMethod.post) {
            textInput { name = "title"; required=true ; placeholder="Titre"}
            textInput { name = "text" ; required=true ; placeholder="Texte"}
            submitInput { value = "Envoyer" }
        }
        hr()
        h2{+"Liste des articles:"}
        articles.forEach{
            ul {
                li {
                    a(href = "/article/${it.id}") {
                        +it.title
                    }
                    a(href = "admin/article/${it.id}/delete") {
                        text(" [X]")
                    }
                }
            }
        }

        footer {
            a{href="/"; +"Accueil"}
        }
    }
}
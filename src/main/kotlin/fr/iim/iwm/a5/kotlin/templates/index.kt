package fr.iim.iwm.a5.kotlin.templates

import fr.iim.iwm.a5.kotlin.Article
import kotlinx.html.*

fun HTML.indexTemplate(articles: List<Article>) {
    head{
        title("Liste des articles")
    }

    body {
        h1{+"Liste des articles"}
        articles.forEach{
            ul {
                li {
                    a(href = "/article/${it.id}") {
                        +it.title
                    }
                }
            }
        }
        footer {
            a{href="admin"; +"Administration"}
        }
    }
}
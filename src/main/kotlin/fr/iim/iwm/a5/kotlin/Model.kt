package fr.iim.iwm.a5.kotlin

interface Model {
    fun getArticleList(): List<Article>
    fun getArticle(id: Int): Article?
}
package fr.iim.iwm.a5.kotlin

import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ModelTests() {
    val model = MysqlModel("jdbc:h2:mem:cms;MODE=MYSQL", null, null)

    @Before
    fun initDB() {

        model.connectionPool.use{ connection ->
            connection.prepareStatement(
                """DROP TABLE IF EXISTS `articles`;
                        CREATE TABLE `articles` (
                        `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                        `title` varchar(255) DEFAULT NULL,
                        `text` text,
                        PRIMARY KEY (`id`)
                        );

                        INSERT INTO `articles` VALUES
                        (1,'article1','Le Lorem Ipsum est simplement du faux texte employé dans la composition et la mise en page avant impression. Le Lorem Ipsum est le faux texte standard de limprimerie depuis les années 1500, quand un imprimeur anonyme assembla ensemble des morceaux de texte pour réaliser un livre spécimen de polices de texte. Il na pas fait que survivre cinq siècles, mais sest aussi adapté à la bureautique informatique, sans que son contenu nen soit modifié. Il a été popularisé dans les années 1960 grâce à la vente de feuilles Letraset contenant des passages du Lorem Ipsum, et, plus récemment, par son inclusion dans des applications de mise en page de texte, comme Aldus PageMaker.\n\n\n'),
                        (2,'article2','On sait depuis longtemps que travailler avec du texte lisible et contenant du sens est source de distractions, et empêche de se concentrer sur la mise en page elle-même. Lavantage du Lorem Ipsum sur un texte générique comme. Du texte. Du texte. Du texte. est quil possède une distribution de lettres plus ou moins normale, et en tout cas comparable avec celle du français standard. De nombreuses suites logicielles de mise en page ou éditeurs de sites Web ont fait du Lorem Ipsum leur faux texte par défaut, et une recherche pour Lorem Ipsum vous conduira vers de nombreux sites qui nen sont encore quà leur phase de construction. Plusieurs versions sont apparues avec le temps, parfois par accident, souvent intentionnellement (histoire dy rajouter de petits clins doeil, voire des phrases embarassantes).\n\n');
                        """).use { stmt ->
                stmt.execute()
            }
        }
    }

    @Test
    fun testArticleInDB() {
        val article = model.getArticle(1)
        assertNotNull(article)
        assertEquals(1, article.id)
        assertEquals("article1", article.title)
        assertTrue(article.text!!.startsWith("Le Lorem"))
    }

    @Test
    fun testArticleNotInDB(){
        val article = model.getArticle(3)
        assertNull(article)
    }
}
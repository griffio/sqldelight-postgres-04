package griffio

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import griffio.migrations.Post
import griffio.queries.Sample

import org.postgresql.ds.PGSimpleDataSource
import java.util.Locale

private fun getSqlDriver(): SqlDriver {
    val datasource = PGSimpleDataSource()
    datasource.setURL("jdbc:postgresql://localhost:5432/blog")
    datasource.applicationName = "App Main"
    return datasource.asJdbcDriver()
}

fun stringIdentifier(n: Int) = (1..n).map { ('A'..'Z').random() }.joinToString("")
fun longIdentifier(n: Int) = (1..n).map { (1..10).random() }.joinToString("").toLong()
fun titleIdentifier(n: Int) = stringIdentifier(n).lowercase().replaceFirstChar { it.titlecase(Locale.ENGLISH) }
fun tagIdentifier() = stringIdentifier(4).lowercase()
fun authorIdentifier() = "${titleIdentifier(6)} ${titleIdentifier(68)}"

val arrayAdapter = object: ColumnAdapter<Set<String>, Array<String>> {
    override fun decode(databaseValue: Array<String>): Set<String> = databaseValue.toSet()
    override fun encode(value: Set<String>): Array<String> = value.toTypedArray()
}

fun main() {

    val driver = getSqlDriver()
    val sample = Sample(driver, Post.Adapter(arrayAdapter))

    val authorName = authorIdentifier()
    val blogTitle = "The ${titleIdentifier(8)} by $authorName is a great read"

    val author = sample.authorQueries.create(authorName, "$authorName@example.com", true).executeAsOne()
    val blogAuthor = sample.actionsQueries.createBlogWithAuthor(titleIdentifier(10), author.author_id).executeAsOne()

    sample.postQueries.create(blogAuthor.blog_id, blogTitle, setOf("blog", tagIdentifier())).executeAsOne()

}

package fr.iim.iwm.a5.kotlin

import java.sql.Connection
import java.sql.DriverManager
import java.util.concurrent.ConcurrentLinkedDeque
import java.util.concurrent.ConcurrentLinkedQueue

class ConnectionPool(val url: String?, val user: String?, val password: String?) {
    val list = ConcurrentLinkedQueue<Connection>()

    fun getConnection(): Connection {
        return list.poll()
            ?: DriverManager.getConnection(url, user, password)
    }
    fun makeAvailable(connection: Connection){
        list.add(connection)
    }

    inline fun use(block: (Connection) -> Unit) {
        val connection = getConnection()

        try {
            block(connection)
        } finally {
            makeAvailable(connection)
        }
    }
}
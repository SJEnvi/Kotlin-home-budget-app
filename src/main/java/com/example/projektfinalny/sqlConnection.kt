//////package com.example.projektfinalny
////////import com.google.cloud.sql.postgres.SocketFactory
//////import java.sql.Connection
//////import java.sql.DriverManager
//////import java.util.*
//////
//////class SqlConnection {
////////        val instanceConnectionName = "sound-yew-380917:europe-central2:sjenvidatabase"
////////        val databaseName = "kotlin-budget-app-db"
//////        val username = "postgres"
//////        val password = "unkind4YOU"
//////        val DB_NAME = "kotlin-budget-app-db"
//////        val jdbcUrl = String.format("jdbc:postgresql:///%s", DB_NAME)
//////
//////        //val url = "jdbc:postgresql:///postgres?cloudSqlInstance=sound-yew-380917:europe-central2:sjenvidatabase&socketFactory=com.google.cloud.sql.postgres.SocketFactory&sslmode=require&sslrootcert=server-ca.pem&sslcert=client-cert.pem&sslkey=client-key.pem"
//////
//////        fun connect(): Connection {
//////                val connectionProperties = Properties().apply {
//////                        setProperty("user", "gamplayfan")
//////                        setProperty("password", "LOLlolLOL98")
//////                        setProperty("sslmode", "disable")
////////                        setProperty("sslrootcert", "server-ca.pem")
////////                        setProperty("sslcert", "client-cert.pem")
////////                        setProperty("sslkey", "client-key.pem")
//////                        setProperty("socketFactory", "com.google.cloud.sql.postgres.SocketFactory")
//////                        setProperty("cloudSqlInstance", "sound-yew-380917:europe-central2:sjenvidatabase")
//////                        setProperty("enableIamAuth", "true")
//////                }
//////
//////                return DriverManager.getConnection(jdbcUrl, connectionProperties)
//////                        .also { connection ->
//////                                Runtime.getRuntime().addShutdownHook(Thread { connection.close() })
//////                        }
//////        }
//////}
////
////package com.example.projektfinalny
////
////import com.zaxxer.hikari.HikariConfig
////import com.zaxxer.hikari.HikariDataSource
////import java.util.*
////
////
////class ConnectorConnectionPoolFactory : Any() {
////    private val INSTANCE_CONNECTION_NAME =
////        "sound-yew-380917:europe-central2:sjenvidatabase"
////    private val INSTANCE_UNIX_SOCKET = System.getenv("INSTANCE_UNIX_SOCKET")
////    private val DB_USER = System.getenv("postgres")
////    private val DB_PASS = System.getenv("unkind4YOU")
////    private val DB_NAME = System.getenv("kotlin-budget-app-db")
////    var jdbcURL = String.format("jdbc:postgresql:///%s", DB_NAME)
////
////
////    var connProps = Properties().apply {
////        setProperty("user", "gamplayfan@gmail.com");
////        setProperty("password", "LOLlolLOL98");
////        setProperty("sslmode", "disable");
////        setProperty("socketFactory", "com.google.cloud.sql.postgres.SocketFactory");
////        setProperty("cloudSqlInstance", INSTANCE_CONNECTION_NAME);
////        setProperty("enableIamAuth", "true");
////    }
////
////    // Initialize connection pool
////    fun createConnectionPool(): HikariDataSource {
////        val config = HikariConfig().apply {
////            jdbcUrl = jdbcURL
////            dataSourceProperties = connProps
////            connectionTimeout = 10000 // 10s
////        }
////
////        return HikariDataSource(config)
////
////    }
////}
//
//import java.sql.*
//import java.util.*
//import java.sql.DriverManager
//import java.sql.Connection
//import java.sql.SQLException
//
//class DatabaseConnector {
//    fun connect() {
//        val connectionString =
//            "Server=tcp:new-server-sqo.database.windows.net,1433;Initial Catalog=tested;Persist Security Info=False;User ID=sjenvi;Password={your_password};MultipleActiveResultSets=False;Encrypt=True;TrustServerCertificate=False;Connection Timeout=30;"
//        val conn = DriverManager.getConnection(connectionString)
//        val sql = "INSERT INTO emails (email) VALUES (?)"
//        val statement = conn.prepareStatement(sql)
//        statement.setString(1, "test")
//        statement.executeUpdate()
//
//        conn.close()
//    }
//}
package hu.simda.musicmanagerserver.config

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories(basePackages = ["hu.simda.musicmanagerserver.dao"])
class MongoConfig {

    @Value("\${spring.data.mongodb.databaseName}")
    private val DB_NAME: String = ""

    @Value("\${spring.data.mongodb.connectionString}")
    private val CONNECTION_STRING = ""

    @Bean
    fun mongo(): MongoClient {
        val connectionString = ConnectionString(CONNECTION_STRING)
        val mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString).build()
        return MongoClients.create(mongoClientSettings)
    }

    @Bean
    fun mongoTemplate(): MongoTemplate = MongoTemplate(mongo(), DB_NAME)
}
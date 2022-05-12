package hu.simda.musicmanagerserver.config

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories(basePackages = ["hu.simda.musicmanagerserver.dao"])
class MongoConfig {

    @Bean
    fun mongo(): MongoClient {
        val connectionString = ConnectionString("mongodb+srv://admin:ysHqzMOUsqrr576s@musicmanagerdbinstance.ytfv0.mongodb.net/musicmanagerdb?retryWrites=true&w=majority")
        val mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString).build()
        return MongoClients.create(mongoClientSettings)
    }

    @Bean
    fun mongoTemplate(): MongoTemplate = MongoTemplate(mongo(), "musicmanagerdb")
}
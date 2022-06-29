package hu.simda.musicmanagerserver.domain

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
data class User(
    @Id
    val id: String = ObjectId.get().toHexString(),
    val name: String,
    @Indexed(unique = true)
    val username: String,
    val password: String,
    val role: Role = Role.USER
)


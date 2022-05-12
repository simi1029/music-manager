package hu.simda.musicmanagerserver.domain

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document("artists")
data class Artist(
    @Id
    val id: String = ObjectId.get().toHexString(),
    @Indexed(unique = true)
    val name: String,
    val country: String,
    val genre: Genre?
)
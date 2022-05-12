package hu.simda.musicmanagerserver.domain

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document("songs")
data class Song(
    @Id
    val id: String = ObjectId.get().toHexString(),
    @DBRef
    val artist: Artist,
    @Indexed(unique = true)
    val title: String,
    val length: Int,
    val rank: Rank?,
    val rating: Int?
)

package hu.simda.musicmanagerserver.domain

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document("albums")
data class Album(
    @Id
    val id: String = ObjectId.get().toHexString(),
    @DBRef
    val artist: Artist,
    val title: String,
    val releaseDate: Int,
    @DBRef
    val songs: List<Song>,
    val albumCover: String?,
    val rank: Rank? = Rank.UNRANKED,
    val rating: Int? = 0
)

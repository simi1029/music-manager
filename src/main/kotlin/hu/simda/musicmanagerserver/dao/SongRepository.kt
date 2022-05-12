package hu.simda.musicmanagerserver.dao

import hu.simda.musicmanagerserver.domain.Song
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface SongRepository : MongoRepository<Song, ObjectId> {
    @Query(value = "{}")
    fun getAllSongs() : List<Song>
}
package hu.simda.musicmanagerserver.dao

import hu.simda.musicmanagerserver.domain.Artist
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.util.Optional

interface ArtistRepository : MongoRepository<Artist, ObjectId> {
    @Query(value = "{}")
    fun getAllArtists(): List<Artist>

    @Query(value = "{ 'name': ?0 }")
    fun getArtist(name: String): Optional<Artist>

    @Query(value = "{ 'genre.genre': ?0 }")
    fun getArtistsByGenre(genre: String): List<Artist>

    @Query(value = "{ 'country': ?0 }")
    fun getArtistsByCountry(country: String): List<Artist>
}
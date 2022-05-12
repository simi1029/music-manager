package hu.simda.musicmanagerserver.service

import hu.simda.musicmanagerserver.dao.ArtistRepository
import hu.simda.musicmanagerserver.domain.Artist
import hu.simda.musicmanagerserver.domain.Genre
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ArtistService(@Autowired private val artistRepository: ArtistRepository) {

    fun getAllArtists() = artistRepository.getAllArtists()

    fun getArtistByID(id: String): Artist {
        val artist = artistRepository.findById(ObjectId(id))
        if (artist.isPresent) return artist.get() else throw ArtistNotFoundException("Artist is not found with given ID")
    }

    fun getArtistByName(name: String): Artist {
        val artist = artistRepository.getArtist(name)
        if (artist.isPresent) return artist.get() else throw ArtistNotFoundException("Artist is not found with given name")
    }

    fun getArtistsByGenre(genre: Genre): List<Artist> = artistRepository.getArtistsByGenre(genre.mainGenre.toString())

    fun getArtistsByCountry(country: String): List<Artist> = artistRepository.getArtistsByCountry(country)

    fun createNewArtist(artist: Artist) = artistRepository.insert(Artist(name = artist.name, country = artist.country, genre = artist.genre))

    fun updateArtist(artist: Artist) = artistRepository.save(artist)

    fun deleteArtist(id: String): String {
        artistRepository.delete(getArtistByID(id))
        return "Success"
    }
}
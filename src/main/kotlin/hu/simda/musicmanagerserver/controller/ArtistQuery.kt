package hu.simda.musicmanagerserver.controller

import com.expediagroup.graphql.server.operations.Query
import hu.simda.musicmanagerserver.domain.Artist
import hu.simda.musicmanagerserver.domain.Genre
import hu.simda.musicmanagerserver.service.ArtistService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class ArtistQuery(@Autowired private val artistService: ArtistService) : Query {

    fun allArtists(): List<Artist> = artistService.getAllArtists()

    fun allArtistsByCountry(country: String): List<Artist> = artistService.getArtistsByCountry(country)

    fun allArtistsByGenre(genre: Genre): List<Artist> = artistService.getArtistsByGenre(genre)

    fun singleArtistByID(id: String): Artist = artistService.getArtistByID(id)

    fun singleArtistByName(name: String): Artist = artistService.getArtistByName(name)
}
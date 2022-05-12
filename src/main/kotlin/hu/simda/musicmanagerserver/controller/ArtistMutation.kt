package hu.simda.musicmanagerserver.controller

import com.expediagroup.graphql.server.operations.Mutation
import hu.simda.musicmanagerserver.domain.Artist
import hu.simda.musicmanagerserver.service.ArtistService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class ArtistMutation(@Autowired private val artistService: ArtistService) : Mutation {

    fun createArtist(artist: Artist) = artistService.createNewArtist(artist)

    fun updateArtist(artist: Artist) = artistService.updateArtist(artist)

    fun deleteArtist(id: String) = artistService.deleteArtist(id)
}
package hu.simda.musicmanagerserver.controller

import com.expediagroup.graphql.server.operations.Mutation
import hu.simda.musicmanagerserver.domain.Album
import hu.simda.musicmanagerserver.domain.Song
import hu.simda.musicmanagerserver.service.AlbumService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class AlbumMutation(@Autowired private val albumService: AlbumService) : Mutation {

    fun createAlbum(album: Album) = albumService.createNewAlbum(album)

    fun addSongToAlbum(albumID: String, song: Song) = albumService.addSong(albumID, song)
}
package hu.simda.musicmanagerserver.controller

import com.expediagroup.graphql.server.operations.Query
import hu.simda.musicmanagerserver.domain.Album
import hu.simda.musicmanagerserver.service.AlbumService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class AlbumQuery(@Autowired private val albumService: AlbumService) : Query {

    fun allAlbums(): List<Album> = albumService.getAllAlbums()

    fun allAlbumsByTitle(title: String): List<Album> = albumService.getAlbumsByTitle(title)

    fun singleAlbumByID(id: String): Album = albumService.getAlbumByID(id)
}
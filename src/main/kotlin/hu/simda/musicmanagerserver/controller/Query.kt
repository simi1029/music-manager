package hu.simda.musicmanagerserver.controller

import com.expediagroup.graphql.server.operations.Query
import hu.simda.musicmanagerserver.dao.AlbumRepository
import hu.simda.musicmanagerserver.domain.Album
import org.springframework.stereotype.Controller

@Controller
class CustomQuery : Query {
    val albumRepository = AlbumRepository()

    fun allAlbums(): MutableList<Album> {
        return albumRepository.getAllAlbums()
    }
}
package hu.simda.musicmanagerserver.controller

import com.expediagroup.graphql.server.operations.Query
import hu.simda.musicmanagerserver.domain.Song
import hu.simda.musicmanagerserver.service.SongService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class SongQuery(@Autowired private val songService: SongService) : Query {

    fun allSongs(): List<Song> = songService.getAllSongs()

    fun singleSongByID(id: String): Song = songService.getSongByID(id)
}
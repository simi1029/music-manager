package hu.simda.musicmanagerserver.controller

import com.expediagroup.graphql.server.operations.Mutation
import hu.simda.musicmanagerserver.domain.Rank
import hu.simda.musicmanagerserver.domain.Song
import hu.simda.musicmanagerserver.service.SongService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class SongMutation(@Autowired private val songService: SongService) : Mutation {

    fun createSong(song: Song) = songService.createSong(song)

    fun updateSong(song: Song) = songService.updateSong(song)

    fun deleteSong(id: String) = songService.deleteSong(id)

    fun updateRank(id: String, newRank: Rank) = songService.updateRank(id, newRank)
}
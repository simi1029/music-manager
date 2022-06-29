package hu.simda.musicmanagerserver.service

import hu.simda.musicmanagerserver.dao.SongRepository
import hu.simda.musicmanagerserver.domain.Rank
import hu.simda.musicmanagerserver.domain.Song
import hu.simda.musicmanagerserver.service.exceptions.ArtistNotFoundException
import hu.simda.musicmanagerserver.service.exceptions.SongNotFoundException
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.math.floor

@Service
class SongService(
    @Autowired private val songRepository: SongRepository,
    @Autowired private val artistService: ArtistService
) {

    fun getAllSongs() = songRepository.getAllSongs()

    fun getSongByID(id: String): Song {
        val song = songRepository.findById(ObjectId(id))
        return if (song.isPresent) song.get() else throw SongNotFoundException("Song is not found with given ID")
    }

    fun getSongByTitle(title: String): Song {
        val song = songRepository.getSong(title)
        return if (song.isPresent) song.get() else throw SongNotFoundException("Song is not found with given title")
    }

    fun createSong(song: Song): Song {
        val artistName = song.artist.name
        val artist = try {
            artistService.getArtistByName(artistName)
        } catch (exception: ArtistNotFoundException) {
            artistService.createNewArtist(song.artist)
        }

        return songRepository.insert(
            Song(
                artist = artist,
                title = song.title,
                length = song.length
            )
        )
    }

    fun updateSong(song: Song): Song = songRepository.save(song)

    fun deleteSong(id: String): String {
        songRepository.delete(getSongByID(id))
        return "Success"
    }

    fun updateRank(id: String, newRank: Rank): Song {
        val song = getSongByID(id)
        val updatedSong =
            Song(song.id, song.artist, song.title, song.length, newRank, calculateRating(newRank, song.length))
        return updateSong(updatedSong)
    }

    private fun calculateRating(rank: Rank, length: Int): Int =
        floor(length.div(MINUTE_IN_SECONDS).times(rank.value)).toInt()

    companion object {
        private const val MINUTE_IN_SECONDS = 60.0
    }
}
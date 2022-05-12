package hu.simda.musicmanagerserver.service

import hu.simda.musicmanagerserver.dao.SongRepository
import hu.simda.musicmanagerserver.domain.Rank
import hu.simda.musicmanagerserver.domain.Song
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.math.floor

@Service
class SongService(@Autowired private val songRepository: SongRepository) {

    fun getAllSongs() = songRepository.getAllSongs()

    fun getSongByID(id: String): Song {
        val song = songRepository.findById(ObjectId(id))
        if (song.isPresent) return song.get() else throw SongNotFoundException("Song is not found with given ID")
    }

    fun createNewSong(song: Song): Song = songRepository.insert(
        Song(
            artist = song.artist,
            title = song.title,
            length = song.length,
            rank = song.rank,
            rating = calculateRating(song.rank, song.length)
        ))

    fun updateSong(song: Song): Song = songRepository.save(song)

    fun deleteSong(id: String): String {
        songRepository.delete(getSongByID(id))
        return "Success"
    }

    fun updateRank(id: String, newRank: Rank): Song {
        val song = getSongByID(id)
        val updatedSong = Song(song.id, song.artist, song.title, song.length, newRank, calculateRating(newRank, song.length))
        return updateSong(updatedSong)
    }

    private fun calculateRating(rank: Rank?, length: Int): Int? = if (rank != null) floor(length.div(60.0).times(rank.value)).toInt() else null
}
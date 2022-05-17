package hu.simda.musicmanagerserver.service

import hu.simda.musicmanagerserver.dao.AlbumRepository
import hu.simda.musicmanagerserver.domain.Album
import hu.simda.musicmanagerserver.domain.Artist
import hu.simda.musicmanagerserver.domain.Rank
import hu.simda.musicmanagerserver.domain.Song
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.math.round

@Service
class AlbumService(
    @Autowired private val albumRepository: AlbumRepository,
    @Autowired private val artistService: ArtistService,
    @Autowired private val songService: SongService
) {

    fun getAllAlbums() = albumRepository.getAllAlbums()

    fun getAlbumByID(id: String): Album {
        val album = albumRepository.findById(ObjectId(id))
        return if (album.isPresent) album.get() else throw AlbumNotFoundException("Album is not found with given ID")
    }

    fun getAlbumsByTitle(title: String): List<Album> = albumRepository.getAlbums(title)

    fun getAlbum(artist: Artist, title: String, releaseDate: Int): Album {
        val album = albumRepository.getAlbum(ObjectId(artist.id), title, releaseDate)
        return if (album.isPresent) album.get() else throw AlbumNotFoundException("Album is not found based on search criteria")
    }

    fun createNewAlbum(album: Album): Album {
        val artistName = album.artist.name
        val songs = album.songs

        val artist = try {
            artistService.getArtistByName(artistName)
        } catch (exception: ArtistNotFoundException) {
            artistService.createNewArtist(album.artist)
        }

        val newSongs = songs.map {
            try {
                songService.getSongByTitle(it.title)
            } catch (exception: SongNotFoundException) {
                songService.createNewSong(it)
            }
        }

        return albumRepository.insert(
            Album(
                title = album.title,
                artist = artist,
                releaseDate = album.releaseDate,
                songs = newSongs.toList(),
                albumCover = album.albumCover
            )
        )
    }

    fun addSong(albumID: String, song: Song): Album {
        val albumToUpdate = getAlbumByID(albumID)

        val updatedSongList: List<Song> = albumToUpdate.songs.toCollection(mutableListOf()) + song

        return albumRepository.save(
            Album(
                id = albumToUpdate.id,
                artist = albumToUpdate.artist,
                title = albumToUpdate.title,
                releaseDate = albumToUpdate.releaseDate,
                songs = updatedSongList,
                albumCover = albumToUpdate.albumCover,
                rank = albumToUpdate.rank,
                rating = albumToUpdate.rating
            )
        )
    }

    fun addSongs(albumID: String, songs: List<Song>): Album {
        TODO("need to be implemented")
    }

    private fun calculateAlbumRating(songs: List<Song>): Int = songs.sumOf { it.rating!! }

    private fun calculateAlbumRanking(songs: List<Song>): Rank {
        val numberOfTracks = songs.size
        val sumOfRankings = songs.sumOf { it.rank!!.value }
        val average = sumOfRankings.toDouble().div(numberOfTracks)
        return when {
            average < 6 -> fromInt(round(average).toInt())
            average < 8.5 -> Rank.EXCELLENT
            else -> Rank.MASTERPIECE
        }
    }

    companion object {
        private val map = Rank.values().associateBy(Rank::value)
        fun fromInt(type: Int) = map[type]!!
    }
}
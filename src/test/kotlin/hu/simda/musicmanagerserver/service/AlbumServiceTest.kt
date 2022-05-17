package hu.simda.musicmanagerserver.service

import hu.simda.musicmanagerserver.dao.AlbumRepository
import hu.simda.musicmanagerserver.domain.Album
import hu.simda.musicmanagerserver.domain.Artist
import hu.simda.musicmanagerserver.domain.Genre
import hu.simda.musicmanagerserver.domain.MainGenre
import hu.simda.musicmanagerserver.domain.Song
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

private const val ARTIST_ID = "626c002832268521b7fef07e"
private const val ARTIST_NAME = "Test Artist"
private const val COUNTRY = "Test Country"
private val POP_GENRE = Genre(MainGenre.POP)
private val ARTIST = Artist(ARTIST_ID, ARTIST_NAME, COUNTRY, POP_GENRE)
private const val ALBUM_TITLE = "Test Album"
private const val SONG_ID = "627d4dd763ee582af31463ac"
private const val SONG_TITLE = "Test Song"
private val SONG = Song(SONG_ID, ARTIST, SONG_TITLE, 300)

@SpringBootTest
internal class AlbumServiceTest {

    private val albumRepository: AlbumRepository = mockk(relaxed = true)
    private val artistService: ArtistService = mockk(relaxed = true)
    private val songService: SongService = mockk(relaxed = true)
    private val albumService = AlbumService(albumRepository, artistService, songService)

    @Test
    fun `getAllAlbums should return a list of albums`() {
        //given
        val expectedAlbums = listOf(
            Album("", ARTIST, ALBUM_TITLE, 2007, listOf(SONG, SONG),null),
            Album("", ARTIST, ALBUM_TITLE, 2007, listOf(SONG, SONG),null)
        )
        every { albumRepository.getAllAlbums() } returns expectedAlbums
        //when
        val actualAlbums = albumService.getAllAlbums()
        //then
        verify(exactly = 1) { albumRepository.getAllAlbums() }
        assertEquals(expectedAlbums, actualAlbums)
    }
}
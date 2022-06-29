package hu.simda.musicmanagerserver.service

import hu.simda.musicmanagerserver.dao.SongRepository
import hu.simda.musicmanagerserver.domain.Artist
import hu.simda.musicmanagerserver.domain.Genre
import hu.simda.musicmanagerserver.domain.MainGenre
import hu.simda.musicmanagerserver.domain.Rank
import hu.simda.musicmanagerserver.domain.Song
import hu.simda.musicmanagerserver.service.exceptions.SongNotFoundException
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.boot.test.context.SpringBootTest
import java.util.Optional
import java.util.stream.Stream

private const val SONG_ID = "627d4dd763ee582af31463ac"
private const val SONG_ID_2 = "627d4dd763ee582af31463ad"
private const val SONG_ID_3 = "627d4dd763ee582af31463ae"
private const val SONG_ID_NOT_IN_DB = "012345678901234567890123"
private val SONG_OBJECT_ID = ObjectId(SONG_ID)
private val SONG_OBJECT_ID_NOT_IN_DB = ObjectId(SONG_ID_NOT_IN_DB)
private const val SONG_TITLE = "Test Song"
private const val ARTIST_ID = "626c002832268521b7fef07e"
private const val ARTIST_NAME = "Test Artist"
private const val COUNTRY = "Test Country"
private val POP_GENRE = Genre(MainGenre.POP)
private val ARTIST = Artist(ARTIST_ID, ARTIST_NAME, COUNTRY, POP_GENRE)

@SpringBootTest
internal class SongServiceTest {

    private val songRepository: SongRepository = mockk()
    private val artistService: ArtistService = mockk()
    private val songService = SongService(songRepository, artistService)

    @Test
    fun `getAllSongs should return a list of artists`() {
        //given
        val expectedSongs = listOf(
            Song(SONG_ID, ARTIST, SONG_TITLE, 400),
            Song(SONG_ID_2, ARTIST, SONG_TITLE, 300),
            Song(SONG_ID_3, ARTIST, SONG_TITLE, 200)
        )
        every { songRepository.getAllSongs() } returns expectedSongs
        //when
        val actualArtists = songService.getAllSongs()
        //then
        verify(exactly = 1) { songRepository.getAllSongs() }
        assertEquals(expectedSongs, actualArtists)
    }

    @Test
    fun `getSongByID should throw exception when ID is not found`() {
        //given
        every { songRepository.findById(SONG_OBJECT_ID_NOT_IN_DB) } returns Optional.ofNullable(null)
        //when
        assertThrows<SongNotFoundException> {
            songService.getSongByID(SONG_ID_NOT_IN_DB)
        }
        //then
        verify(exactly = 1) { songRepository.findById(SONG_OBJECT_ID_NOT_IN_DB) }
    }

    @Test
    fun `createNewSong should insert new song into DB and return the inserted song with new ID generated`() {
        //given
        val inputSong = Song("", ARTIST, SONG_TITLE, 300)
        val expectedSong = Song(SONG_ID, ARTIST, SONG_TITLE, 300)
        every { songRepository.insert(any<Song>()) } returns expectedSong
        every { artistService.getArtistByName(ARTIST_NAME) } returns ARTIST
        //when
        val actualArtist = songService.createSong(inputSong)
        //then
        verify(exactly = 1) { songRepository.insert(any<Song>()) }
        assertEquals(expectedSong, actualArtist)
    }

    @Test
    fun `updateSong should update song`() {
        //given
        val expectedSong = Song(SONG_ID, ARTIST, SONG_TITLE, 300)
        every { songRepository.save(expectedSong) } returns expectedSong
        //when
        val actualArtist = songService.updateSong(expectedSong)
        //then
        verify(exactly = 1) { songRepository.save(expectedSong) }
        assertEquals(expectedSong, actualArtist)
    }

    @ParameterizedTest
    @MethodSource("provideDataForUpdateRank")
    fun `updateRank should calculate rating and update both fields`(length: Int, rank: Rank, expectedRating: Int) {
        //given
        val song = Song(
            SONG_ID,
            ARTIST,
            SONG_TITLE,
            length
        )
        val expectedSong = Song(
            SONG_ID,
            ARTIST,
            SONG_TITLE,
            length,
            rank,
            expectedRating
        )
        every { songRepository.findById(SONG_OBJECT_ID) } returns Optional.of(song)
        every { songRepository.save(expectedSong) } returns expectedSong
        //when
        val actualSong = songService.updateRank(SONG_ID, rank)
        //then
        verify(exactly = 1) { songRepository.findById(SONG_OBJECT_ID) }
        verify(exactly = 1) { songRepository.save(expectedSong) }
        assertEquals(expectedRating, actualSong.rating)
    }

    @Test
    fun `deleteSong should delete song and return 'Success' message`() {
        //given
        val expectedSong = Song(SONG_ID, ARTIST, SONG_TITLE, 300)
        every { songRepository.findById(SONG_OBJECT_ID) } returns Optional.of(expectedSong)
        justRun { songRepository.delete(expectedSong) }
        //when
        val actualMessage = songService.deleteSong(SONG_ID)
        //then
        verify(exactly = 1) { songRepository.delete(expectedSong) }
        assertEquals("Success", actualMessage)
    }

    companion object {
        @JvmStatic
        fun provideDataForUpdateRank(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(335, Rank.EXCELLENT, 39),
                Arguments.of(300, Rank.MASTERPIECE, 50),
                Arguments.of(250, Rank.POOR, 0)
            )
        }
    }
}